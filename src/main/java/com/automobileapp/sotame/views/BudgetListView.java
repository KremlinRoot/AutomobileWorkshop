package com.automobileapp.sotame.views;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.BudgetOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class BudgetListView {
    private final ObservableList<BudgetOrder> budgetOrderList;
    private final TableView<BudgetOrder> tableViewBudgetOrder;

    public BudgetListView() {
        // Inicializar lista de BudgetOrder
        budgetOrderList = FXCollections.observableArrayList();

        // Obtener todos los BudgetOrders desde la base de datos
        budgetOrderList.addAll(DatabaseManager.getAllBudgetOrders());

        // Crear tabla de BudgetOrder
        tableViewBudgetOrder = new TableView<>(budgetOrderList);

        // Definir columnas de la tabla reflejando los atributos de BudgetOrder
        TableColumn<BudgetOrder, Integer> idBudgetOrderColumn = new TableColumn<>("ID");
        idBudgetOrderColumn.setCellValueFactory(new PropertyValueFactory<>("idBudgetOrder"));

        TableColumn<BudgetOrder, String> orderNumberColumn = new TableColumn<>("Número de Orden");
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber")); // Aquí necesitas un getter en Order

        TableColumn<BudgetOrder, Double> laborHoursColumn = new TableColumn<>("Horas de Trabajo");
        laborHoursColumn.setCellValueFactory(new PropertyValueFactory<>("laborHours"));

        TableColumn<BudgetOrder, Double> partCostColumn = new TableColumn<>("Costo de Piezas");
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));

        TableColumn<BudgetOrder, Double> totalCostColumn = new TableColumn<>("Costo Total");
        totalCostColumn.setCellValueFactory(cellData -> {
            // Calcular costo total (horas de trabajo * costo hora más costo de piezas)
            double laborCost = cellData.getValue().getLaborHours() * 100; // Ejemplo: costo por hora = 100
            double partCost = cellData.getValue().getPartCost();
            //return new javafx.beans.property.SimpleObjectProperty<>(laborCost + partCost);
            return new javafx.beans.property.SimpleObjectProperty(partCost);
        });

        // Añadir columnas a la tabla
        tableViewBudgetOrder.getColumns().addAll(idBudgetOrderColumn,orderNumberColumn, laborHoursColumn, partCostColumn, totalCostColumn);
    }

    public void show(Stage parentStage) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // Botones de acciones
        Button addButton = new Button("Agregar Presupuesto");
        Button editButton = new Button("Editar Presupuesto");
        Button deleteButton = new Button("Eliminar Presupuesto");

        // Configurar eventos de botones
        addButton.setOnAction(e -> showBudgetForm(null)); // Agregar presupuesto
        editButton.setOnAction(e -> {
            BudgetOrder selectedBudget = tableViewBudgetOrder.getSelectionModel().getSelectedItem();
            if (selectedBudget != null) {
                showBudgetForm(selectedBudget); // Editar presupuesto
                try {
                    DatabaseManager.updateBudgetOrder(selectedBudget); // Actualizar en base de datos
                    tableViewBudgetOrder.refresh(); // Refrescar la tabla
                } catch (SQLException ex) {
                    System.err.println("Error al actualizar el presupuesto: " + ex.getMessage());
                }
            }
        });
        deleteButton.setOnAction(e -> {
            BudgetOrder selectedBudget = tableViewBudgetOrder.getSelectionModel().getSelectedItem();
            if (selectedBudget != null) {
                try {
                    DatabaseManager.deleteBudgetOrder(selectedBudget.getOrder().getIdOrder());
                } catch (SQLException ex) {
                    System.err.println("Error al eliminar el presupuesto: " + ex.getMessage());
                }
                budgetOrderList.remove(selectedBudget); // Eliminar de la lista
            }
        });

        // Contenedor para botones
        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10));

        layout.setCenter(tableViewBudgetOrder);
        layout.setBottom(buttonBox);

        // Configurar escena y mostrarla
        Stage stage = new Stage();
        stage.setTitle("Módulo Presupuestos");
        stage.getIcons().addAll(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-16.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-24.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-32.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-64.png")))
        );
        stage.setScene(new Scene(layout, 800, 600));
        stage.initOwner(parentStage);
        stage.show();
    }

    private void showBudgetForm(BudgetOrder budgetOrder) {
        BudgetOrderFormView formView = new BudgetOrderFormView(budgetOrder, budgetOrderList);
        formView.show();
    }
}
