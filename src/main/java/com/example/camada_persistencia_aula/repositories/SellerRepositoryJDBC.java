package com.example.camada_persistencia_aula.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.camada_persistencia_aula.connection.ConnectionFactory;
import com.example.camada_persistencia_aula.exceptions.DatabaseException;
import com.example.camada_persistencia_aula.models.Department;
import com.example.camada_persistencia_aula.models.Seller;

@Repository
public class SellerRepositoryJDBC implements SellerRepository {

    private Connection connection;

    public SellerRepositoryJDBC() {
        connection = ConnectionFactory.getConnection();
    }

    public List<Seller> getAll() {

        List<Seller> sellers = new ArrayList<>();

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = connection.createStatement();

            result = statement.executeQuery("select seller.*, department.Name as DepartmentName " +
                    "from seller " +
                    "join department " +
                    "on seller.DepartmentId = department.Id");

            Map<Integer, Department> departmentMap = new HashMap<>();

            while (result.next()) {

                Department department = departmentMap.get(result.getInt("DepartmentId"));

                if (department == null) {
                    department = instantiateDepartment(result);
                    departmentMap.put(result.getInt("DepartmentId"), department);
                }

                Seller seller = instantiateSeller(result, department);

                sellers.add(seller);
            }

        } catch (SQLException e) {
            throw new DatabaseException("problemas com a conexão: " + e.getMessage());
        } finally {
            ConnectionFactory.closeSResultSet(result);
            ConnectionFactory.closeStatament(statement);
        }

        return sellers;

    }

    public List<Seller> getSellersByDepartmentId(Integer departmentId){

        List<Seller> sellers = new ArrayList<>();
        
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement("select seller.*, department.Name as DepartmentName " +
                    "from seller " +
                    "join department " +
                    "on seller.DepartmentId = department.Id " +
                    "where department.id = ?");

            statement.setInt(1, departmentId);
            result = statement.executeQuery();
            
            Map<Integer, Department> mapDepartments = new HashMap<>();

            while (result.next()) {

                Department department = mapDepartments.get(result.getInt("DepartmentId"));

                if (department == null) {
                    department = instantiateDepartment(result);
                    mapDepartments.put(result.getInt("DepartmentId"), department);
                }

                Seller seller = instantiateSeller(result, department);

                sellers.add(seller);
            }
            
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeSResultSet(result);
            ConnectionFactory.closeStatament(statement);
        }

        return sellers;

    }

    @Override
    public Seller getById(Integer id) {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = connection.prepareStatement(
                "select seller.*, department.Name as DepartmentName " +
                "from seller " +
                "join department on seller.DepartmentId = department.Id " +
                "where seller.Id = ?"
            );

            statement.setInt(1, id);
            result = statement.executeQuery();

            if (result.next()) {
                Department department = instantiateDepartment(result);
                return instantiateSeller(result, department);
            }

            return null;
        
        } catch (SQLException e) {
            throw new DatabaseException("problemas com a conexão: " + e.getMessage());
        } finally {
            ConnectionFactory.closeSResultSet(result);
            ConnectionFactory.closeStatament(statement);
        }

    }

    public Seller insert(Seller seller) {
        
        PreparedStatement statement;

        try {
            String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) " + "VALUES(?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, Date.valueOf(seller.getBirthDate()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());

            Integer rowsAffected = statement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected == 0) {
                throw new DatabaseException("Seller not inserted");
            }

            ResultSet ids = statement.getGeneratedKeys();

            ids.next();
            
            seller.setId(ids.getInt(1));
       
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seller;
    }

    @Override
    public Seller update(Seller seller) {
        
        PreparedStatement statement = null;

        try {
            String sql = "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, Date.valueOf(seller.getBirthDate()));
            statement.setDouble(4, seller.getBaseSalary());
            statement.setInt(5, seller.getDepartment().getId());
            statement.setInt(6, seller.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Seller not updated");
            }

        } catch (SQLException e) {
            throw new DatabaseException("problemas com a conexão: " + e.getMessage());
        } finally {
            ConnectionFactory.closeStatament(statement);
        }

        return seller;

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;

        try {
            String sql = "DELETE FROM seller WHERE Id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Seller not found or not deleted");
            }

        } catch (SQLException e) {
            throw new DatabaseException("problemas com a conexão: " + e.getMessage());
        } finally {
            ConnectionFactory.closeStatament(statement);
        }
    }


    // Métodos auxiliarres
    
    public Department instantiateDepartment(ResultSet result) throws SQLException {

        Department department = new Department();
        department.setId(result.getInt("DepartmentId"));
        department.setName(result.getString("DepartmentName"));

        return department;
    }

    public Seller instantiateSeller(ResultSet result, Department department) throws SQLException {
        
        Seller seller = new Seller();

        seller.setId(result.getInt("Id"));
        seller.setName(result.getString("Name"));
        seller.setEmail(result.getString("Email"));
        seller.setBirthDate(result.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(result.getDouble("BaseSalary"));
        seller.setDepartment(department);

        return seller;

    }

}
