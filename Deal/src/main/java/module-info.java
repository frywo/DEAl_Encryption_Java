module com.example.deal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.deal to javafx.fxml;
    exports com.example.deal;
}