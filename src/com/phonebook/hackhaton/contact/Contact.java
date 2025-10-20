package com.phonebook.hackhaton.contact;

import java.util.Objects;

public class Contact {
    private String name;
    private String lastName;
    private String phone;

    // Constructor corregido para el orden (name, lastName, phone)
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

    // Transformar los datos recibidos en string xd
    @Override
    public String toString() {
        return name + " " + lastName + " - " + phone;
    }

}