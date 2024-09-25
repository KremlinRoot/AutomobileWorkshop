package com.automobileapp.sotame.views;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.StockItem;
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
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class ItemStockListView {
    private final ObservableList <StockItem> itemStockList;
    private final TableView<StockItem> tableViewItemStockList;

    public ItemStockListView(){
        itemStockList = FXCollections.observableArrayList();
        try{
            itemStockList.addAll(DatabaseManager.getAllItemStock());
        } catch(SQLException e){
            System.err.println("Error to get all item stock. Method: "+"ItemStockListView()" + e.getMessage());
        }
        tableViewItemStockList = new TableView<>(itemStockList);
        // Defining columns by StockItem class variables
        TableColumn<StockItem,Integer> idStockItem = new TableColumn<>("ID");
        idStockItem.setCellValueFactory(new PropertyValueFactory<>("idStockItem"));
        TableColumn<StockItem,String> productName = new TableColumn<>("Nombre del Producto");
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        TableColumn<StockItem,String> productCode = new TableColumn<>("Código del Producto");
        productCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        TableColumn<StockItem, String> category = new TableColumn<>("Categoría");
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableColumn<StockItem,Integer> quantityInStock = new TableColumn<>("Existencias");
        quantityInStock.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
        TableColumn<StockItem,Integer> minumunQunantityInStock = new TableColumn<>("Existencias Mínimas");
        minumunQunantityInStock.setCellValueFactory(new PropertyValueFactory<>("minumunQunantityInStock"));
        TableColumn<StockItem,Double> unitPrice = new TableColumn<>("Precio Unitario");
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        TableColumn<StockItem,String> supplier = new TableColumn<>("Proveedoredor");
        supplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
        TableColumn<StockItem,Double> totalCost = new TableColumn<>("Costo Total en Stock");
        totalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        TableColumn<StockItem,String> notes = new TableColumn<>("Notas");
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        // Adding columns to table view
        tableViewItemStockList.getColumns().addAll(idStockItem,productName,
                productCode,category,quantityInStock,minumunQunantityInStock,
                unitPrice,supplier,totalCost,notes);

    }

    public void show(Stage parentStage){
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        // Styling layout
        layout.getStyleClass().add("listview");
        // Buttons to add, edit and delete items
        Button addButton = new Button("Añadir artículo");
        Button editButton = new Button("Editar artículo");
        Button deleteButton = new Button("Eliminar artículo");
        // Styling buttons
        addButton.getStyleClass().add("button-crud");
        editButton.getStyleClass().add("button-crud");
        deleteButton.getStyleClass().add("button-crud");
        // Set up action buttons
        addButton.setOnAction(e -> {
            showItemStockForm(null);
        });
        editButton.setOnAction(e -> {
            // TODO: Edit item
            StockItem editedItem = tableViewItemStockList.getSelectionModel().getSelectedItem();
            if(editedItem != null){
                showItemStockForm(editedItem);
                tableViewItemStockList.refresh();
            } else {
                showAlert("Editar artículo", "Por favor, selecciona un artículo para editar.");
            }
        });
        deleteButton.setOnAction(e -> {
            // TODO: Delete item
            StockItem deletedItem = tableViewItemStockList.getSelectionModel().getSelectedItem();
            if (deletedItem != null) {
                if (showConfirmation("Eliminar artículo", "¿Estás seguro de eliminar este artículo?")) {
                    try {
                        DatabaseManager.deleteItemStock(deletedItem);
                        itemStockList.remove(deletedItem);
                        tableViewItemStockList.refresh();
                    } catch (SQLException e1) {
                        System.err.println("Error to delete item stock. Method: " + "ItemStockListView()" + e1.getMessage());
                    }
                }
            }
        });

        // HBox for buttons
        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10));
        layout.setCenter(tableViewItemStockList);
        layout.setBottom(buttonBox);
        // Setting up scene and show
        Stage stage = new Stage();
        stage.setTitle("Lista de artículos");
        stage.getIcons().addAll(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-16.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-24.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-32.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-64.png")))
        );
        // Creating scene
        Scene stockItemListScene = new Scene(layout, 800, 600);
        // Loading styles
        String styleSheet = Objects.requireNonNull(getClass().getResource("/MainStyle.css")).toExternalForm();
        stockItemListScene.getStylesheets().add(styleSheet);
        // Adding scene to stage
        stage.setScene(stockItemListScene);
        stage.initOwner(parentStage);
        stage.show();
    }
    private void showItemStockForm (StockItem itemStock){
        StockItemFormView stockItemFormView = new StockItemFormView(itemStock, itemStockList);
        stockItemFormView.show();
    }

    private void showAlert (String title, String message){
        // Mostrar una alerta simple
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmation (String title, String message){
        // Mostrar una confirmación y devolver el resultado
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;

    }
}

