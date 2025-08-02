package com.example.medilinkapplogin.user;

public class UserSession {
    private static UserSession instance;

    private String name;
    private String phone;
    private String email;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUser(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;

    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
