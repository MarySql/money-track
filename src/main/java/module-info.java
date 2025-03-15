module com.marysql.moneytrack {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;

    opens com.marysql.moneytrack to javafx.fxml;
    exports com.marysql.moneytrack;
}