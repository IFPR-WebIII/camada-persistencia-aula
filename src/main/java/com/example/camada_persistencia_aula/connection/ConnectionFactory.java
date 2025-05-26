package com.example.camada_persistencia_aula.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public static Connection connection = null;
    
    public static Connection getConnection(){
        
        String url = "jdbc:mysql://localhost/ifpr_store";
        String user = "root";
        String pass = "bancodedados";

        try {
        
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, pass);
            } 

            return connection;
        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
