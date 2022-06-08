package com.example.k_travel.model;

import com.youth.banner.Banner;

import java.io.Serializable;

public class BannerModel implements Serializable {
    private String imgUrl;
    private String url;

    public BannerModel(){}

    public BannerModel(String i, String u){
        this.imgUrl = i;
        this.url = u;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
