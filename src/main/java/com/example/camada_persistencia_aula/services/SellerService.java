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

    public Seller getSellersById(Integer id){

        if (id < 0) {
            throw new IllegalArgumentException("Id inválido");
        }

        return sellerRepository.getById(id);

    }

    public Seller insert(Seller newSeller) {
        if (newSeller == null) {
            throw new IllegalArgumentException("Seller inválido");
        }
        // Você pode adicionar validações adicionais aqui, se necessário
        return sellerRepository.insert(newSeller);
    }

    public Seller update(Seller updatedSeller) {
        
        if (updatedSeller == null || updatedSeller.getId() < 0) {
            throw new IllegalArgumentException("Seller inválido");
        }
        
        Seller existingSeller = sellerRepository.getById(updatedSeller.getId());
        
        if (existingSeller == null) {
            throw new IllegalArgumentException("Vendedor não encontrado");
        }
        
        // Atualize os campos necessários do existingSeller com os valores de updatedSeller
        existingSeller.setName(updatedSeller.getName());
        existingSeller.setEmail(updatedSeller.getEmail());
        existingSeller.setBirthDate(updatedSeller.getBirthDate());
        existingSeller.setBaseSalary(updatedSeller.getBaseSalary());
        existingSeller.setDepartment(updatedSeller.getDepartment());

        return sellerRepository.update(existingSeller);
    }

    public void deleteById(Integer id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id inválido");
        }

        Seller existingSeller = sellerRepository.getById(id);
        
        if (existingSeller == null) {
            throw new IllegalArgumentException("Vendedor não encontrado");
        }

        sellerRepository.deleteById(existingSeller.getId());
    }
    
}
