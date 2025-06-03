package com.example.camada_persistencia_aula.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.camada_persistencia_aula.models.Seller;
import com.example.camada_persistencia_aula.repositories.SellerRepository;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    
    public List<Seller> getAll(){
        
        return sellerRepository.getAll();

    }

    public List<Seller> getSellersByDepartmentId(Integer departmentId){

        if (departmentId < 0) {
            throw new IllegalArgumentException("Id inválido");
        }

        return sellerRepository.getSellersByDepartmentId(departmentId);

    }
    
}
