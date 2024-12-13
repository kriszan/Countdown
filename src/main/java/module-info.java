module org.example.countdown {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;


    opens org.example.countdown to javafx.fxml;
    exports org.example.countdown;
}