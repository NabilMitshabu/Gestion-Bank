package com.example.bankapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.bankapp.banques.Banque;
import com.example.bankapp.banques.CompteBancaire;
import com.example.bankapp.connection.ConnectDatabase;



import java.io.IOException;
import java.sql.*;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private  Label addbtn, supbtn,modbtn,rech_nombtn, list_lettreComptebtn, rechTypebtn,affichCompteType, detailbtn,transbtn,relevbtn ;

    @FXML
    private Button btn_quit;

    @FXML
            private Pane cont=new Pane(afficherElements());

    Enregistrement compte;
    static ConnectDatabase conn=new ConnectDatabase();
    static Connection connection=conn.connected();
    public void supprimerCompte(MouseEvent mouseEvent) {
    }

    public void modifierCompte(MouseEvent mouseEvent) {
    }

    public void afficherCompteType(MouseEvent mouseEvent) {
    }

    public void releverCompte(MouseEvent mouseEvent) {
    }

    public void details(MouseEvent mouseEvent) {
    }

    public void transfert(MouseEvent mouseEvent) {
    }


    public void ajouterCompte1(MouseEvent mouseEvent) {
    }
    public void insertCompteBancaire(CompteBancaire compte) throws SQLException {
        String sql = "INSERT INTO CompteBancaire (numeroCompte, titulaire, solde, type, tauxInteret) VALUES (?, ?, ?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, compte.getNumeroCompte());
        statement.setString(2, compte.getTitulaire());
        statement.setBigDecimal(3, compte.getSolde());
        statement.setString(4, compte.getType());
        statement.setBigDecimal(5, compte.getTauxInteret());

        statement.executeUpdate();
        statement.close();
        disconnect();
    }
    public void ajouterCompte(MouseEvent mouseEvent) throws IOException {
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ajouterCompte.fxml"));
        Scene scene = new Scene(fxmlLoader.load() );
        stage.setTitle("Nouveau compte");
        stage.setScene(scene);
        stage.show();
    }

    public void rechercherCompteType(MouseEvent mouseEvent) {
    }

    public void fermer(ActionEvent actionEvent) {
    }

    public void recherccherNom(MouseEvent mouseEvent) {
    }

    public  TableView afficherElements() {
        TableView<Enregistrement> tableView = new TableView<>();

        TableColumn<Enregistrement, String> colNom = new TableColumn<>("NUM.COMPTE");
        colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());

        TableColumn<Enregistrement, String> colTitu = new TableColumn<>("TITULAIRE");
        colTitu.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        TableColumn<Enregistrement, String> colSolde = new TableColumn<>("SOLDE");
        colSolde.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        TableColumn<Enregistrement, String> colType = new TableColumn<>("TYPE");
        colType.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        TableColumn<Enregistrement, String> colDecouverte = new TableColumn<>("DECOUVERTE");
        colDecouverte.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        TableColumn<Enregistrement, String> colTaux = new TableColumn<>("TAUX");
        colTaux.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());

        tableView.getColumns().addAll(colNom, colTitu, colSolde, colType, colDecouverte, colTaux);
        //VBox = new VBox(tableView);

//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Affichage des données dans une TableView");
//        primaryStage.show();

        // Chargement des données depuis la base de données
        chargerDonnees();
        return tableView;
    }


    private void chargerDonnees() {
        ObservableList<Enregistrement>compte = FXCollections.observableArrayList();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM CompteBancaire")) {

            while (resultSet.next()) {
                String num = resultSet.getString("numeroCompte");
                String titulaire = resultSet.getString("titulaire");
                String solde= resultSet.getString("solde");
                String type = resultSet.getString("type");
                String decouverte = resultSet.getString("decouvertAutorise");
                String taux = resultSet.getString("taux");

                compte.add(new Enregistrement(num,titulaire,solde,type,decouverte,taux));
            }

            TableView<Enregistrement> tableView = null;
            tableView.setItems(compte);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}