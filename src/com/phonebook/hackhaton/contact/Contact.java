package com.phonebook.hackhaton.contact;

public class Contact {
    private String phone;
    private String name;
    private String lastName;

    public Contact(String phone, String name, String lastName) {
        this.phone = phone;
        this.name = name;
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
