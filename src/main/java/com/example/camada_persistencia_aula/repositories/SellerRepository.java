package com.example.camada_persistencia_aula.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.camada_persistencia_aula.connection.ConnectionFactory;
import com.example.camada_persistencia_aula.exceptions.DatabaseException;
import com.example.camada_persistencia_aula.models.Seller;

public class SellerRepository {

    private Connection connection;


    public SellerRepository(){
        connection = ConnectionFactory.getConnection();
    }

    public List<Seller> getAll(){

        List<Seller> sellers = new ArrayList<>();

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery("select * from seller");

            while (result.next()) {
                Seller seller = new Seller();

                seller.setId(result.getInt("Id"));
                seller.setName(result.getString("Name"));
                seller.setEmail(result.getString("Email"));
                seller.setBirthDate(result.getDate("BirthDate").toLocalDate());
                seller.setBaseSalary(result.getDouble("BaseSalary"));


                sellers.add(seller);
            }


        } catch(SQLException e){
            throw new DatabaseException("não foi possível criar um Statment");
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return sellers;

    }

    
}
