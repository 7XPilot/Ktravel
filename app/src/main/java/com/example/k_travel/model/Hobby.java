package com.example.k_travel.model;

import java.io.Serializable;

public class Hobby implements Serializable {
    private String name;
    private boolean isChcek;

    public Hobby() {
    }

    public Hobby(String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChcek() {
        return isChcek;
    }

    public void setChcek(boolean chcek) {
        isChcek = chcek;
    }
}
