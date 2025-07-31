package com.example.medilinkapplogin.userDataModel;

public class dataModel {

    int id;
    String name,email,phone,pass,bloodGroup,district,profession;
    String  diabetes,hepatitis,operation,vaccine,high_blood_pressure,donation_date;

    public dataModel(String name, String email,String pass,String phone, String bloodGroup, String district,
                     String profession,String diabetes,String hepatitis,String operation,
                     String high_blood_pressure,String vaccine,String donation_date) {
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.bloodGroup = bloodGroup;
        this.district = district;
        this.profession = profession;
        this.diabetes = diabetes;
        this.hepatitis = hepatitis;
        this.operation = operation;
        this.high_blood_pressure = high_blood_pressure;
        this.vaccine = vaccine;
        this.donation_date = donation_date;
    }

    public dataModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getHepatitis() {
        return hepatitis;
    }

    public void setHepatitis(String hepatitis) {
        this.hepatitis = hepatitis;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVaccine(String vaccine) {
        this.vaccine = vaccine;
    }

    public String getHigh_blood_pressure() {
        return high_blood_pressure;
    }

    public void setHigh_blood_pressure(String high_blood_pressure) {
        this.high_blood_pressure = high_blood_pressure;
    }

    public String getDonation_date() {
        return donation_date;
    }

    public void setDonation_date(String donation_date) {
        this.donation_date = donation_date;
    }
}
