package com.automobileapp.sotame.views;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class OrderListView {
    private final ObservableList<Order> orderList;
    private final TableView<Order> tableViewOrder;

    public OrderListView(){
        orderList = FXCollections.observableArrayList();
        try{
            orderList.addAll(DatabaseManager.getAllOrders());
        } catch (SQLException e) {
            System.err.println("Error to get all orders. Method: "+"OrderListView()" + e.getMessage());
        }
        tableViewOrder = new TableView<>(orderList);
        // Defining columns by Order class variables
        TableColumn<Order,Integer> idOrder = new TableColumn<>("ID");
        idOrder.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        TableColumn<Order,String> dateOrder = new TableColumn<>("Fecha");
        dateOrder.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        TableColumn<Order, String> estimatedCompletionDate = new TableColumn<>("Fecha Estimada de Entrega");
        estimatedCompletionDate.setCellValueFactory(new PropertyValueFactory<>("estimatedCompletionDate"));
        TableColumn<Order,String> workDescription = new TableColumn<>("Descripción del Trabajo");
        workDescription.setPrefWidth(450);
        workDescription.setCellValueFactory(new PropertyValueFactory<>("workDescription"));
        workDescription.setCellFactory(tc -> {
            TableCell<Order, String> cell = new TableCell<>();
            final Text text = new Text();
            text.wrappingWidthProperty().bind(workDescription.widthProperty());
            text.setTextAlignment(TextAlignment.LEFT); // Alineación del texto dentro del Text
            text.setStyle("-fx-text-alignment: left;"); // Estilo CSS para alinear texto dentro del Text
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            cell.setAlignment(Pos.TOP_LEFT); // Asegura la alineación de la celda al tope izquierdo
            cell.setPadding(new Insets(5   )); // Ajuste del padding para una mejor presentación

            // Listener para actualizar el texto del cell
            cell.itemProperty().addListener((obs, oldValue, newValue) -> text.setText(newValue));
            return cell;
        });
        TableColumn<Order, String> totalCost = new TableColumn<>("Costo Total");
        totalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        TableColumn<Order, String> status = new TableColumn<>("Estado de la Órden");
        status.setCellValueFactory(new PropertyValueFactory<>("statusOrder"));
        TableColumn<Order, String> automobileId = new TableColumn<>("ID del Automovil");
        automobileId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAutomobileOfOrder().getIdAutomobile())));
        TableColumn<Order, String> automobileManufacturer = new TableColumn<>("Fabricante del Automovil");
        automobileManufacturer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutomobileOfOrder().getManufacturer()));
        TableColumn<Order, String> automobileModel = new TableColumn<>("Modelo del Automovil");
        automobileModel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutomobileOfOrder().getModel()));
        TableColumn<Order, String> automobileYear = new TableColumn<>("Año del Automovil");
        automobileYear.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAutomobileOfOrder().getYearManufactured())));
        TableColumn<Order,String> automobilePlates = new TableColumn<>("Patente del Automovil");
        automobilePlates.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutomobileOfOrder().getNumberPlate()));
        TableColumn<Order,String> customerName = new TableColumn<>("Nombre(s) del Cliente");
        customerName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerOfOrder().getFirstNameCustomer()));
        TableColumn<Order,String> customerLastName = new TableColumn<>("Apellido(s) del Cliente");
        customerLastName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerOfOrder().getLastNameCustomer()));
        // Adding columns to table
        tableViewOrder.getColumns().addAll(idOrder,dateOrder,
                estimatedCompletionDate,
                workDescription,
                totalCost,
                status,
                automobileId,
                automobileManufacturer,
                automobileModel,
                automobileYear,
                automobilePlates,
                customerName,
                customerLastName);
    }

    public void show(Stage parentStage){
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        // buttons to add, edit and deleter employees
        Button addButton = new Button("Agregar órden");
        Button editButton = new Button("Editar órden");
        Button deleteButton = new Button("Eliminar órden");
        // set up button events
        addButton.setOnAction(event -> {
            // Abre el formulario de órdenes para agregar una nueva orden
            showOrderForm(null); // `null` para agregar una nueva orden
        });
        editButton.setOnAction(event -> {
            // TODO
            // Obtiene la orden seleccionada en la tabla
            Order selectedOrder = tableViewOrder.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                // Abre el formulario con los datos de la orden seleccionada para editar
                showOrderForm(selectedOrder);
                tableViewOrder.refresh();
            } else {
                // Muestra un mensaje de alerta si no se ha seleccionado ninguna orden
                showAlert("Editar Orden", "Por favor, selecciona una orden para editar.");
            }
        });
        deleteButton.setOnAction(event -> {
            // Obtiene la orden seleccionada en la tabla
            Order selectedOrder = tableViewOrder.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                // Pide confirmación antes de eliminar
                boolean confirmed = showConfirmation("Eliminar Orden", "¿Estás seguro de que deseas eliminar esta orden?");
                if (confirmed) {
                    try {
                        // Elimina la orden de la base de datos y de la lista
                        DatabaseManager.deleteOrder(selectedOrder.getIdOrder());
                        orderList.remove(selectedOrder);
                    } catch (SQLException e) {
                        showAlert("Error al eliminar", "Hubo un error al eliminar la orden: " + e.getMessage());
                    }
                }
            } else {
                // Muestra un mensaje de alerta si no se ha seleccionado ninguna orden
                showAlert("Eliminar Orden", "Por favor, selecciona una orden para eliminar.");
            }
        });
        // HBox for buttons
        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10));

        layout.setCenter(tableViewOrder);
        layout.setBottom(buttonBox);
        // set up scene and show it
        Stage stage = new Stage();
        stage.setTitle("Módulo de Órdenes de Trabajo");
        stage.setScene(new Scene(layout, 800, 600));
        stage.initOwner(parentStage);
        stage.show();
    }

    private void showOrderForm(Order order){
        OrderFormView formView = new OrderFormView(order, orderList);
        formView.show();
    }

    private void showAlert(String title, String message) {
        // Mostrar una alerta simple
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String message) {
        // Mostrar una confirmación y devolver el resultado
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}
