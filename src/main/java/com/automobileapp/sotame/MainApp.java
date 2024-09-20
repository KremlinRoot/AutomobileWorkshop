package com.automobileapp.sotame;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.views.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.h2.tools.Server;

import java.sql.SQLException;


public class MainApp extends Application {
    /**
     * Splash screen to start at begin of app
     */
    @Override
    public void start(Stage primaryStage) {
        /*
        start() is main entry point of application, class stage is to can build a window (top level container)
        scene class is container of all elements (content).
        root element of main scene is mainStackPane.
         */
        // Splash screen
        DatabaseManager.initializeDatabase(); // Init H" database
        startH2WebConsole();
        BorderPane mainLayout = new BorderPane();
        // Create sidebar for modules
        VBox sidebar = new VBox();
        // Create scroll pane for sidebar
        ScrollPane sidebarScrollPane = new ScrollPane(sidebar);
        sidebar.setSpacing(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setAlignment(Pos.CENTER);
        VBox.setVgrow(sidebar, Priority.ALWAYS);

        // Create buttons for modules
        Button btnEmployeeModule = new Button("Empleados");
        Button btnOrdersModule = new Button("Órdenes");
        Button btnManageStockModule = new Button("Control de Stock");
        Button btnSuppliersModule = new Button("Proveedores");
        Button btnDeliveryDateModule = new Button("Fechas de Entrega");
        Button btnBudgetModule = new Button("Presupuesto");
        // Adding buttons to sidebar
        sidebar.getChildren().addAll(
                btnEmployeeModule,
                btnOrdersModule,
                btnSuppliersModule,
                btnManageStockModule,
                btnDeliveryDateModule,
                btnBudgetModule
        );
        // Vbox children configurations
        for(Button button : sidebar.getChildren().stream().filter(node -> node instanceof Button).map(node -> (Button) node).toList()) {
            VBox.setVgrow(button,Priority.ALWAYS); // Able to buttons grow vertically-uniformly
            button.setMaxHeight(Double.MAX_VALUE); // Able to buttons grow vertically
            button.setPrefWidth(150);   // set a prefer width to buttons
            button.setPrefHeight(55);
        }
        // Configure Scroll pane for sidebar
        sidebarScrollPane.setFitToHeight(true);

        // Add sidebar to mainLayout
        mainLayout.setLeft(sidebarScrollPane);

        // Init scene with just the welcome menssage
        Label welcomeLabel = new Label("Bienvenido a MecanikRed'");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD,22));
        mainLayout.setCenter(welcomeLabel);

        Scene mainScene = new Scene(mainLayout, 1270, 720);
        primaryStage.setTitle("MecanikRed™");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        // calling modules from buttons
        btnEmployeeModule.setOnAction(e -> {
            EmployeeListView employeeListView = new EmployeeListView();
            employeeListView.show(primaryStage);
        });
        btnSuppliersModule.setOnAction(e -> {
            SupplierListView supplierListView = new SupplierListView();
            supplierListView.show(primaryStage);
        });
        btnOrdersModule.setOnAction(event -> {
            OrderListView orderListView = new OrderListView();
            orderListView.show(primaryStage);
        });
        btnManageStockModule.setOnAction(e -> {
            ItemStockListView itemStockListView = new ItemStockListView();
            itemStockListView.show(primaryStage);
        });
        btnDeliveryDateModule.setOnAction(e -> {
            DeliveryCalendar deliveryCalendar = new DeliveryCalendar();
            try {
                deliveryCalendar.showDeliveryCalendar();
            } catch (SQLException ex) {
                System.err.println("Error to open 'Fechas de Entrega' module " + ex.getMessage());
            }
        });
        btnBudgetModule.setOnAction(e -> {
            BudgetListView budgetListView = new BudgetListView();
            budgetListView.show(primaryStage);
        });

        // Configuration of styling
        String cssMainStage = getClass().getResource("/MainStyle.css").toExternalForm();
        mainScene.getStylesheets().add(cssMainStage);
        btnEmployeeModule.getStyleClass().add("module-button");
        btnOrdersModule.getStyleClass().add("module-button");
        btnSuppliersModule.getStyleClass().add("module-button");
        btnManageStockModule.getStyleClass().add("module-button");
        btnDeliveryDateModule.getStyleClass().add("module-button");
        btnBudgetModule.getStyleClass().add("module-button");
        sidebar.getStyleClass().add("module-sidebar");

    }

    public static void main(String[] args) {
        launch();
    }

    /**
     *
     */
    public void startH2WebConsole(){
        try{
            Server webServer = Server.createWebServer("-web","-webAllowOthers", "-webPort","8082").start();
            System.out.println("H2 console started at ..."+webServer.getURL());
        } catch(SQLException e) {

            System.out.println("H2 console error: "+e.getMessage());
        }
    }


// End of class
}
