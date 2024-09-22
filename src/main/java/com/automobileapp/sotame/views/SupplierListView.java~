package com.automobileapp.sotame.views;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SupplierListView {
    private final ObservableList<Supplier> supplierList;
    private final TableView<Supplier> tableViewSupplier;

    public SupplierListView(){
        supplierList = FXCollections.observableArrayList();

        try{
            supplierList.addAll(DatabaseManager.getAllSuppliers());
        } catch (SQLException e) {
            System.err.println("Error al obtener los proveedores" + e.getMessage());
        }

        tableViewSupplier = new TableView<>(supplierList);

        // Definition of columns
        TableColumn<Supplier, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idSupplier"));
        TableColumn<Supplier, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstNameSupplier"));
        TableColumn<Supplier, String> lastNameColumn = new TableColumn<>("Apellidos");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastNameSupplier"));
        TableColumn<Supplier, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailSupplier"));
        TableColumn<Supplier, String> phoneColumn = new TableColumn<>("Teléfono");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneSupplier"));
        TableColumn<Supplier, String> addressColumn = new TableColumn<>("Dirección");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("addressSupplier"));
        TableColumn<Supplier, String> cityColumn = new TableColumn<>("Ciudad");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("citySupplier"));
        TableColumn<Supplier, String> stateColumn = new TableColumn<>("Provincia");
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("provinceSupplier"));
        TableColumn<Supplier, String> zipColumn = new TableColumn<>("Código Postal");
        zipColumn.setCellValueFactory(new PropertyValueFactory<>("zipSupplier"));
        TableColumn<Supplier, String> countryColumn = new TableColumn<>("País");
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("countrySupplier"));
        TableColumn<Supplier, String> companyNameColumn = new TableColumn<>("Compañía");
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyNameSupplier"));

        // Adding columns to table
        tableViewSupplier.getColumns().addAll(
                idColumn,
                nameColumn,
                lastNameColumn,
                emailColumn,
                phoneColumn,
                addressColumn,
                cityColumn,
                stateColumn,
                zipColumn,
                countryColumn,
                companyNameColumn
        );
    }

    public void show(Stage parentSateg){
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // Buttons to add, edit and delete suppliers
        Button addButton = new Button("Agregar proveedor");
        Button editButton = new Button("Editar proveedor");
        Button deleteButton = new Button("Eliminar proveedor");

        // Set up button events
        // Add button will open showSupplierForm
        addButton.setOnAction(e -> showSupplierForm(null));
        // Edit button will open showSupplierForm with selected supplier
        editButton.setOnAction(e -> {
            Supplier selectedSupplier = tableViewSupplier.getSelectionModel().getSelectedItem();
            if(selectedSupplier != null){
                showSupplierForm(selectedSupplier); // pass selected supplier
                try {
                    DatabaseManager.updateSupplier(selectedSupplier); // update selected supplier
                    tableViewSupplier.refresh(); // refresh table view if we update values of supplier
                } catch (SQLException ex) {
                    System.err.println("Error al actualizar el proveedor" + ex.getMessage());
                }
            }
        });
        // Delete button will delete selected supplier
        deleteButton.setOnAction(e -> {
            Supplier selectedSupplier = tableViewSupplier.getSelectionModel().getSelectedItem();
            if(selectedSupplier != null){
                try {
                    DatabaseManager.deleteSupplier(selectedSupplier.getIdSupplier());
                    supplierList.remove(selectedSupplier);
                    tableViewSupplier.refresh();
                } catch (SQLException ex) {
                    System.err.println("Error al eliminar el proveedor" + ex.getMessage());
                }
            }
        });

        // Add buttons to HBox
        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10));

        layout.setCenter(tableViewSupplier);
        layout.setBottom(buttonBox);

        // Set up scene
        Stage stage = new Stage();
        stage.setTitle("Módulo Proveedores");
        stage.setScene(new Scene(layout, 800, 600));
        stage.initOwner(parentSateg);
        stage.show();
    }

    private void showSupplierForm(Supplier supplier){
        SupplierFormView supplierForm = new SupplierFormView(supplier, supplierList);
        supplierForm.show();
    }

}
