package com.men.hospitalsystem;

public class UserHelperClass {

    String fullName, idNumber, email, password, phone, gender;

    public UserHelperClass( ) {

    }

    public UserHelperClass(String fullName, String idNumber, String email, String password, String phone, String gender) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
