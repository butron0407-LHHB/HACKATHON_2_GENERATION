package com.phonebook.hackhaton.agendahackhaton.model;


public class Contact {
    private String name;
    private String lastName;
    private String phone;

    public Contact(String name, String lastName, String phone) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}