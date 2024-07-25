package com.example.bankapp.banques;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompteCourant extends CompteBancaire {
    private double decouvertAutorise;

    public CompteCourant(String numeroCompte, String titulaire, double solde, double decouvertAutorise) {
        super(numeroCompte, titulaire, solde);
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public void retirer(double montant) {
        if (montant > 0 && solde + decouvertAutorise >= montant) {
            solde -= montant;
        }
    }

    @Override
    public void afficherDetails() {
        System.out.println("Compte Courant:");
        System.out.println("Numéro de compte: " + numeroCompte);
        System.out.println("Titulaire: " + titulaire);
        System.out.println("Solde: " + solde);
        System.out.println("Découvert autorisé: " + decouvertAutorise);
    }


    @Override
    public void sauvegarder(Connection conn) throws SQLException {
        String query = "INSERT INTO CompteBancaire (numeroCompte, titulaire, solde, type, decouvertAutorise) VALUES (?, ?, ?, 'CompteCourant', ?) " +
                "ON DUPLICATE KEY UPDATE titulaire = VALUES(titulaire), solde = VALUES(solde), decouvertAutorise = VALUES(decouvertAutorise)";
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            stmt.setString(2, titulaire);
            stmt.setDouble(3, solde);
            stmt.setDouble(4, decouvertAutorise);
            stmt.executeUpdate();
        }
    }
}

