package com.example.k_travel.been;

import java.io.Serializable;

public class Booking implements Serializable {
    public int id;

    public int scenceId;

    public int userId;

    public String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScenceId() {
        return scenceId;
    }

    public void setScenceId(int scenceId) {
        this.scenceId = scenceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
