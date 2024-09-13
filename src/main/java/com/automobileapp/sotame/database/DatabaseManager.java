package com.automobileapp.sotame.database;

import com.automobileapp.sotame.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:./sotameDB;AUTO_SERVER=TRUE";
    private static final String DB_USER = "mecanikred";
    private static final String DB_PASSWORD = "admin";

    // methods to get connection

    /**
     * Execute connection to database
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.h2.Driver());
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
                        phone VARCHAR(20),
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
                        phoneSupplier VARCHAR(20),
                        addressSupplier VARCHAR(255),
                        citySupplier VARCHAR(255),
                        provinceSupplier VARCHAR(255),
                        zipSupplier VARCHAR(10),
                        countrySupplier VARCHAR(255),
                        companyNameSupplier VARCHAR(255)
                    );
                    """;
            stmt.execute(createTableSupplier);

            String createTableAutomobile = """
                    CREATE TABLE IF NOT EXISTS AUTOMOBILE (
                        idAutomobile INT AUTO_INCREMENT PRIMARY KEY,
                        manufacturer VARCHAR(255),
                        model VARCHAR(255),
                        yearManufactured VARCHAR(10),
                        color VARCHAR(50),
                        numberPlate VARCHAR(50),
                        numberVin VARCHAR(50),
                        kilometers INT,
                        fuelType VARCHAR(50),
                        currentState VARCHAR(255),
                        lastMaintenance DATE,
                        nextMaintenance DATE
                    );
                    """;
            stmt.execute(createTableAutomobile);

            String createTableCustomer = """
                    CREATE TABLE IF NOT EXISTS CUSTOMER (
                        idCustomer INT AUTO_INCREMENT PRIMARY KEY,
                        firstNameCustomer VARCHAR(255),
                        lastNameCustomer VARCHAR(255),
                        emailCustomer VARCHAR(255),
                        phoneCustomer VARCHAR(20),
                        addressCustomer VARCHAR(255),
                        cityCustomer VARCHAR(255),
                        provinceCustomer VARCHAR(255),
                        zipCustomer VARCHAR(10),
                        countryCustomer VARCHAR(255),
                        customerType VARCHAR(50),
                        dateOfBirthCustomer DATE
                    );
                    """;
            stmt.execute(createTableCustomer);

            String createTableOrder = """
                    CREATE TABLE IF NOT EXISTS ORDER_WORK (
                        idOrder INT AUTO_INCREMENT PRIMARY KEY,
                        orderNumber VARCHAR(255),
                        orderDate DATE,
                        estimatedCompletionDate DATE,
                        workDescription VARCHAR(800),
                        totalCost DOUBLE,
                        statusOrder VARCHAR(255),
                        idAutomobile INT,
                        idCustomer INT,
                        FOREIGN KEY (idAutomobile) REFERENCES AUTOMOBILE(idAutomobile),
                        FOREIGN KEY (idCustomer) REFERENCES CUSTOMER(idCustomer)
                    );
                    """;
            stmt.execute(createTableOrder);

            String createTableStockItem = """
                    CREATE TABLE IF NOT EXISTS STOCK_ITEM (
                        idStockItem INT AUTO_INCREMENT PRIMARY KEY,
                        productName VARCHAR(255),
                        productCode VARCHAR(255),
                        category VARCHAR(255),
                        quantityInStock INT,
                        minimumQuantityInStock INT,
                        unitPrice DOUBLE,
                        supplier VARCHAR(255),
                        totalCost DOUBLE,
                        notes VARCHAR(255)
                    );
                    """;
            stmt.execute(createTableStockItem);

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

    public static Employee getEmployeeById(int idEmployee, Connection conn) throws SQLException {
        String getEmployeeByIdSQL = "SELECT * FROM employee WHERE idEmployee = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(getEmployeeByIdSQL)){
            pstmt.setInt(1, idEmployee);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Employee employeeFromTable = new Employee();
                    employeeFromTable.setIdEmployee(rs.getInt("idEmployee"));
                    employeeFromTable.setFirstName("firstName");
                    employeeFromTable.setLastName("lastName");
                    employeeFromTable.setEmail("email");
                    employeeFromTable.setPhone("phone");
                    employeeFromTable.setJobTitle("jobTitle");
                    employeeFromTable.setWorkedHours(rs.getDouble("workedHours"));
                    return employeeFromTable;

                }
            }
        } catch (SQLException e) {
            System.err.print("Error while getting employee by id: " +" methos: getEmployeeById(int idEmployee, Connection conn)"+ e.getMessage());
        }
        return null;
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
                    throw new SQLException("No ID obtained from insertSupplier");
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

    // Orders CRUD

    public static int insertOrder(Order order) throws SQLException {
        String insertOrderSQL = """
            INSERT INTO order_work (orderNumber, orderDate, estimatedCompletionDate, workDescription, totalCost, statusOrder, idAutomobile, idCustomer)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, order.getOrderNumber());
            pstmt.setDate(2, Date.valueOf(order.getOrderDate()));
            pstmt.setDate(3, Date.valueOf(order.getEstimatedCompletionDate()));
            pstmt.setString(4, order.getWorkDescription());
            pstmt.setDouble(5, order.getTotalCost());
            pstmt.setString(6, order.getStatusOrder().toString());

            // Obtiene los IDs de Automobile y Customer para asignar la relación
            pstmt.setInt(7, order.getAutomobileOfOrder().getIdAutomobile());
            pstmt.setInt(8, order.getCustomerOfOrder().getIdCustomer());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No ID obtained from insertOrder");
                }
            }
        }
    }

    public static boolean updateOrder(Order order) throws SQLException {
        String updateOrderSQL = """
            UPDATE order_work 
            SET orderNumber = ?, orderDate = ?, estimatedCompletionDate = ?, workDescription = ?, totalCost = ?, statusOrder = ?, idAutomobile = ?, idCustomer = ?
            WHERE idOrder = ?;
            """;

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(updateOrderSQL)) {
            pstmt.setString(1, order.getOrderNumber());
            pstmt.setDate(2, Date.valueOf(order.getOrderDate()));
            pstmt.setDate(3, Date.valueOf(order.getEstimatedCompletionDate()));
            pstmt.setString(4, order.getWorkDescription());
            pstmt.setDouble(5, order.getTotalCost());
            pstmt.setString(6, order.getStatusOrder().toString());
            pstmt.setInt(7, order.getAutomobileOfOrder().getIdAutomobile());
            pstmt.setInt(8, order.getCustomerOfOrder().getIdCustomer());

            pstmt.setInt(9, order.getIdOrder());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar la orden: " + e.getMessage());
            return false;
        }
    }

    public static void deleteOrder(int idOrder) throws SQLException {
        String deleteOrderSQL = "DELETE FROM order_work WHERE idOrder = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteOrderSQL)) {
            pstmt.setInt(1, idOrder);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar la orden: " + e.getMessage());
        }
    }

    public static Automobile getAutomobile(String manufacturer, String model, String plates) throws SQLException {
        String getAutomobileSQL = """
            SELECT * FROM AUTOMOBILE WHERE manufacturer = ? AND model = ? AND numberPlate = ?;
            """;

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(getAutomobileSQL)) {
            pstmt.setString(1, manufacturer);
            pstmt.setString(2, model);
            pstmt.setString(3, plates);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Automobile automobile = new Automobile();
                    automobile.setIdAutomobile(rs.getInt("idAutomobile"));
                    automobile.setManufacturer(rs.getString("manufacturer"));
                    automobile.setModel(rs.getString("model"));
                    automobile.setYearManufactured(rs.getString("yearManufactured"));
                    automobile.setColor(rs.getString("color"));
                    automobile.setNumberPlate(rs.getString("numberPlate"));
                    automobile.setNumberVin(rs.getString("numberVin"));
                    automobile.setKilometers(rs.getInt("kilometers"));
                    automobile.setFuelType(Fuel.valueOf(rs.getString("fuelType")));
                    automobile.setCurrentState(rs.getString("currentState"));
                    automobile.setLastMaintenance(rs.getDate("lastMaintenance").toLocalDate());
                    automobile.setNextMaintenance(rs.getDate("nextMaintenance").toLocalDate());

                    return automobile;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el automóvil: " + e.getMessage());
        }
        return null;
    }

    public static Customer getCustomer(String firstName, String lastName) throws SQLException {
        String getCustomerSQL = """
            SELECT * FROM CUSTOMER WHERE firstNameCustomer = ? AND lastNameCustomer = ?;
            """;

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(getCustomerSQL)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setIdCustomer(rs.getInt("idCustomer"));
                    customer.setFirstNameCustomer(rs.getString("firstNameCustomer"));
                    customer.setLastNameCustomer(rs.getString("lastNameCustomer"));
                    customer.setDateOfBirthCustomer(rs.getDate("dateOfBirthCustomer").toLocalDate());
                    customer.setEmailCustomer(rs.getString("emailCustomer"));
                    customer.setPhoneCustomer(rs.getString("phoneCustomer"));
                    customer.setAddressCustomer(rs.getString("addressCustomer"));
                    customer.setCityCustomer(rs.getString("cityCustomer"));
                    customer.setProvinceCustomer(rs.getString("provinceCustomer"));
                    customer.setZipCustomer(rs.getString("zipCustomer"));
                    customer.setCountryCustomer(rs.getString("countryCustomer"));

                    return customer;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el cliente: " + e.getMessage());
        }
        return null;
    }

    public static List<Order> getAllOrders() throws SQLException {
        // Consulta SQL para recuperar todas las órdenes junto con los datos de automóvil y cliente
        String getAllOrdersSQL = """
                SELECT o.idOrder, o.orderNumber, o.orderDate, o.estimatedCompletionDate, o.workDescription, 
                       o.totalCost, o.statusOrder, 
                       o.idAutomobile, a.manufacturer, a.model, a.yearManufactured, a.color, a.numberPlate, 
                       o.idCustomer, c.firstNameCustomer, c.lastNameCustomer, c.emailCustomer
                FROM order_work o
                JOIN AUTOMOBILE a ON o.idAutomobile = a.idAutomobile
                JOIN CUSTOMER c ON o.idCustomer = c.idCustomer;
                """;

        // Lista para almacenar las órdenes recuperadas
        List<Order> orderList = new ArrayList<>();

        // Intento de conexión y ejecución de la consulta
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(getAllOrdersSQL);
             ResultSet rs = pstmt.executeQuery()) {

            // Recorrido por los resultados para crear objetos Order y agregar a la lista
            while (rs.next()) {
                // Creación de objetos relacionados: Automobile y Customer
                Automobile automobile = new Automobile();
                automobile.setIdAutomobile(rs.getInt("idAutomobile"));
                automobile.setManufacturer(rs.getString("manufacturer"));
                automobile.setModel(rs.getString("model"));
                automobile.setYearManufactured(rs.getString("yearManufactured"));
                automobile.setColor(rs.getString("color"));
                automobile.setNumberPlate(rs.getString("numberPlate"));

                Customer customer = new Customer();
                customer.setIdCustomer(rs.getInt("idCustomer"));
                customer.setFirstNameCustomer(rs.getString("firstNameCustomer"));
                customer.setLastNameCustomer(rs.getString("lastNameCustomer"));
                customer.setEmailCustomer(rs.getString("emailCustomer"));

                // Creación del objeto Order con la información obtenida
                Order order = new Order();
                order.setIdOrder(rs.getInt("idOrder"));
                order.setOrderNumber(rs.getString("orderNumber"));
                order.setOrderDate(rs.getDate("orderDate").toLocalDate());
                order.setEstimatedCompletionDate(rs.getDate("estimatedCompletionDate").toLocalDate());
                order.setWorkDescription(rs.getString("workDescription"));
                order.setTotalCost(rs.getDouble("totalCost"));
                order.setStatusOrder(StatusOrder.valueOf(rs.getString("statusOrder")));
                order.setAutomobileOfOrder(automobile);
                order.setCustomerOfOrder(customer);

                // Añadir la orden a la lista
                orderList.add(order);
            }
        } catch (SQLException e) {
            // Manejo de excepciones
            System.err.println("Error al obtener las órdenes: " + e.getMessage());
        }

        // Retorno de la lista de órdenes
        return orderList;
    }

    public static int insertAutomobileIntoNewOrderForm(Automobile automobile) throws SQLException {
        String insertAutomobileSQL = """
                INSERT INTO AUTOMOBILE (manufacturer, model, numberPlate, yearManufactured) VALUES (?, ?, ?,?);
                """;
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertAutomobileSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, automobile.getManufacturer());
            pstmt.setString(2, automobile.getModel());
            pstmt.setString(3, automobile.getNumberPlate());
            pstmt.setString(4, automobile.getYearManufactured());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No ID obtained from insertAutomobileIntoNewOrderForm");
                }
            }
        }


    }

    public static int insertCustomerIntoNewOrderForm(Customer customer) throws SQLException {
        String insertCustomerSQL= """
                INSERT INTO CUSTOMER (firstNameCustomer, lastNameCustomer, emailCustomer) VALUES (?, ?, ?);
                """;
        try(Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, customer.getFirstNameCustomer());
            pstmt.setString(2, customer.getLastNameCustomer());
            pstmt.setString(3, customer.getEmailCustomer());
            pstmt.executeUpdate();

            try(ResultSet rs = pstmt.getGeneratedKeys()) {
                if(rs.next()){
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No ID obtained from insertCustomerIntoNewOrderForm");
                }
            }
        }
    }

    public static ObservableList<StockItem> getAllItemStock() throws SQLException {
        ObservableList<StockItem> itemStockListInTable = FXCollections.observableArrayList();
        String getAllItemStockSQL = """
                SELECT * FROM STOCK_ITEM;
                """;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(getAllItemStockSQL)) {
            while (rs.next()) {
                StockItem stockItemsFromTable = new StockItem(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        Category.valueOf(rs.getString(4)),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getString(8),
                        rs.getString(10));
                itemStockListInTable.add(stockItemsFromTable);
            }
        }
        return itemStockListInTable;
    }

    public static int insertItemStock(StockItem newStockItem) throws SQLException {
        String insertItemStockSQL = """
                INSERT INTO STOCK_ITEM (productName, productCode, category, quantityInStock, minimumQuantityInStock, unitPrice, supplier, totalCost, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertItemStockSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, newStockItem.getProductName());
            pstmt.setString(2, newStockItem.getProductCode());
            pstmt.setString(3, newStockItem.getCategory().toString());
            pstmt.setInt(4, newStockItem.getQuantityInStock());
            pstmt.setInt(5, newStockItem.getMinumunQunantityInStock());
            pstmt.setDouble(6, newStockItem.getUnitPrice());
            pstmt.setString(7, newStockItem.getSupplier());
            pstmt.setDouble(8, newStockItem.getTotalCost());
            pstmt.setString(9, newStockItem.getNotes());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No ID obtained from insertItemStock");
                }
            }
        }
    }

    public static void updateItemStock(StockItem stockItem) throws SQLException {
        String updateItemStockSQL = """
                UPDATE STOCK_ITEM
                SET productName = ?, productCode = ?, category = ?, quantityInStock = ?, minimumQuantityInStock = ?, unitPrice = ?, supplier = ?,  totalCost = ?,notes = ?
                WHERE idStockItem = ?;
                """;
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(updateItemStockSQL)) {
            pstmt.setString(1, stockItem.getProductName());
            pstmt.setString(2, stockItem.getProductCode());
            pstmt.setString(3, stockItem.getCategory().toString());
            pstmt.setInt(4, stockItem.getQuantityInStock());
            pstmt.setInt(5, stockItem.getMinumunQunantityInStock());
            pstmt.setDouble(6, stockItem.getUnitPrice());
            pstmt.setString(7, stockItem.getSupplier());
            pstmt.setDouble(8, stockItem.getTotalCost());
            pstmt.setString(9, stockItem.getNotes());
            pstmt.setInt(10, stockItem.getIdStockItem());
            pstmt.executeUpdate();
        }
    }

    public static void deleteItemStock(StockItem deletedItem) throws SQLException {
        String deleteItemStockSQL = """
                DELETE FROM STOCK_ITEM
                WHERE idStockItem = ?;
                """;
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(deleteItemStockSQL)) {
            pstmt.setInt(1, deletedItem.getIdStockItem());
            pstmt.executeUpdate();
        }
    }
// end class
}