package com.automobileapp.sotame.views;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.Employee;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EmployeeFormView {
    // fields
    private final Employee employee;
    private final ObservableList<Employee> employeeList;
    // constructor
    public EmployeeFormView(Employee employee, ObservableList<Employee> employeeList) {
        this.employee = employee;
        this.employeeList = employeeList;
    }

    public void show(){
        Stage stage = new Stage();
        stage.setTitle(employee == null ? "Agregar Empleado" : "Editar Empleado");
        stage.initModality(Modality.APPLICATION_MODAL);

        GridPane formLayout = new GridPane();
        formLayout.setHgap(10);
        formLayout.setVgap(8);
        formLayout.setPadding(new Insets(10));

        // text fields
        // TextField idField = new TextField(employee != null ? String.valueOf(employee.getIdEmployee()) : "");
        TextField firstNameField = new TextField(employee != null ? employee.getFirstName():"");
        TextField lastNameField = new TextField(employee != null ? employee.getLastName():"");
        TextField emailField = new TextField(employee != null ? employee.getEmail():"");
        TextField phoneField = new TextField(employee != null ? employee.getPhone():"");
        TextField jobTitleField = new TextField(employee != null ? employee.getJobTitle():"");
        TextField workedHoursField = new TextField(employee != null ? String.valueOf(employee.getWorkedHours()):"");

        // campos de texto
        // formLayout.add(new Label("ID:"), 0, 0);
        // formLayout.add(idField, 0, 0);
        formLayout.add(new Label("Nombre(s):"), 0, 0);
        formLayout.add(firstNameField, 1, 0);
        formLayout.add(new Label("Apellidos:"), 0, 1);
        formLayout.add(lastNameField, 1, 1);
        formLayout.add(new Label("Email:"), 0, 2);
        formLayout.add(emailField, 1, 2);
        formLayout.add(new Label("Teléfono:"), 0, 3);
        formLayout.add(phoneField, 1, 3);
        formLayout.add(new Label("Cargo:"), 0, 4);
        formLayout.add(jobTitleField, 1, 4);
        formLayout.add(new Label("Horas Trabajadas:"), 0, 5);
        formLayout.add(workedHoursField, 1, 5);

        // Botón guardar
        Button saveButton = new Button("Guardar");
        saveButton.setOnAction(e -> {
           try {
               double workedHours = Double.parseDouble(workedHoursField.getText());
               if(employee == null){
                   // create new employee
                   Employee newEmployee = new Employee(0, firstNameField.getText(), lastNameField.getText(), emailField.getText(), phoneField.getText(), jobTitleField.getText(), workedHours);
                   DatabaseManager.insertEmployee(newEmployee);
                   employeeList.add(newEmployee);
               } else {
                   // update employee
                   employee.setFirstName(firstNameField.getText());
                   employee.setLastName(lastNameField.getText());
                   employee.setEmail(emailField.getText());
                   employee.setPhone(phoneField.getText());
                   employee.setJobTitle(jobTitleField.getText());
                   employee.setWorkedHours(workedHours);
               }
               stage.close();
           } catch (NumberFormatException ex){
               System.err.println("Error: ID y Horas Trabajadas deben ser numéricos." + ex.getMessage());
           } catch (SQLException ex) {
               System.err.println("Error al insertar o actualizar el empleado. " + ex.getMessage());
           }
        });

        formLayout.add(saveButton, 1, 6);
        Scene scene = new Scene(formLayout,400,300);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
