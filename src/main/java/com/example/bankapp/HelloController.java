package com.example.bankapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.example.bankapp.banques.Banque;
import com.example.bankapp.banques.CompteBancaire;
import com.example.bankapp.connection.ConnectDatabase;

import java.io.IOException;
import java.sql.*;

public class HelloController<MyDataType> {

    @FXML
    private TableView<MyDataType> tableView;

    @FXML
    public void initialize() {
        // Initialiser ta TableView ou effectuer d'autres initialisations
        tableView = new TableView<>();
        // Autres configurations éventuelles
    }

    @FXML
    private Label welcomeText;

    @FXML
    private Label addbtn, supbtn, modbtn, rech_nombtn, list_lettreComptebtn, rechTypebtn, affichCompteType, detailbtn, transbtn, relevbtn;

    @FXML
    private Button btn_quit;

    @FXML
    private Pane cont = new Pane(afficherElements());
    Enregistrement compte;
    static ConnectDatabase conn = new ConnectDatabase();
    static Connection connection = conn.connected();

    // Méthode pour gérer la sélection des boutons du menu
    public void handleMenuSelection(ActionEvent event) {
        try {
            String fxmlFile = "";

            // Déterminer le fichier FXML à charger en fonction du bouton sélectionné
            if (event.getSource() instanceof Node) {
                Node node = (Node) event.getSource();
                if (node.getId().equals("button1")) {
                    fxmlFile = "ajouterCompte.fxml";
                } else if (node.getId().equals("button2")) {
                    fxmlFile = "Supprimercompte.fxml";
                } else if (node.getId().equals("button3")) {
                    fxmlFile = "modifiercompte.fxml";
                } else if (node.getId().equals("button4")) {
                    fxmlFile = "rechercheCompteType.fxml";
                }else if (node.getId().equals("button8")) {
                    fxmlFile = "details.fxml";
                }

            }

            // Charger le nouveau fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'exception d'une manière appropriée
        }
    }

