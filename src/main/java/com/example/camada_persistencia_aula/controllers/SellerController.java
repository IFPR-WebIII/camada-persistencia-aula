package com.example.camada_persistencia_aula.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.camada_persistencia_aula.models.Department;
import com.example.camada_persistencia_aula.models.Seller;
import com.example.camada_persistencia_aula.services.SellerService;

@Controller
@RequestMapping({"/sellers", "/sellers/"})
public class SellerController {

    @Autowired
    SellerService sellerService;

    @GetMapping
    public String getAll(Model model){
        
        List<Seller> sellers = sellerService.getAll();

        // adiciona os dados vindo do banco a um model, que é o responsável por compartilhar esses dados com uma tela
        //A tela, nesse caso "sellers", deve receber e renderizar esses dados. 

        model.addAttribute("vendedores", sellers);

        return "seller_list";

    }

    @GetMapping("/department/{id}")
    public String getSellersByDepartmentId(Model model, @PathVariable Integer id){
        
        List<Seller> sellers = sellerService.getSellersByDepartmentId(id);

        model.addAttribute("vendedores", sellers);

        return "seller_list";

    }

    @GetMapping("/{id}")
    public String getSellersById(Model model, @PathVariable Integer id){
        
        Seller seller = sellerService.getSellersById(id);

        model.addAttribute("vendedores", seller);

        return "seller";

    }

    // Aqui destaco que, são necessários duas rotas: a primeira cria a o formulário de Cadastro
    //Já a segunda rota, recebe os dados do novo Seller e realiza a operação de INSERT no banco de dados.
    
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("seller", new Seller());
        return "seller_create";
    }

    @PostMapping("/create")
    public String create(Seller seller) {

        // Como não temos a model de department para obtê-lo, vamos informar o department de forma hardcoded.

        Department department = new Department();
        department.setId(1);

        seller.setDepartment(department);

        sellerService.insert(seller);
        return "redirect:/sellers";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        sellerService.deleteById(id);
        return "redirect:/sellers";
    }


    // Assim como no cadastro, aqui também são necessárias duas rotas: a primeira cria a o formulário de atualização com os dados atuais do Seller
    //Já a segunda rota, recebe os dados atualizados e realiza a operação de UPDATE no banco de dados.

    @GetMapping("/update/{id}")
    public String updateForm(Model model, @PathVariable Integer id) {
        Seller seller = sellerService.getSellersById(id);

        //Embora não tenhamos implementado, seria interessante obter a lista de departamentos para que no momento de atualizar o Seller
        //pudessemos atualizar o Departament vinculado a ele. 
        //Department List<Department> departments = departmentsService.getDepartments();

        model.addAttribute("seller", seller);
        return "seller_update";
    }

    @PostMapping("/update")
    public String update(Seller seller) {



        sellerService.update(seller);

        return "redirect:/sellers";
    }
    
}
