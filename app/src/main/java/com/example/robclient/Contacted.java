package com.example.robclient;

public class Contacted {

    private String Name,Email,Phone,Properties,Enquiry;

    public Contacted(){}



    public Contacted(String name, String email, String phone, String properties, String Enquiry) {
        Name = name;
        Email = email;
        Phone = phone;
        Properties = properties;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getProperties() {
        return Properties;
    }

    public void setProperties(String properties) {
        Properties = properties;
    }
    public String getEnquiry() {
        return Enquiry;
    }

    public void setEnquiry(String enquiry) {
        Enquiry = enquiry;
    }
}
