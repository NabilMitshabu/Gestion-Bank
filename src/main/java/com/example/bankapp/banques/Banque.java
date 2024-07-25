package com.example.bankapp.banques;
import com.example.bankapp.connection.ConnectDatabase;
import com.example.bankapp.connection.ManageDatabase;

import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Banque {
    private Map<String, CompteBancaire> comptes;
    private Connection conn;
    public static ManageDatabase manage;
    public Banque() {
        comptes = new HashMap<>();
    }


    public Banque(String url, String user, String password) throws SQLException {
        comptes = new HashMap<>();
        conn = DriverManager.getConnection(url, user, password);
    }
    static ConnectDatabase con=new ConnectDatabase();
    public void ajouterCompte(CompteBancaire compte) throws SQLException {
        conn=con.connected();
        compte.sauvegarder(conn);
        comptes.put(compte.getNumeroCompte(), compte);
    }

    public void supprimerCompte(String numeroCompte) throws SQLException {
        manage.supprimerCompte(numeroCompte);
        comptes.remove(numeroCompte);
    }

    public boolean verifierSiCompteExiste(String numeroCompte) throws SQLException {
        return manage.verify(numeroCompte);
    }

    public void modifierCompte(String numeroCompte, String newName, double newSolde) throws SQLException {
        manage.modifier(numeroCompte,newName,newSolde);
        CompteBancaire compte = comptes.get(numeroCompte);
        if (compte != null) {
            compte.setTitulaire(newName);
            compte.setSolde(newSolde);
        }
    }

    public CompteBancaire rechercherCompteParNom(String nom) throws SQLException {
        manage.rechercherNom(nom);
        return null;
    }

    public void listerComptesParLettre(char lettre) throws SQLException {
        manage.listLetrre(lettre);
    }

    public int compterComptesParType(Class<? extends CompteBancaire> type) throws SQLException {
        manage.compterType(type);
        return 0;
    }

    public void afficherComptesParType(Class<? extends CompteBancaire> type) throws SQLException {
        manage.afficheType(type);
    }

    public void afficherDetailsCompte(String numeroCompte) throws SQLException {
        CompteBancaire compte = CompteBancaire.charger(conn, numeroCompte);
        if (compte != null) {
            compte.afficherDetails();
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    public void transfererFonds(String numeroDeCompteSource, String numeroDeCompteDestination, double montant) throws SQLException {
        manage.tansfert(numeroDeCompteSource,numeroDeCompteDestination,montant);

        CompteBancaire source = comptes.get(numeroDeCompteSource);
        CompteBancaire destination = comptes.get(numeroDeCompteDestination);
        if (source != null && destination != null) {
            source.setSolde(source.getSolde() - montant);
            destination.setSolde(destination.getSolde() + montant);
        }
    }

    public void genererReleve(String numeroCompte) throws SQLException {
        CompteBancaire compte = CompteBancaire.charger(conn, numeroCompte);
        if (compte != null) {
            // Exemple simple d'affichage d'un relevé
            System.out.println("Relevé de compte pour le compte numéro : " + numeroCompte);
            System.out.println("Titulaire : " + compte.getTitulaire());
            System.out.println("Solde : " + compte.getSolde());
            // Ajouter d'autres détails si nécessaire
        } else {
            System.out.println("Compte non trouvé.");
        }
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}

