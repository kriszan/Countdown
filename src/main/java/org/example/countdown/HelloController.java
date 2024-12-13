package org.example.countdown;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class HelloController {
    @FXML
    private Label outputLabel;
    @FXML
    private Button myButton;
    @FXML
    private TextField myInput;

    private Task<Void> timerTask;

    public void initialize() {
        myInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                /*if (!newVal.matches("(0|[1-9]\\d{3})")) {
                    //inputLeft.setText(inputLeft.getText().substring(0,inputLeft.getLength()-1));
                    input.setText(newVal.replaceAll("[^\\d]", ""));
                }*/
                if (false) {
                }
            }
        });

        myButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String myString = myInput.getText();
                timerTask = new Task() {
                    @Override
                    protected Void call() {
                        while (!isCancelled()) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            LocalDateTime dateTime = LocalDateTime.parse(myString, formatter);
                            if (dateTime.isAfter(LocalDateTime.now())) {
                                updateMessage(formatDuration(dateTime));
                            }

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                        return null;
                    }
                };
                outputLabel.textProperty().bind(timerTask.messageProperty());
                new Thread(timerTask).start();
            }
        });

    }

    private String formatDuration(LocalDateTime dateTime) {
        LocalDateTime lnow = LocalDateTime.now();
        long years = lnow.until(dateTime, ChronoUnit.YEARS);
        long months = lnow.until(dateTime, ChronoUnit.MONTHS);
        long days = lnow.until(dateTime, ChronoUnit.DAYS);
        long hours = lnow.until(dateTime, ChronoUnit.HOURS);
        long minutes = lnow.until(dateTime, ChronoUnit.MINUTES);
        long seconds = lnow.until(dateTime, ChronoUnit.SECONDS);
        return String.format("%02d.%02d.%02d. %02d:%02d:%02d", years, months, days, hours, minutes, seconds);
    }
}