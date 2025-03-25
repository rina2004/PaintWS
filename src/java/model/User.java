/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author anhbu
 */
public class User {

    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String country;
    private boolean acceptTerms;
    private boolean acceptMarketing;

    private int userID;
    private int roleID;

    // Constructors
    public User() {
    }

    public User(String username, String password, String fullname, String email,
            String phoneNumber, String country, boolean acceptTerms, boolean acceptMarketing) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.acceptTerms = acceptTerms;
        this.acceptMarketing = acceptMarketing;
    }

    public User(String username, String password, String country, String phoneNumber, String email, int roleID, int userID) {
        this.username = username;
        this.password = password;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roleID = roleID;
        this.userID = userID;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullName) {
        this.fullname = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isAcceptTerms() {
        return acceptTerms;
    }

    public void setAcceptTerms(boolean acceptTerms) {
        this.acceptTerms = acceptTerms;
    }

    public boolean isAcceptMarketing() {
        return acceptMarketing;
    }

    public void setAcceptMarketing(boolean acceptMarketing) {
        this.acceptMarketing = acceptMarketing;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }
}
