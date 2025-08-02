package com.example.medilinkapplogin.userDataModel;

public class BloodRequestModel {
    private String patientName, bloodGroup, phone, donorPhone;

    public BloodRequestModel(String patientName, String bloodGroup, String phone, String donorPhone) {
        this.patientName = patientName;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.donorPhone = donorPhone;
    }

    // Getters...


    public String getPatientName() {
        return patientName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public String getDonorPhone() {
        return donorPhone;
    }
}
