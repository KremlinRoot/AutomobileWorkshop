package com.automobileapp.sotame;

import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.StatusOrder;
import com.automobileapp.sotame.views.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.h2.tools.Server;

import javax.swing.text.PlainView;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;


public class MainApp extends Application {
    // CLASS VARIABLES
    Label timeLabel,pendingOrdersLabel,inProgressOrdersLabel,completedOrdersLabel, canceledOrdersLabel, deliveredOrdersLabel;

    /**
     * Start app and initialize database for app and web h2
     */
    @Override
    public void start(Stage primaryStage){
        // Load icons of primary stage
        primaryStage.getIcons().addAll(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-16.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-24.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-32.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-64.png")))
        );
        // Create splash screen
        StackPane splashLayout = new StackPane(); 
        ImageView splashImage = new ImageView(new Image(getClass().getResource("/images/splash-screen.png").toExternalForm()));
        splashLayout.getChildren().add(splashImage);
        Scene splashScene = new Scene(splashLayout,850,315);

        // Init stage
        Stage initStage = new Stage();
        initStage.setScene(splashScene);
        initStage.show();

        // Loading database and end splash screen run app
        new Thread(() -> {
            try{
                DatabaseManager.initializeDatabase(); // Init H" database
                startH2WebConsole();
                Thread.sleep(0);

            } catch(InterruptedException ex) {
                System.err.println("Error while initializing application: "+ "method: "+"initApplication()" +"into init Thread" + ex.getMessage());
            } finally{
                javafx.application.Platform.runLater(() -> {
                    initStage.close();
                    MainApplicationInit(primaryStage);
                });
            }
        }).start();
    }

    private void MainApplicationInit(Stage primaryStage) {
        // Adding icons to stage
        // Class variables
        /*
        start() is main entry point of application, class stage is to can build a window (top level container)
        scene class is container of all elements (content).
        root element of main scene is mainStackPane.
         */

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
        Label welcomeLabel = new Label("Bienvenido a MecanikRed");

        // Time label
        timeLabel = new Label();
        updateTimeLabel();
        startClock();

        // Labels for orders
        processForUpdateOrdeLabelsInMainPage();
        pendingOrdersLabel = new Label("ORDENES PENDIENTES: 0");
        inProgressOrdersLabel = new Label("ORDENES EN PROGRESO: 0");
        completedOrdersLabel = new Label("ORDENES COMPLETADAS: 0");
        canceledOrdersLabel = new Label("ORDENES CANCELADAS: 0");
        deliveredOrdersLabel = new Label("ORDENES ENTREGADAS: 0");

        // Create layout for main content
        VBox mainContent = new VBox(10, welcomeLabel, timeLabel, pendingOrdersLabel, inProgressOrdersLabel, completedOrdersLabel, canceledOrdersLabel, deliveredOrdersLabel);
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setPadding(new Insets(20));
        mainLayout.setCenter(mainContent);

        // Adding main layout to scene
        Scene mainScene = new Scene(mainLayout, 1270, 720);
        primaryStage.setTitle("MecanikRed©");
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

        // Orders label
        pendingOrdersLabel.getStyleClass().add("orders-labels");
        inProgressOrdersLabel.getStyleClass().add("orders-labels");
        completedOrdersLabel.getStyleClass().add("orders-labels");
        canceledOrdersLabel.getStyleClass().add("orders-labels");
        deliveredOrdersLabel.getStyleClass().add("orders-labels");
        // Welcome to MecanikRed
        welcomeLabel.getStyleClass().add("welcome-message");
        timeLabel.getStyleClass().add("time-label");
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimeLabel()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private void updateTimeLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-YYYY HH:mm:ss",new Locale("es", "ES"));
        String currentTime = LocalDateTime.now().format(formatter);
        timeLabel.setText(currentTime);
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

    private void updateOrderofLabelIntoMainPage(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{
                    int pendingOrders = DatabaseManager.getOrderByStatusForMainPage(String.valueOf(StatusOrder.PENDIENTE));
                    int inProgressOrders = DatabaseManager.getOrderByStatusForMainPage(String.valueOf(StatusOrder.PROGRESO));
                    int completedOrders = DatabaseManager.getOrderByStatusForMainPage(String.valueOf(StatusOrder.COMPLETADO));
                    int canceledOrders = DatabaseManager.getOrderByStatusForMainPage(String.valueOf(StatusOrder.CANCELADO));
                    int deliveredOrders = DatabaseManager.getOrderByStatusForMainPage(String.valueOf(StatusOrder.ENTREGADO));
                    Platform.runLater(() -> {
                        pendingOrdersLabel.setText("ORDENES PENDIENTES: " + pendingOrders);
                        inProgressOrdersLabel.setText("ORDENES EN PROGESO: " + inProgressOrders);
                        completedOrdersLabel.setText("ORDENES COMPLETADAS: " + completedOrders);
                        canceledOrdersLabel.setText("ORDENES CANCELADAS: " + canceledOrders);
                        deliveredOrdersLabel.setText("ORDENES ENTREGADAS: " + deliveredOrders);
                    });
                } catch (SQLException e)
                {
                    System.err.println("Error while updating ORDERS for main page label! " + e.getMessage());
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    public void processForUpdateOrdeLabelsInMainPage(){
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(3), event -> updateOrderofLabelIntoMainPage()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }


// End of class
}
