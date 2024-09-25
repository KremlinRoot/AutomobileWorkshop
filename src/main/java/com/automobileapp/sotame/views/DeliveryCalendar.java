package com.automobileapp.sotame.views;

import com.automobileapp.sotame.models.StatusOrder;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;
import com.automobileapp.sotame.database.DatabaseManager;
import com.automobileapp.sotame.models.Order;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DeliveryCalendar {
    private Locale locale = new Locale("es","MX");
    private LocalDate currentWeekStart = LocalDate.now().with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1); // Lunes actual
    public void showDeliveryCalendar() throws SQLException {
        // Crear el escenario principal
        Stage calendarStage = new Stage();
        calendarStage.setTitle("Calendario de Entregas");
        calendarStage.getIcons().addAll(
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-16.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-24.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-32.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/wrench-64.png")))
        );

        // Crear un VBox para el diseño principal
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setFillWidth(true); // Asegurarse de que el VBox llene todo el ancho

        // Crear un HBox para los botones de navegación entre semanas
        HBox navigationBox = new HBox();
        navigationBox.setSpacing(10);
        navigationBox.setAlignment(Pos.CENTER);

        // Botón para la semana anterior
        Button previousWeekButton = new Button("<");
        previousWeekButton.setOnAction(e -> {
            currentWeekStart = currentWeekStart.minusWeeks(1); // Retroceder una semana
            try {
                updateCalendar(mainLayout); // Actualizar calendario
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Botón para la semana siguiente
        Button nextWeekButton = new Button(">");
        nextWeekButton.setOnAction(e -> {
            currentWeekStart = currentWeekStart.plusWeeks(1); // Avanzar una semana
            try {
                updateCalendar(mainLayout); // Actualizar calendario
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Etiqueta para mostrar la semana actual
        Label weekLabel = new Label("Semana del " + currentWeekStart);
        navigationBox.getChildren().addAll(previousWeekButton, weekLabel, nextWeekButton);

        // Agregar la barra de navegación al VBox
        mainLayout.getChildren().add(navigationBox);

        // Agregar el calendario por primera vez
        updateCalendar(mainLayout);

        // Configurar el Scene y mostrar la ventana
        Scene scene = new Scene(mainLayout, 1200, 800); // Tamaño de la ventana ajustado
        calendarStage.setScene(scene);
        calendarStage.show();
    }

    private void updateCalendar(VBox mainLayout) throws SQLException {
        // Remover el calendario anterior si existe
        if (mainLayout.getChildren().size() > 1) {
            mainLayout.getChildren().remove(1);
        }

        // Crear el calendario
        Agenda calendar = new Agenda();
        calendar.setMaxHeight(Double.MAX_VALUE);
        calendar.setMaxWidth(Double.MAX_VALUE);

        // Establecer los encabezados de los días de la semana
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE, MMM d");

        LocalDate weekDay = currentWeekStart;
        for (int i = 0; i < 7; i++) {
            // Aquí se ajustan los encabezados de los días en el calendario
            calendar.withDisplayedLocalDateTime(weekDay.atStartOfDay()); // Actualiza el día actual en el calendario
            weekDay = weekDay.plusDays(1);
        }

        // Limpiar las citas existentes
        calendar.appointments().clear();

        // Obtener las órdenes con fechas de entrega desde la base de datos
        List<Order> deliveryDatesOrders = DatabaseManager.getDeliveryDatesOfOrders();

        // Agregar las órdenes como eventos en el calendario
        LocalDate endOfWeek = currentWeekStart.plusDays(6); // Obtener el final de la semana
        for (Order order : deliveryDatesOrders) {
            LocalDate deliveryDate = order.getEstimatedCompletionDate();
            if (!deliveryDate.isBefore(currentWeekStart) && !deliveryDate.isAfter(endOfWeek) ) {
                Agenda.Appointment appointment = new Agenda.AppointmentImplLocal()
                        .withStartLocalDateTime(deliveryDate.atTime(9, 0)) // Inicio del día
                        .withEndLocalDateTime(deliveryDate.atTime(17, 0)) // Fin del día
                        .withSummary("Entregar órden: " + order.getOrderNumber()+"\n"
                                + "Trabajo realizado: "+order.getWorkDescription()+"\n"
                                + "Costo de la órden: $"+order.getTotalCost())
                        .withDescription("Fecha de entrega para la orden #" + order.getOrderNumber()
                        + "\n"+"Descripción del trabajo: " + order.getWorkDescription());
                calendar.appointments().add(appointment);
            }
        }

        // Agregar el calendario al VBox
        mainLayout.getChildren().add(calendar);

        // Actualizar la etiqueta de la semana
        HBox navigationBox = (HBox) mainLayout.getChildren().get(0);
        Label weekLabel = (Label) navigationBox.getChildren().get(1);
        weekLabel.setText("Semana del " + currentWeekStart);



    }
}
