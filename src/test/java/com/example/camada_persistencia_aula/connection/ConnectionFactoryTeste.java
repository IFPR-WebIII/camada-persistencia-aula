package com.example.camada_persistencia_aula.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.camada_persistencia_aula.models.Seller;
import com.example.camada_persistencia_aula.repositories.SellerRepository;

@SpringBootTest
public class ConnectionFactoryTeste {

    @Test
    public void deveConectarAoBanco() throws SQLException{

        // Realiza a conexão
        Connection connection = ConnectionFactory.getConnection();

        //Objeto, que executa as operações do banco de dados
        Statement statement = connection.createStatement();
        
        //ResultSet é um objeto que de forma intermediária, recebe os dados em formado de tabela do banco
        ResultSet result = statement.executeQuery("select * from seller");

        //result.getInt(1);
        //result.getInt("Id");

        //result.getString(2);
        //result.getString("Name");
        
        result.next();
        System.out.println("Id: " + result.getInt("Id") + " name: " + result.getString("Name"));

        assertThat(connection).isNotNull();

    }

    @Test
    public void deveRetornarUmaListaDeSellers(){

        SellerRepository repository = new SellerRepository();

        List<Seller> sellers = repository.getAll();

        for (Seller seller : sellers) {
            System.out.println(seller);
        }

        assertThat(sellers.size()).isGreaterThan(0);

    }
    
}