    public void supprimerCompte(MouseEvent mouseEvent) {
        // Logique pour supprimer un compte
        TableView.TableViewSelectionModel<MyDataType> selectionModel = tableView.getSelectionModel();
        MyDataType selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            tableView.getItems().remove(selectedItem);
            // Suppression dans la base de données
            try {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM CompteBancaire WHERE numeroCompte = ?");
                stmt.setString(1, ((Enregistrement) selectedItem).getNum());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void modifierCompte(MouseEvent mouseEvent) {
        // Logique pour modifier un compte
        TableView.TableViewSelectionModel<MyDataType> selectionModel = tableView.getSelectionModel();
        MyDataType selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            // Exemple: Modification du solde
            ((Enregistrement) selectedItem).setSolde("Nouveau solde");
            tableView.refresh();
            // Mise à jour dans la base de données
            try {
                PreparedStatement stmt = connection.prepareStatement("UPDATE CompteBancaire SET solde = ? WHERE numeroCompte = ?");
                stmt.setString(1, ((Enregistrement) selectedItem).getSolde());
                stmt.setString(2, ((Enregistrement) selectedItem).getNum());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void afficherCompteType(MouseEvent mouseEvent) {
        // Logique pour afficher les comptes par type
        String typeCompte = "type désiré"; // A remplacer par la valeur souhaitée
        ObservableList<MyDataType> comptesParType = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CompteBancaire WHERE type = ?");
            stmt.setString(1, typeCompte);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                comptesParType.add((MyDataType) new Enregistrement(
                        resultSet.getString("numeroCompte"),
                        resultSet.getString("titulaire"),
                        resultSet.getString("solde"),
                        resultSet.getString("type"),
                        resultSet.getString("decouvertAutorise"),
                        resultSet.getString("taux")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(comptesParType);
    }

    public void releverCompte(MouseEvent mouseEvent) {
        // Logique pour générer le relevé d'un compte
        TableView.TableViewSelectionModel<MyDataType> selectionModel = tableView.getSelectionModel();
        MyDataType selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            // Afficher ou générer un relevé de compte
            System.out.println("Relevé pour le compte: " + ((Enregistrement) selectedItem).getNum());
        }
    }

    public void details(MouseEvent mouseEvent) {
        // Logique pour afficher les détails d'un compte
        TableView.TableViewSelectionModel<MyDataType> selectionModel = tableView.getSelectionModel();
        MyDataType selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            // Afficher les détails du compte
            System.out.println("Détails pour le compte: " + ((Enregistrement) selectedItem).getNum());
        }
    }

    public void transfert(MouseEvent mouseEvent) {
        // Logique pour transférer des fonds entre comptes
        TableView.TableViewSelectionModel<MyDataType> selectionModel = tableView.getSelectionModel();
        MyDataType selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            // Exemple: Transfert de fonds
            ((Enregistrement) selectedItem).setSolde("Nouveau solde après transfert");
            tableView.refresh();
            // Mise à jour dans la base de données
            try {
                PreparedStatement stmt = connection.prepareStatement("UPDATE CompteBancaire SET solde = ? WHERE numeroCompte = ?");
                stmt.setString(1, ((Enregistrement) selectedItem).getSolde());
                stmt.setString(2, ((Enregistrement) selectedItem).getNum());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ajouterCompte(MouseEvent mouseEvent) throws IOException {
        // Logique pour ajouter un nouveau compte
        Enregistrement nouveauCompte = new Enregistrement("num", "titulaire", "solde", "type", "decouverte", "taux");
        tableView.getItems().add((MyDataType) nouveauCompte);
        // Insertion dans la base de données
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO CompteBancaire (numeroCompte, titulaire, solde, type, decouvertAutorise, taux) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, nouveauCompte.getNum());
            stmt.setString(2, nouveauCompte.getTitulaire());
            stmt.setString(3, nouveauCompte.getSolde());
            stmt.setString(4, nouveauCompte.getType());
            stmt.setString(5, nouveauCompte.getDecouverte());
            stmt.setString(6, nouveauCompte.getTaux());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rechercherCompteType(MouseEvent mouseEvent) {
        // Logique pour rechercher des comptes par type
        String typeRecherche = "type à rechercher"; // A remplacer par la valeur de recherche souhaitée
        ObservableList<MyDataType> comptesTrouves = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CompteBancaire WHERE type = ?");
            stmt.setString(1, typeRecherche);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                comptesTrouves.add((MyDataType) new Enregistrement(
                        resultSet.getString("numeroCompte"),
                        resultSet.getString("titulaire"),
                        resultSet.getString("solde"),
                        resultSet.getString("type"),
                        resultSet.getString("decouvertAutorise"),
                        resultSet.getString("taux")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(comptesTrouves);
    }

    public void ajouterCompte(ActionEvent actionEvent) {
        // Logique pour ajouter un nouveau compte via ActionEvent
        Enregistrement nouveauCompte = new Enregistrement("num", "titulaire", "solde", "type", "decouverte", "taux");
        tableView.getItems().add((MyDataType) nouveauCompte);
        // Insertion dans la base de données
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO CompteBancaire (numeroCompte, titulaire, solde, type, decouvertAutorise, taux) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, nouveauCompte.getNum());
            stmt.setString(2, nouveauCompte.getTitulaire());
            stmt.setString(3, nouveauCompte.getSolde());
            stmt.setString(4, nouveauCompte.getType());
            stmt.setString(5, nouveauCompte.getDecouverte());
            stmt.setString(6, nouveauCompte.getTaux());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void  recherccherNom(MouseEvent mouseEvent) {
        // Logique pour rechercher un compte par nom
        String nomRecherche = "nom à rechercher"; // A remplacer par la valeur de recherche souhaitée
        ObservableList<MyDataType> comptesTrouves = FXCollections.observableArrayList();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CompteBancaire WHERE titulaire = ?");
            stmt.setString(1, nomRecherche);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                comptesTrouves.add((MyDataType) new Enregistrement(
                        resultSet.getString("numeroCompte"),
                        resultSet.getString("titulaire"),
                        resultSet.getString("solde"),
                        resultSet.getString("type"),
                        resultSet.getString("decouvertAutorise"),
                        resultSet.getString("taux")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(comptesTrouves);
    }

    public TableView<Enregistrement> afficherElements() {
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

        // Chargement des données depuis la base de données
        chargerDonnees();
        return tableView;
    }

    private void chargerDonnees() {
        ObservableList<Enregistrement> compte = FXCollections.observableArrayList();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM CompteBancaire")) {

            while (resultSet.next()) {
                String num = resultSet.getString("numeroCompte");
                String titulaire = resultSet.getString("titulaire");
                String solde = resultSet.getString("solde");
                String type = resultSet.getString("type");
                String decouverte = resultSet.getString("decouvertAutorise");
                String taux = resultSet.getString("taux");

                compte.add(new Enregistrement(num, titulaire, solde, type, decouverte, taux));
            }

            TableView<Enregistrement> tableView = new TableView<>();
            tableView.setItems(compte);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fermer(ActionEvent actionEvent) {
        // Logique pour fermer l'application
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void Supprimercompte(MouseEvent mouseEvent) throws IOException {
        Stage stage = null;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Supprimercompte.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Supprimer compte");
        stage.setScene(scene);
        stage.show();
    }

}
