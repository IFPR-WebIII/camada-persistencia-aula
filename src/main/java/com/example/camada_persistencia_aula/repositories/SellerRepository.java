package com.example.camada_persistencia_aula.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.camada_persistencia_aula.connection.ConnectionFactory;
import com.example.camada_persistencia_aula.exceptions.DatabaseException;
import com.example.camada_persistencia_aula.models.Department;
import com.example.camada_persistencia_aula.models.Seller;

public class SellerRepository {

    private Connection connection;

    public SellerRepository() {
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
            throw new DatabaseException("não foi possível criar um Statment");
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
                    "where department.id = " + departmentId);
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }


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
