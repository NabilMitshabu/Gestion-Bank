package com.example.bankapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.example.bankapp.banques.CompteEpargne;
import com.example.bankapp.connection.ManageDatabase;

import java.sql.SQLException;

import static java.lang.Double.parseDouble;
import static com.example.bankapp.banques.Banque.manage;

public class BanqueControl {

    @FXML
    private TextField numch, nomch,typech, montantch, tauxch,decouvertech;
    @FXML
    private Button ajoutbtn;

    ManageDatabase manege=new ManageDatabase();
    public void ajouter(ActionEvent actionEvent) throws SQLException {
        try {


        manege.inserer(numch.getText(),nomch.getText(),parseDouble(montantch.getText()),parseDouble(tauxch.getText()));
    } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
