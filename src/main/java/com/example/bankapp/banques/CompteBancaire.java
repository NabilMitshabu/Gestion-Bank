package com.example.bankapp.banques;


import javafx.beans.value.ObservableValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CompteBancaire {
    protected String numeroCompte;
    protected String titulaire;
    protected double solde;

    public CompteBancaire(String numeroCompte, String titulaire, double solde) {
        this.numeroCompte = numeroCompte;
        this.titulaire = titulaire;
        this.solde = solde;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public double getSolde() {
        return solde;
    }

    public void deposer(double montant) {
        if (montant > 0) {
            solde += montant;
        }
    }

    public void retirer(double montant) {
        if (montant > 0 && solde >= montant) {
            solde -= montant;
        }
    }

    public abstract void afficherDetails();

    public abstract void sauvegarder(Connection conn) throws SQLException;

    public static CompteBancaire charger(Connection conn, String numeroCompte) throws SQLException {
        String query = "SELECT * FROM CompteBancaire WHERE numeroCompte = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("type");
                    String titulaire = rs.getString("titulaire");
                    double solde = rs.getDouble("solde");

                    if ("CompteCourant".equals(type)) {
                        double decouvertAutorise = rs.getDouble("decouvertAutorise");
                        return new CompteCourant(numeroCompte, titulaire, solde, decouvertAutorise);
                    } else if ("CompteEpargne".equals(type)) {
                        double tauxInteret = rs.getDouble("tauxInteret");
                        return new CompteEpargne(numeroCompte, titulaire, solde, tauxInteret);
                    }
                }
            }
        }
        return null;
    }
    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }
    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }
    public void setSolde(double solde) {
        this.solde = solde;
    }



}
