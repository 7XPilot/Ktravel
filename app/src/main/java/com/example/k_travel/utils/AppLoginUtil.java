package com.example.k_travel.utils;

import android.content.Context;

import com.example.k_travel.MyApp;
import com.example.k_travel.been.User;


public class AppLoginUtil {

    private static final long THREE_DAYS = 3 * 24 * 60 * 60 * 1000;

    public static boolean hasLogin() {
        Context context = MyApp.getInstance();
        if (SPUtil.get(context, SPUtil.IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }


    public static User createLoginModel() {
        Context context = MyApp.getInstance();
        User loginModel = new User();
        loginModel.setId(SPUtil.get(context, SPUtil.USER_ID, 0));
        loginModel.setPhone(SPUtil.get(context, SPUtil.MOBILE, ""));
        loginModel.setPwd(SPUtil.get(context, SPUtil.PASSWORD, ""));
        loginModel.setHeader(SPUtil.get(context, SPUtil.HEADER, ""));
        loginModel.setGender(SPUtil.get(context, SPUtil.GENDER, ""));
        loginModel.setHobby(SPUtil.get(context, SPUtil.HOBBY, ""));
        return loginModel;
    }
}
