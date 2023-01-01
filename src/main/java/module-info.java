module CurrencyCalculator {

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires org.jsoup;
    requires java.logging;

    opens org.example to javafx.graphics, javafx.fxml;
    exports org.example;
}