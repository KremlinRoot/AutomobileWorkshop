package com.automobileapp.sotame.database;

import com.automobileapp.sotame.models.Employee;
import com.automobileapp.sotame.models.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:~/sotameDB";
    private static final String DB_USER = "user_sotame";
    private static final String DB_PASSWORD = "userpassword";

    // methods to get connection

    /**
     * Execute connection to database
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Method to initialize database
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

            String createTableSupplier = """
                    CREATE TABLE IF NOT EXISTS SUPPLIER (
                        idSupplier INT AUTO_INCREMENT PRIMARY KEY,
                        firstNameSupplier VARCHAR(255),
                        lastNameSupplier VARCHAR(255),
                        emailSupplier VARCHAR(255),
                        phoneSupplier VARCHAR(50),
                        addressSupplier VARCHAR(255),
                        citySupplier VARCHAR(100),
                        provinceSupplier VARCHAR(100),
                        zipSupplier VARCHAR(20),
                        countrySupplier VARCHAR(100),
                        companyNameSupplier VARCHAR(255)
                    )
                    """;
            stmt.execute(createTableSupplier);

            String createTableAutomobile = """
                    CREATE TABLE IF NOT EXISTS AUTOMOBILE (
                        idAutomobile INT AUTO_INCREMENT PRIMARY KEY,
                        manufacturer VARCHAR(255),
                        model VARCHAR(255),
                        yearManufactured VARCHAR(255),
                        color VARCHAR(255),
                        numberPlate VARCHAR(255),
                        numberVin VARCHAR(255),
                        kilometers INT,
                        fuelType VARCHAR(255),
                        currentState VARCHAR(255),
                        lastMaintenance DATE,
                        nextMaintenance DATE
                    )
                    """;
            stmt.execute(createTableAutomobile);

            

        } catch (SQLException e) {
            System.err.println("Error al inicalizar base de datos: "+ e.getMessage());
        }
    }
    // CRUD Employee

    /**
     * Insert a new employee into the database
     * @param employeeToInsert
     * @throws SQLException
     */
    public static int insertEmployee(Employee employeeToInsert) throws SQLException{
        String insertEmployeeSQL = """
                INSERT INTO employee (firstName, lastName, email, phone, jobTitle, workedHours)
                VALUES (?, ?, ?, ?, ?, ?);
                """;
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertEmployeeSQL, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, employeeToInsert.getFirstName());
            pstmt.setString(2, employeeToInsert.getLastName());
            pstmt.setString(3, employeeToInsert.getEmail());
            pstmt.setString(4, employeeToInsert.getPhone());
            pstmt.setString(5, employeeToInsert.getJobTitle());
            pstmt.setDouble(6, employeeToInsert.getWorkedHours());
            pstmt.executeUpdate();

            // get the generated ID and set it on the object
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                } else{
                    throw new SQLException("We cannot get ID of new employee");
                }
            }
        }
    }

    /**
     * Get all employees from the database
     * @return
     * @throws SQLException
     */
    public static ObservableList<Employee> getAllEmployees() throws SQLException {
        ObservableList<Employee> employeesInTable = FXCollections.observableArrayList();
        String getAllEmployeesSQL = "SELECT * FROM employee";

        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getAllEmployeesSQL)){
            while(rs.next()){
                Employee employeeFromTable = new Employee(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getDouble(7));
                employeesInTable.add(employeeFromTable);
            }
        }
        return employeesInTable;
    }

    /**
     * Delete an employee from the database
     * @param idEmployee
     * @throws SQLException
     */
    public static void deleteEmployee(int idEmployee) throws SQLException{
        String deleteEmployeeSQL = "DELETE FROM employee WHERE idEmployee = ?";
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteEmployeeSQL)){
            pstmt.setInt(1, idEmployee);
            pstmt.executeUpdate();
        }
    }

    /**
     * Update an employee in the database
     * @param employeeToUpdate
     * @throws SQLException
     */
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
    // CRUD Supplier
    /**
     * Inserts a new supplier into the database
     *
     * @param supplierToInsert
     * @return
     * @throws SQLException
     */
    public static int insertSupplier(Supplier supplierToInsert) throws SQLException{
        String insertSupplierSQL = """
                INSERT INTO supplier (firstNameSupplier, lastNameSupplier, emailSupplier, phoneSupplier, addressSupplier, citySupplier, provinceSupplier, zipSupplier, countrySupplier, companyNameSupplier)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertSupplierSQL,Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, supplierToInsert.getFirstNameSupplier());
            pstmt.setString(2, supplierToInsert.getLastNameSupplier());
            pstmt.setString(3, supplierToInsert.getEmailSupplier());
            pstmt.setString(4, supplierToInsert.getPhoneSupplier());
            pstmt.setString(5, supplierToInsert.getAddressSupplier());
            pstmt.setString(6, supplierToInsert.getCitySupplier());
            pstmt.setString(7, supplierToInsert.getProvinceSupplier());
            pstmt.setString(8, supplierToInsert.getZipSupplier());
            pstmt.setString(9, supplierToInsert.getCountrySupplier());
            pstmt.setString(10, supplierToInsert.getCompanyNameSupplier());
            pstmt.executeUpdate();

            // get the generated ID and set it on the object
            try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No ID obtained from insert");
                }
            }
        }
    }

    public static ObservableList<Supplier> getAllSuppliers() throws SQLException {
        ObservableList<Supplier> suppliersInTable = FXCollections.observableArrayList();
        String getAllSuppliersSQL = "SELECT * FROM supplier";

        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getAllSuppliersSQL)){
            while(rs.next()){
                Supplier supplierFromTable = new Supplier(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11));
                suppliersInTable.add(supplierFromTable);
            }

        }
        return suppliersInTable;
    }

    public static void deleteSupplier(int idSupplier) throws SQLException{
        String deleteSupplierSQL = "DELETE FROM supplier WHERE idSupplier = ?";
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteSupplierSQL)){
            pstmt.setInt(1, idSupplier);
            pstmt.executeUpdate();
        }
    }

    /**
     * Updated an supplier in the database
     * @param supplierToUpdate
     * @throws SQLException
     */
    public static void updateSupplier(Supplier supplierToUpdate) throws SQLException{
        String updateSupplier = """
                UPDATE supplier SET firstNameSupplier = ?, lastNameSupplier = ?, emailSupplier = ?, phoneSupplier = ?, addressSupplier = ?, citySupplier = ?, stateSupplier = ?, zipSupplier = ?, countrySupplier = ?, companyNameSupplier = ?                
                """;

        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(updateSupplier)){
            pstmt.setString(1, supplierToUpdate.getFirstNameSupplier());
            pstmt.setString(2, supplierToUpdate.getLastNameSupplier());
            pstmt.setString(3, supplierToUpdate.getEmailSupplier());
            pstmt.setString(4, supplierToUpdate.getPhoneSupplier());
            pstmt.setString(5, supplierToUpdate.getAddressSupplier());
            pstmt.setString(6, supplierToUpdate.getCitySupplier());
            pstmt.setString(7, supplierToUpdate.getProvinceSupplier());
            pstmt.setString(8, supplierToUpdate.getZipSupplier());
            pstmt.setString(9, supplierToUpdate.getCountrySupplier());
            pstmt.setString(10, supplierToUpdate.getCompanyNameSupplier());
            pstmt.executeUpdate();
        }
    }
}
