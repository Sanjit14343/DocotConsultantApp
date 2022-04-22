package com.men.hospitalsystem;

public class MainModel {

    String fullName, gender, phone, image;
    MainModel(){

    }


    public MainModel(String fullName, String gender, String phone, String image) {
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
