package com.example.camada_persistencia_aula.models;

import java.time.LocalDate;

public class Seller {

    private Integer Id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private Double BaseSalary;
    //private Department department;
    
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Double getBaseSalary() {
        return BaseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        BaseSalary = baseSalary;
    }

    @Override
    public String toString() {
        return "Seller [getId()=" + getId() + ", getName()=" + getName() + ", getEmail()=" + getEmail()
                + ", getBirthDate()=" + getBirthDate() + ", getBaseSalary()=" + getBaseSalary() + "]";
    }

}
