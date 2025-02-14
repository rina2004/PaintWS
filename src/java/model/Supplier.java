/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author anhbu
 */
public class Supplier {
    private int id;
    private String companyName, contactName, country, phone;

    public Supplier(int id, String companyName, String contactName, String country, String phone) {
        this.id = id;
        this.companyName = companyName;
        this.contactName = contactName;
        this.country = country;
        this.phone = phone;
    }

    public Supplier() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Supplier{" + "id=" + id + ", companyName=" + companyName + ", contactName=" + contactName + ", country=" + country + ", phone=" + phone + '}';
    }
    
    
}
