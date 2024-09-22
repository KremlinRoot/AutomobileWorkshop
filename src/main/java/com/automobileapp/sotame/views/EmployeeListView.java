package com.automobileapp.sotame.views;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.Employee;
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

public class EmployeeListView {
    private final ObservableList<Employee> employeeList;
    private final TableView<Employee> tableViewEmployee;

    public EmployeeListView(){
        // init employee list
        employeeList = FXCollections.observableArrayList();

        // getting all employees from database
        try {
            employeeList.addAll(DatabaseManager.getAllEmployees());
        } catch (SQLException e) {
            System.err.println("Error al obtener empleados: "+ e.getMessage());
        }

        tableViewEmployee = new TableView<>(employeeList);

        // definition of table Columnns, reflecting employee class attributes
        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idEmployee"));
        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("Nombre");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Apellidos");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Employee, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Employee, String> phoneColumn = new TableColumn<>("Teléfono");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TableColumn<Employee, String> jobTitleColumn = new TableColumn<>("Cargo");
        jobTitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        TableColumn<Employee, Double> workedHoursColumn = new TableColumn<>("Horas Trabajadas");
        workedHoursColumn.setCellValueFactory(new PropertyValueFactory<>("workedHours"));

        // adding columns to table
        tableViewEmployee.getColumns().addAll(
                idColumn,
                firstNameColumn,
                lastNameColumn,
                emailColumn,
                phoneColumn,
                jobTitleColumn,
                workedHoursColumn
        );
    }

    public void show(Stage parentStage){
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // buttons to add, edit and deleter employees
        Button addButton = new Button("Agregar empleado");
        Button editButton = new Button("Editar empleado");
        Button deleteButton = new Button("Eliminar empleado");

        // set up button events
        // addbutton will open showEmployeeForm
        addButton.setOnAction(e -> showEmployeeForm(null));
        // editBUtton will open showEmployeeForm with selected employee
        editButton.setOnAction(e -> {
            Employee selectedEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
            if(selectedEmployee != null){
                showEmployeeForm(selectedEmployee); // pass selected employee
                try {
                    DatabaseManager.updateEmployee(selectedEmployee); // update selected employee
                    tableViewEmployee.refresh(); // refresh table view if we update values of employee
                } catch (SQLException ex) {
                    System.err.println("Error al actualizar el empleado" + ex.getMessage());
                }

            }
        });
        // deleteBUtton will delete selected employee
        deleteButton.setOnAction(e -> {
            Employee selectedEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
            if(selectedEmployee != null){
                try {
                    DatabaseManager.deleteEmployee(selectedEmployee.getIdEmployee());
                } catch (SQLException ex) {
                    System.err.println("Error al borrar empleado: "+ ex.getMessage());
                }
                employeeList.remove(selectedEmployee);
            }
        });

        // Hbox for buttons
        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10));

        layout.setCenter(tableViewEmployee);
        layout.setBottom(buttonBox);

        // set up scene and show it
        Stage stage = new Stage();
        stage.setTitle("Módulo Empleados");
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

    private void showEmployeeForm(Employee employee){
        EmployeeFormView formView = new EmployeeFormView(employee,employeeList);
        formView.show();
    }


}
