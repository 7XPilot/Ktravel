package com.example.k_travel.model;

import java.io.Serializable;

public class RegisterModel implements Serializable {
    private String phone = "";
    private String password = "";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
