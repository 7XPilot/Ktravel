package com.example.k_travel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.k_travel.been.Booking;
import com.example.k_travel.been.Scence;
import com.example.k_travel.been.User;

import java.util.ArrayList;

public class DBDao {

    private DBOpenHelper dbOpenHelper;

    public DBDao(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
    }


    /**
     * 根据phone获取用户
     */
    public User searchUserByPhone(String phone) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("tb_user", new String[]{"_id", "phone", "pwd", "header", "gender", "hobby"}, " phone =?", new String[]{phone}, null, null, null);
        User user = null;
        while (cursor.moveToNext()) {
            user = new User();
            user.id = cursor.getInt(cursor.getColumnIndex("_id"));
            user.phone = cursor.getString(cursor.getColumnIndex("phone"));
            user.pwd = cursor.getString(cursor.getColumnIndex("pwd"));
            user.header = cursor.getString(cursor.getColumnIndex("header"));
            user.gender = cursor.getString(cursor.getColumnIndex("gender"));
            user.hobby = cursor.getString(cursor.getColumnIndex("hobby"));
        }
        db.close();
        return user;
    }

    /**
     * 添加用户
     */
    public void addUser(User user) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", user.phone);
        values.put("pwd", user.pwd);
        values.put("header", "");
        values.put("gender", "");
        values.put("hobby", "");
        db.insert("tb_user", "_id", values);
        db.close();
    }

    /**
     * 修改用户信息  修改密码
     */
    public void updateUser(User user) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", user.phone);
        values.put("pwd", user.pwd);
        values.put("header", TextUtils.isEmpty(user.header) ? "" : user.header);
        values.put("gender", TextUtils.isEmpty(user.gender) ? "" : user.gender);
        values.put("hobby", TextUtils.isEmpty(user.hobby) ? "" : user.hobby);
        db.update("tb_user", values, "_id=?", new String[]{String.valueOf(user.id)});
    }


    /**
     * @param scence
     */
    public void addScence(Scence scence) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", scence.getName());
        values.put("introd", scence.getIntrod());
        values.put("eval", scence.getEval());
        values.put("price", scence.getPrice());
        values.put("url", scence.getUrl());
        values.put("phone", scence.getPhone());
        values.put("email", scence.getEmail());
        db.insert("tb_scence", "_id", values);
        db.close();
    }


    /**
     * @param scence
     */
    public void deleteScence(Scence scence) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("tb_scence", "_id=?", new String[]{String.valueOf(scence.getId())});
        db.close();
    }


    /**
     * @return
     */
    public ArrayList<Scence> searchAllScence() {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        Cursor cursor = db.query("tb_scence", new String[]{"_id", "name", "introd", "eval", "price", "url", "phone", "email"}, "", new String[]{}, null, null, "_id");
        ArrayList<Scence> list = new ArrayList<Scence>();
        while (cursor.moveToNext()) {
            Scence project = new Scence();
            project.id = cursor.getInt(cursor.getColumnIndex("_id"));
            project.name = cursor.getString(cursor.getColumnIndex("name"));
            project.introd = cursor.getString(cursor.getColumnIndex("introd"));
            project.eval = cursor.getString(cursor.getColumnIndex("eval"));
            project.price = cursor.getString(cursor.getColumnIndex("price"));
            project.url = cursor.getString(cursor.getColumnIndex("url"));
            project.phone = cursor.getString(cursor.getColumnIndex("phone"));
            project.email = cursor.getString(cursor.getColumnIndex("email"));
            list.add(project);
        }
        db.close();
        return list;
    }


    public Scence searchScenceById(String scenceId) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        Cursor cursor = db.query("tb_scence", new String[]{"_id", "name", "introd", "eval", "price", "url", "phone", "email"}, " _id =?", new String[]{scenceId}, null, null, "null");
        Scence scence = new Scence();
        while (cursor.moveToNext()) {
            scence.id = cursor.getInt(cursor.getColumnIndex("_id"));
            scence.name = cursor.getString(cursor.getColumnIndex("name"));
            scence.introd = cursor.getString(cursor.getColumnIndex("introd"));
            scence.eval = cursor.getString(cursor.getColumnIndex("eval"));
            scence.price = cursor.getString(cursor.getColumnIndex("price"));
            scence.url = cursor.getString(cursor.getColumnIndex("url"));
            scence.phone = cursor.getString(cursor.getColumnIndex("phone"));
            scence.email = cursor.getString(cursor.getColumnIndex("email"));
        }
        db.close();
        return scence;
    }


    /**
     * @param booking
     */
    public int addBooking(Booking booking) {
        int result = 0;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("scenceId", booking.getScenceId());
        values.put("userId", booking.getUserId());
        values.put("date", booking.getDate());
        result = (int) db.insert("tb_booking", "_id", values);
        db.close();
        return result;
    }


    /**
     * @param userId
     */
    public ArrayList<Booking> searchBookingByUser(String userId) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("tb_booking", new String[]{"_id", "scenceId", "userId", "date"}, " userId =?", new String[]{userId}, null, null, null);
        ArrayList<Booking> list = new ArrayList<Booking>();
        while (cursor.moveToNext()) {
            Booking plan = new Booking();
            plan.id = cursor.getInt(cursor.getColumnIndex("_id"));
            plan.scenceId = cursor.getInt(cursor.getColumnIndex("scenceId"));
            plan.userId = Integer.parseInt(userId);
            plan.date = cursor.getString(cursor.getColumnIndex("date"));
            list.add(plan);
        }
        db.close();
        return list;
    }


    /**
     * @param booking
     */
    public void deleteBooking(Booking booking) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("tb_booking", "_id=?", new String[]{String.valueOf(booking.getId())});
        db.close();
    }

}
