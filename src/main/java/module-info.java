/* module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example to javafx.fxml;
    exports com.example;
} */
module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Exporte le package pour le rendre accessible à d'autres modules
    exports com.example.bankapp;

    // Ouvre le package pour l'accès via la réflexion (utilisé par JavaFX)
    opens com.example.bankapp to javafx.graphics, javafx.fxml;
}
