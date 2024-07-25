package com.example.bankapp.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    String jdbcUrl = "jdbc:mysql://localhost:3306/java";
    String username = "root";
    String password = "";
    String databaseName = "java";

    public Connection connected(){
        try{
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            if (connection != null) {
                System.out.println("Connected to the database!");
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connected();
    }
}


