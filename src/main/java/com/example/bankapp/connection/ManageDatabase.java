package com.example.bankapp.connection;

import com.example.bankapp.banques.CompteBancaire;

import java.sql.*;

public class ManageDatabase {

    static ConnectDatabase con= new ConnectDatabase();


    public void insertDataUSers(String numeroCompte,String titulaire,Double solde, Double tauxInteret) throws SQLException {
        String query = "INSERT INTO CompteBancaire (numeroCompte, titulaire, solde, type, taux) VALUES (?, ?, ?, 'CompteEpargne', ?) " +
                "ON DUPLICATE KEY UPDATE titulaire = VALUES(titulaire), solde = VALUES(solde),  taux = VALUES(taux)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            stmt.setString(2, titulaire);
            stmt.setDouble(3, solde);
            stmt.setDouble(4, tauxInteret);
            stmt.executeUpdate();
        }


    }


    public void selectDataUsers() throws SQLException {
        String selectDataSQL = "SELECT * FROM users";

        Connection connection = con.connected();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectDataSQL);


        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
        }


    }

    Connection conn= con.connected();
    public void modifier(String newName,String numeroCompte,Double newSolde) throws SQLException {
        String query = "UPDATE CompteBancaire SET titulaire = ?, solde = ? WHERE numeroCompte = ?";
        try ( Connection conn= con.connected();
              PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setDouble(2, newSolde);
            stmt.setString(3, numeroCompte);
            stmt.executeUpdate();
        }
    }
    public int compterType(Class<? extends CompteBancaire> type){
        String query = "SELECT COUNT(*) AS count FROM CompteBancaire WHERE type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, type.getSimpleName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
        }
        return 0;
    }
    public void inserer(String numeroCompte,String titulaire,Double solde, Double tauxInteret) throws SQLException {
        String query = "INSERT INTO CompteBancaire (numeroCompte, titulaire, solde, type, tauxInteret) VALUES (?, ?, ?, 'CompteEpargne', ?) " +
                "ON DUPLICATE KEY UPDATE titulaire = VALUES(titulaire), solde = VALUES(solde), tauxInteret = VALUES(tauxInteret)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            stmt.setString(2, titulaire);
            stmt.setDouble(3, solde);

            stmt.setDouble(5, tauxInteret);
            stmt.executeUpdate();
        }
    }


    public void listLetrre(char lettre){
        String query = "SELECT * FROM CompteBancaire WHERE titulaire LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, lettre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CompteBancaire compte = CompteBancaire.charger(conn, rs.getString("numeroCompte"));
                    if (compte != null) {
                        compte.afficherDetails();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public CompteBancaire rechercherNom(String nom) throws SQLException {
        String query = "SELECT * FROM CompteBancaire WHERE titulaire = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return CompteBancaire.charger(conn, rs.getString("numeroCompte"));
                }
            }
        }
        return null;
    }
    public boolean verify(String numeroCompte){
        String query = "SELECT 1 FROM CompteBancaire WHERE numeroCompte = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void tansfert(String numeroDeCompteSource, String numeroDeCompteDestination, double montant) throws SQLException {
        String query = "UPDATE CompteBancaire SET solde = solde - ? WHERE numeroCompte = ?";
        try (PreparedStatement stmtSource = conn.prepareStatement(query)) {
            stmtSource.setDouble(1, montant);
            stmtSource.setString(2, numeroDeCompteSource);
            stmtSource.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        query = "UPDATE CompteBancaire SET solde = solde + ? WHERE numeroCompte = ?";
        try (PreparedStatement stmtDestination = conn.prepareStatement(query)) {
            stmtDestination.setDouble(1, montant);
            stmtDestination.setString(2, numeroDeCompteDestination);
            stmtDestination.executeUpdate();
        }
    }
    public  void afficheType(Class<? extends CompteBancaire> type){
        String query = "SELECT * FROM CompteBancaire WHERE type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, type.getSimpleName());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CompteBancaire compte = CompteBancaire.charger(conn, rs.getString("numeroCompte"));
                    if (compte != null) {
                        compte.afficherDetails();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void supprimerCompte(String numeroCompte) throws SQLException {
        String query = "DELETE FROM CompteBancaire WHERE numeroCompte = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroCompte);
            stmt.executeUpdate();
        }
    }
}


