package com.example.k_travel.db;

import android.content.Context;

import com.example.k_travel.been.Booking;
import com.example.k_travel.been.Scence;
import com.example.k_travel.been.User;

import java.util.ArrayList;


/**
 * @author
 */
public class DBServer {

    /**
     * @param context
     * @param phone
     */
    public static User searchUserByPhone(Context context, String phone) {
        DBDao dbDao = new DBDao(context);
        return dbDao.searchUserByPhone(phone);
    }

    /**
     * @param user
     */
    public static void addUser(Context context, User user) {
        DBDao dbDao = new DBDao(context);
        dbDao.addUser(user);
    }

    /**
     * @param user
     */
    public static void updateUser(Context context, User user) {
        DBDao dbDao = new DBDao(context);
        dbDao.updateUser(user);
    }


    /**
     * @param scence
     */
    public static void addScence(Context context, Scence scence) {
        DBDao dbDao = new DBDao(context);
        dbDao.addScence(scence);
    }

    /**
     * @param scence
     */
    public static void deleteScence(Context context, Scence scence) {
        DBDao dbDao = new DBDao(context);
        dbDao.deleteScence(scence);
    }

    /**
     * @return
     */
    public static ArrayList<Scence> searchAllScence(Context context) {
        DBDao dbDao = new DBDao(context);
        return dbDao.searchAllScence();
    }

    /**
     * @return
     */
    public static Scence searchScenceById(Context context, int scenceId) {
        DBDao dbDao = new DBDao(context);
        return dbDao.searchScenceById("" + scenceId);
    }


    /**
     * @param booking
     */
    public static int addBooking(Context context, Booking booking) {
        DBDao dbDao = new DBDao(context);
        return dbDao.addBooking(booking);
    }

    /**
     * @param userId
     */
    public static ArrayList<Booking> searchBookingByUser(Context context, int userId) {
        DBDao dbDao = new DBDao(context);
        return dbDao.searchBookingByUser("" + userId);
    }


    /**
     * @param booking
     */
    public static void deleteBooking(Context context, Booking booking) {
        DBDao dbDao = new DBDao(context);
        dbDao.deleteBooking(booking);
    }
}
