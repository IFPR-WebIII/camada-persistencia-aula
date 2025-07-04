package com.example.camada_persistencia_aula.repositories;

import java.util.List;

import com.example.camada_persistencia_aula.models.Seller;

public interface SellerRepository {
    
    public List<Seller> getAll();
    public Seller getById(Integer id);
    public List<Seller> getSellersByDepartmentId(Integer departmentId);
    public Seller insert(Seller seller);
    public Seller update(Seller seller);
    public void deleteById(Integer id);

}
