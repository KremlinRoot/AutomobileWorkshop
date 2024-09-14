package com.automobileapp.sotame.views;

import com.automobileapp.sotame.models.Category;
import com.automobileapp.sotame.models.StockItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.h2.engine.Database;
import com.automobileapp.sotame.database.DatabaseManager;

public class StockItemFormView {
    private final StockItem stockItem;
    private final ObservableList<StockItem> stockItemList;
    // Constructor
    public StockItemFormView(StockItem stockItem, ObservableList<StockItem> stockItemList) {
        this.stockItem = stockItem;
        this.stockItemList = stockItemList;
    }

    public void show(){
        Stage stage = new Stage();
        stage.setTitle(stockItem == null ? "Agregar producto" : "Editar Proyecto");
        stage.initModality(Modality.WINDOW_MODAL);

        // Create the form layout
        GridPane formLayout = new GridPane();
        formLayout.setHgap(10);
        formLayout.setVgap(8);
        formLayout.setPadding(new Insets(10));

        // Text fields
        TextField productName = new TextField(stockItem != null ? stockItem.getProductName() : "");
        TextField productCode = new TextField(stockItem != null ? stockItem.getProductCode() : "");
        ComboBox<Category> categoryComboBox = new ComboBox<>(FXCollections.observableArrayList(Category.values())); // Create a list of category values
        categoryComboBox.setValue(stockItem != null ? stockItem.getCategory() : Category.ACCESORIO);
        TextField quantityInStock = new TextField(stockItem != null ? String.valueOf(stockItem.getQuantityInStock()) : "");
        TextField minumunQunantityInStock = new TextField(stockItem != null ? String.valueOf(stockItem.getMinumunQunantityInStock()) : "");
        TextField unitPrice = new TextField(stockItem != null ? String.valueOf(stockItem.getUnitPrice()) : "");
        TextField Supplier = new TextField(stockItem != null ? stockItem.getSupplier() : "");
        TextField totalCost = new TextField(stockItem != null ? String.valueOf(stockItem.getTotalCost()) : "");
        TextField notes = new TextField(stockItem != null ? stockItem.getNotes() : "");

        // Add labels and text fields to the grid
        formLayout.add(new Label("Nombre:"), 0, 0);
        formLayout.add(productName, 1, 0);
        formLayout.add(new Label("Código:"), 0, 1);
        formLayout.add(productCode, 1, 1);
        formLayout.add(new Label("Categoría:"), 0, 2);
        formLayout.add(categoryComboBox, 1, 2);
        formLayout.add(new Label("Existencias:"), 0, 3);
        formLayout.add(quantityInStock, 1, 3);
        formLayout.add(new Label("Existencias Minimas:"), 0, 4);
        formLayout.add(minumunQunantityInStock, 1, 4);
        formLayout.add(new Label("Precio Unitario:"), 0, 5);
        formLayout.add(unitPrice, 1, 5);
        formLayout.add(new Label("Proveedoredor:"), 0, 6);
        formLayout.add(Supplier, 1, 6);
        formLayout.add(new Label("Notas:"), 0, 7);
        formLayout.add(notes, 1, 7);

        // Save button
        Button saveButton = new Button("Guardar");
        saveButton.setOnAction(e -> {
            try {
                if (stockItem == null) {
                    StockItem newStockItem = new StockItem(
                            0,
                            productName.getText(),
                            productCode.getText(),
                            categoryComboBox.getValue(),
                            Integer.parseInt(quantityInStock.getText()),
                            Integer.parseInt(minumunQunantityInStock.getText()),
                            Double.parseDouble(unitPrice.getText()),
                            Supplier.getText(),
                            notes.getText()
                    );

                    // Insert item stock in database and get ID
                    int idNewItemStock = DatabaseManager.insertItemStock(newStockItem);
                    newStockItem.setIdStockItem(idNewItemStock);
                    stockItemList.add(newStockItem);
                } else {
                    stockItem.setProductName(productName.getText());
                    stockItem.setProductCode(productCode.getText());
                    stockItem.setCategory(categoryComboBox.getValue());
                    stockItem.setQuantityInStock(Integer.parseInt(quantityInStock.getText()));
                    stockItem.setMinumunQunantityInStock(Integer.parseInt(minumunQunantityInStock.getText()));
                    stockItem.setUnitPrice(Double.parseDouble(unitPrice.getText()));
                    stockItem.setSupplier(Supplier.getText());
                    stockItem.setNotes(notes.getText());
                    DatabaseManager.updateItemStock(stockItem);
                }
                stage.close();
            }
            catch (Exception ex) {
                System.err.println("Error while saving item: " + ex.getMessage());
            }
        });

        // Cancel button
        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(e -> stage.close());

        // layout buttons
        HBox buttonLayout = new HBox(10, saveButton, cancelButton);
        formLayout.add(buttonLayout, 1, 9);

        // Set scene and show
        Scene scene = new Scene(formLayout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
