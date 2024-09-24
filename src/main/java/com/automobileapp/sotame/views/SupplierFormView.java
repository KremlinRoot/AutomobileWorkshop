package com.automobileapp.sotame.views;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.Supplier;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class SupplierFormView {
    // Campos de clase
    private final Supplier supplier;
    private final ObservableList<Supplier> supplierList;

    // Constructor
    public SupplierFormView(Supplier supplier, ObservableList<Supplier> supplierList) {
        this.supplier = supplier;
        this.supplierList = supplierList;
    }

    // Método para mostrar el formulario
    public void show() {
        Stage stage = new Stage();
        stage.getIcons().addAll(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-16.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-24.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-32.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-64.png")))
        );
        stage.setTitle(supplier == null ? "Agregar Proveedor" : "Editar Proveedor");
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane formLayout = new GridPane();
        formLayout.setHgap(10);
        formLayout.setVgap(8);
        formLayout.setPadding(new Insets(10));

        // Campos de texto para todos los atributos del proveedor
        TextField firstNameField = new TextField(supplier != null ? supplier.getFirstNameSupplier() : "");
        TextField lastNameField = new TextField(supplier != null ? supplier.getLastNameSupplier() : "");
        TextField emailField = new TextField(supplier != null ? supplier.getEmailSupplier() : "");
        TextField phoneField = new TextField(supplier != null ? supplier.getPhoneSupplier() : "");
        TextField addressField = new TextField(supplier != null ? supplier.getAddressSupplier() : "");
        TextField cityField = new TextField(supplier != null ? supplier.getCitySupplier() : "");
        TextField provinceField = new TextField(supplier != null ? supplier.getProvinceSupplier() : "");
        TextField zipField = new TextField(supplier != null ? supplier.getZipSupplier() : "");
        TextField countryField = new TextField(supplier != null ? supplier.getCountrySupplier() : "");
        TextField companyNameField = new TextField(supplier != null ? supplier.getCompanyNameSupplier() : "");

        // Añadir etiquetas y campos de texto al formulario
        formLayout.add(new Label("Nombre(s):"), 0, 0);
        formLayout.add(firstNameField, 1, 0);
        formLayout.add(new Label("Apellidos:"), 0, 1);
        formLayout.add(lastNameField, 1, 1);
        formLayout.add(new Label("Email:"), 0, 2);
        formLayout.add(emailField, 1, 2);
        formLayout.add(new Label("Teléfono:"), 0, 3);
        formLayout.add(phoneField, 1, 3);
        formLayout.add(new Label("Calle:"), 0, 4);
        formLayout.add(addressField, 1, 4);
        formLayout.add(new Label("Ciudad:"), 0, 5);
        formLayout.add(cityField, 1, 5);
        formLayout.add(new Label("Provincia:"), 0, 6);
        formLayout.add(provinceField, 1, 6);
        formLayout.add(new Label("Código Postal:"), 0, 7);
        formLayout.add(zipField, 1, 7);
        formLayout.add(new Label("País:"), 0, 8);
        formLayout.add(countryField, 1, 8);
        formLayout.add(new Label("Nombre de la Compañía:"), 0, 9);
        formLayout.add(companyNameField, 1, 9);

        // Botón guardar
        Button saveButton = new Button("Guardar");
        saveButton.getStyleClass().add("button-crud");
        saveButton.setOnAction(e -> {
            try {
                if (supplier == null) {
                    // Crear nuevo proveedor
                    Supplier newSupplier = new Supplier(0,
                            firstNameField.getText(),
                            lastNameField.getText(),
                            emailField.getText(),
                            phoneField.getText(),
                            addressField.getText(),
                            cityField.getText(),
                            provinceField.getText(),
                            zipField.getText(),
                            countryField.getText(),
                            companyNameField.getText()
                    );
                    int generatedID = DatabaseManager.insertSupplier(newSupplier);
                    newSupplier.setIdSupplier(generatedID);
                    //DatabaseManager.insertSupplier(newSupplier);
                    supplierList.add(newSupplier);

                } else {
                    // Actualizar proveedor existente
                    supplier.setFirstNameSupplier(firstNameField.getText());
                    supplier.setLastNameSupplier(lastNameField.getText());
                    supplier.setEmailSupplier(emailField.getText());
                    supplier.setPhoneSupplier(phoneField.getText());
                    supplier.setAddressSupplier(addressField.getText());
                    supplier.setCitySupplier(cityField.getText());
                    supplier.setProvinceSupplier(provinceField.getText());
                    supplier.setZipSupplier(zipField.getText());
                    supplier.setCountrySupplier(countryField.getText());
                    supplier.setCompanyNameSupplier(companyNameField.getText());

                    DatabaseManager.updateSupplier(supplier); // Actualizar en la base de datos
                }
                stage.close();
            } catch (SQLException ex) {
                System.err.println("Error al insertar o actualizar el proveedor: " + ex.getMessage());
            }
        });

        formLayout.add(saveButton, 1, 10);
        Scene sceneSupplierForm = new Scene(formLayout, 450, 500);
        // Loading and applying style
        String stylesheet = Objects.requireNonNull(getClass().getResource("/MainStyle.css")).toExternalForm();
        sceneSupplierForm.getStylesheets().add(stylesheet);
        stage.setScene(sceneSupplierForm);
        stage.showAndWait();
    }
}
