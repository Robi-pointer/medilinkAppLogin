package com.example.medilinkapplogin.userDataModel;

public class dataModel {

    int id;
    String name,email,phone,pass,bloodGroup,district,profession;

    public dataModel(String name, String email,String phone, String bloodGroup, String district, String profession) {
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.district = district;
        this.profession = profession;
    }

    public dataModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
