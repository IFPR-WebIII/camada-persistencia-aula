package com.example.camada_persistencia_aula.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.camada_persistencia_aula.models.Department;
import com.example.camada_persistencia_aula.models.Seller;
import com.example.camada_persistencia_aula.repositories.SellerRepository;
import com.example.camada_persistencia_aula.repositories.SellerRepositoryJDBC;

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

        SellerRepositoryJDBC repository = new SellerRepositoryJDBC();

        List<Seller> sellers = repository.getAll();

        for (Seller seller : sellers) {
            System.out.println(seller);
        }

        assertThat(sellers.size()).isGreaterThan(0);

    }

    @Test
    public void deveRetornarUmaListaDeSellersPorDepartamento(){

        SellerRepository repository = new SellerRepositoryJDBC();

        List<Seller> sellers = repository.getSellersByDepartmentId(2);

        for (Seller seller : sellers) {
            System.out.println(seller);
        }

        assertThat(sellers.size()).isGreaterThan(0);

    }

    @Test
    public void deveInserirUmSellerComSucesso(){
        SellerRepository repository = new SellerRepositoryJDBC();

        Department department = new Department();
        department.setId(1);

        Seller s1 = new Seller();
        s1.setName("Carl");
        s1.setEmail("carl@gmail.com");
        s1.setBirthDate(LocalDate.of(1985, 01, 01));
        s1.setBaseSalary(1000.0);
        s1.setDepartment(department);

        Seller result = repository.insert(s1);

        assertThat(result).isNotNull();
    }

    
}
