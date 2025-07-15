package com.example.medilinkapplogin.user;

public class userInfo {
    String name,email,phone,bloodGroup;

    public userInfo() {

    }

    public userInfo(String name, String email, String phone,String bloodGroup) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
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
    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
