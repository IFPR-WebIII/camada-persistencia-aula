package com.example.camada_persistencia_aula.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.camada_persistencia_aula.models.Seller;
import com.example.camada_persistencia_aula.services.SellerService;

@Controller
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @GetMapping
    public String getAll(Model model){
        
        List<Seller> sellers = sellerService.getAll();

        //Ao inves de retornar o JSON, deveriamos usar o Thymeleaf
        //para apresentar uma tela

        model.addAttribute("vendedores", sellers);

        return "sellers";

    }

    @ResponseBody
    @GetMapping("/{id}")
    public List<Seller> getSellersByDepartmentId(@PathVariable Integer id){
        
        List<Seller> sellers = sellerService.getSellersByDepartmentId(id);

        return sellers;

    }
    
}
