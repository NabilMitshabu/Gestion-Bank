package com.example.bankapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField textField = new TextField();
        Button btnEnvoyer = new Button("Envoyer");

        btnEnvoyer.setOnAction(event -> {
            String input = textField.getText();

            // Vérifier la valeur saisie et exécuter une logique en conséquence
            switch (input) {
                case "1":
                    // Logique pour le choix 1
                    System.out.println("Logique pour le choix 1");
                    break;
                case "2":
                    // Logique pour le choix 2
                    System.out.println("Logique pour le choix 2");
                    break;
                case "3":
                    // Logique pour le choix 3
                    System.out.println("Logique pour le choix 3");
                    break;
                // Ajouter d'autres cas pour les autres choix
                default:
                    System.out.println("Choix non reconnu");
            }
        });

        Pane root = new Pane();
        root.getChildren().addAll(textField, btnEnvoyer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}