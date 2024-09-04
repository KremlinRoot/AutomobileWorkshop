package com.automobileapp.sotame.database;

import com.automobileapp.sotame.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:~/sotameDB";
    private static final String DB_USER = "user_sotame";
    private static final String DB_PASSWORD = "userpassword";

    // methods to get connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Methods to initialize database
    public static void initializeDatabase(){
        try(Connection conn = getConnection(); Statement stmt= conn.createStatement()){
            String createTableEmployee = """
                    CREATE TABLE IF NOT EXISTS EMPLOYEE (
                        idEmployee INT AUTO_INCREMENT PRIMARY KEY,
                        firstName VARCHAR(255),
                        lastName VARCHAR(255),
                        email VARCHAR(255),
                        phone VARCHAR(255),
                        jobTitle VARCHAR(255),
                        workedHours DOUBLE
                    );
                    """;
            stmt.execute(createTableEmployee);
        } catch (SQLException e) {
            System.err.println("Error al inicalizar base de datos: "+ e.getMessage());
        }
    }

    public static void insertEmployee(Employee employeeToInsert) throws SQLException{
        String insertEmployeeSQL = """
                INSERT INTO employee (firstName, lastName, email, phone, jobTitle, workedHours)
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertEmployeeSQL)){
            pstmt.setString(1, employeeToInsert.getFirstName());
            pstmt.setString(2, employeeToInsert.getLastName());
            pstmt.setString(3, employeeToInsert.getEmail());
            pstmt.setString(4, employeeToInsert.getPhone());
            pstmt.setString(5, employeeToInsert.getJobTitle());
            pstmt.setDouble(6, employeeToInsert.getWorkedHours());
            pstmt.executeUpdate();
        }
    }

    public static ObservableList<Employee> getAllEmployees() throws SQLException {
        ObservableList<Employee> employeesInTable = FXCollections.observableArrayList();
        String getAllEmployeesSQL = "SELECT * FROM employee";

        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getAllEmployeesSQL)){
            while(rs.next()){
                Employee employeeFromTable = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7));
                employeesInTable.add(employeeFromTable);
            }
        }
        return employeesInTable;
    }

    public static void deleteEmployee(int idEmployee) throws SQLException{
        String deleteEmployeeSQL = "DELETE FROM employee WHERE idEmployee = ?";
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteEmployeeSQL)){
            pstmt.setInt(1, idEmployee);
            pstmt.executeUpdate();
        }
    }

    public static void updateEmployee(Employee employeeToUpdate) throws SQLException{
        String updateEmployeeSQL = "UPDATE employee SET firstName = ?, lastName = ?, email = ?, phone = ?, jobTitle = ?, workedHours = ? WHERE idEmployee = ?";
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(updateEmployeeSQL)){
            pstmt.setString(1, employeeToUpdate.getFirstName());
            pstmt.setString(2, employeeToUpdate.getLastName());
            pstmt.setString(3, employeeToUpdate.getEmail());
            pstmt.setString(4, employeeToUpdate.getPhone());
            pstmt.setString(5, employeeToUpdate.getJobTitle());
            pstmt.setDouble(6, employeeToUpdate.getWorkedHours());
            pstmt.setInt(7, employeeToUpdate.getIdEmployee());
            pstmt.executeUpdate();
        }
    }
}
