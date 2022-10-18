package com.example.pr2.Models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String surname;
    private String name;
    private Boolean dormitories;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Integer groups;

    public Student(String Surname, String Name, Integer Groups, Date Birthday,Boolean Dormitories) {
        this.surname = Surname;
        this.name = Name;
        this.dormitories = Dormitories;
        this.groups = Groups;
        this.birthday = Birthday;
    }

    public Student() {

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

    public Integer getGroup() {
        return groups;
    }

    public void setGroup(Integer group) {
        this.groups = group;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDormitories() {
        return dormitories;
    }

    public void setDormitories(Boolean dormitories) {
        this.dormitories = dormitories;
    }
    public String getBirthdayString(){
        return new SimpleDateFormat("yyyy-MM-dd").format(this.getBirthday());
    }
}
