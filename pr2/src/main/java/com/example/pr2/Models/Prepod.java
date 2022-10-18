package com.example.pr2.Models;

import javax.persistence.*;

@Entity
public class Prepod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String surname, name, predmets;
    private Double salary;
    private Integer OpeningHours;

    public Prepod(String Surname, String Name, Double salary, String Predmets, Integer OpeningHours) {
        this.surname = Surname;
        this.name = Name;
        this.salary = salary;
        this.predmets = Predmets;
        this.OpeningHours = OpeningHours;
    }

    public Prepod() {

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPredmets() {
        return predmets;
    }

    public void setPredmets(String predmets) {
        this.predmets = predmets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getOpeningHours() {
        return OpeningHours;
    }

    public void setOpeningHours(Integer openingHours) {
        OpeningHours = openingHours;
    }
}
