package com.automobileapp.sotame.views;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.BudgetOrder;
import com.automobileapp.sotame.models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class BudgetOrderFormView {
    private final BudgetOrder budgetOrder;
    private final ObservableList<BudgetOrder> budgetOrderList;

    public BudgetOrderFormView(BudgetOrder budgetOrder, ObservableList<BudgetOrder> budgetOrderList) {
        this.budgetOrder = budgetOrder;
        this.budgetOrderList = budgetOrderList;
    }

    public void show() {
        Stage stage = new Stage();
        stage.setTitle(budgetOrder == null ? "Agregar Presupuesto" : "Editar Presupuesto");

        // Crear campos de entrada de datos
        ComboBox<Order> orderComboBox = new ComboBox<>();
        TextField laborHoursField = new TextField();
        TextField partCostField = new TextField();

        // Poblar el ComboBox con órdenes disponibles desde la base de datos
        try {
            List<Order> orders = DatabaseManager.getAllOrders();
            orderComboBox.setItems(FXCollections.observableArrayList(orders));
        } catch (SQLException e) {
            System.err.println("Error al obtener órdenes: " + e.getMessage());
        }

        // Si estamos editando, poblar los campos con los valores existentes
        if (budgetOrder != null) {
            orderComboBox.setValue(budgetOrder.getOrder());
            laborHoursField.setText(String.valueOf(budgetOrder.getLaborHours()));
            partCostField.setText(String.valueOf(budgetOrder.getPartCost()));
        }

        // Crear un formulario utilizando GridPane para organizar los campos
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Orden Relacionada:"), 0, 0);
        gridPane.add(orderComboBox, 1, 0);

        gridPane.add(new Label("Horas de Trabajo:"), 0, 1);
        gridPane.add(laborHoursField, 1, 1);

        gridPane.add(new Label("Costo de Piezas:"), 0, 2);
        gridPane.add(partCostField, 1, 2);

        // Botones de acción
        Button saveButton = new Button("Guardar");
        Button cancelButton = new Button("Cancelar");

        // Evento del botón guardar
        saveButton.setOnAction(e -> {
            Order selectedOrder = orderComboBox.getValue();
            double laborHours = Double.parseDouble(laborHoursField.getText());
            double partCost = Double.parseDouble(partCostField.getText());

            if (budgetOrder == null) {
                // Si es nuevo, crear un nuevo BudgetOrder
                BudgetOrder newBudgetOrder = new BudgetOrder(0,selectedOrder, laborHours, partCost);
                try {
                    DatabaseManager.insertBudgetOrder(newBudgetOrder); // Insertar en la base de datos
                    budgetOrderList.add(newBudgetOrder); // Añadir a la lista en la vista principal
                } catch (SQLException ex) {
                    System.err.println("Error al guardar presupuesto: " + ex.getMessage());
                }
            } else {
                // Si estamos editando, actualizar el BudgetOrder existente
                budgetOrder.setOrder(selectedOrder);
                budgetOrder.setLaborHours(laborHours);
                budgetOrder.setPartCost(partCost);

                try {
                    DatabaseManager.updateBudgetOrder(budgetOrder); // Actualizar en la base de datos
                } catch (SQLException ex) {
                    System.err.println("Error al actualizar presupuesto: " + ex.getMessage());
                }
            }
            stage.close(); // Cerrar el formulario
        });

        // Evento del botón cancelar
        cancelButton.setOnAction(e -> stage.close());

        // Añadir botones al gridPane
        gridPane.add(saveButton, 0, 3);
        gridPane.add(cancelButton, 1, 3);

        // Configurar escena y mostrarla
        Scene scene = new Scene(gridPane, 400, 200);
        stage.setScene(scene);
        stage.show();
    }
}
