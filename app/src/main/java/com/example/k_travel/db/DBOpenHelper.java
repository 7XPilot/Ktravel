package com.example.k_travel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = "travel.db";

    private static final int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //account
        db.execSQL("CREATE TABLE tb_user(_id integer primary key autoincrement,phone varchar(30),pwd varchar(10), header varchar(255), gender varchar(10), hobby varchar(255))");

        //scence
        db.execSQL("CREATE TABLE tb_scence(_id integer primary key autoincrement," +
                "name varchar(100)," +
                "introd varchar(1000)," +
                "eval varchar(10)," +
                "price varchar(10)," +
                "url varchar(255), " +
                "phone varchar(25), " +
                "email varchar(50))");
        db.execSQL("insert into tb_scence values (1,'Hobbiton™ Movie Set Tours','Experience the real Middle-earth with a visit to the Hobbiton Movie Set, featured in The Lord of the Rings and The Hobbit films.','$210','199.00','https://www.newzealand.com/assets/Operator-Database/3fc2b5d066/img-1536162697-1401-3228-p-95B514EA-DAAA-9CB0-6839532442B3132A-2544003__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.jpg','64 7 888 1505','office@Newzealandtontours.com')");
        db.execSQL("insert into tb_scence values (2,'Ziptrek Ecotours','Ziptrek Ecotours is NZs # 1 Original Zipline Tour','$215','199.00','https://www.newzealand.com/assets/Operator-Database/ceb3e6e816/img-1536356630-5962-3797-p-8231EEC0-F82D-775E-17EEFB9703A509F5-2544003__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.jpg','64 7 888 1505','office@Newzealandtontours.com')");
        db.execSQL("insert into tb_scence values (3,'AJ Hackett Bungy - Ledge Swing (47M)','Pull the pin - and drop! At 400m above Queenstown the Ledge Swing takes swinging to a whole new level','$180','165.00','https://www.newzealand.com/assets/Operator-Database/img-1605563899-1403-7299-landscape_1080x630-ls_2__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.jpg','64 7 888 1505','office@Newzealandtontours.com')");
        db.execSQL("insert into tb_scence values (4,'Waiheke Horse Tours','Ride across a secluded part of Waiheke. Private tours.','$380','345','https://www.newzealand.com/assets/Operator-Database/56ebda4923/img-1570045086-2515-1334-p-312A7EEE-A847-3A98-0C8D0EF12732299B-2544003__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.jpg','64 7 888 1505','office@Newzealandtontours.com')");
        db.execSQL("insert into tb_scence values (5,'Diveworks Charters Whales and Dolphin Watch','Your experience of a lifetime...swim with dolphins and Seals with occasional sightings of whales in their natural environment.','$180','160','https://www.newzealand.com/assets/Tourism-NZ/Auckland/aca288564e/img-1536235425-6216-4224-A570D2B6-DFE5-CD46-88DFCABD4161044B__aWxvdmVrZWxseQo_FocalPointCropWzY2MCwxOTIwLDUwLDUwLDc1LCJqcGciLDY1LDIuNV0.jpg','64 7 888 1505','office@Newzealandtontours.com')");
        db.execSQL("insert into tb_scence values (6,'Orakei Korako Cave & Thermal Park','Lonely Planet Travellers Guide writes: Orakei Korako is arguably the best thermal area left in New Zealand.','$50','42','https://www.newzealand.com/assets/Operator-Database/f38bd6b736/img-1536238232-2008-16654-p-46820400-A2FA-A7D6-0D9BA8D7CB3F1E3B-2544003__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.jpg','64 7 888 1505','240519937@126.com')");
        db.execSQL("insert into tb_scence values (7,'Tamaki Māori Village','Tamaki Māori Village is the only cultural experience in New Zealand to be the recipient of the Supreme New Zealand Tourism Award','$150','135','https://www.newzealand.com/assets/Operator-Database/img-1626062353-6801-3157-2__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.-tmv-wero.jpg','64 7 888 1505','office@Newzealandtontours.com')");
        db.execSQL("insert into tb_scence values (8,'Hell Gate Geothermal Reserve and Mud Spa','More than just a place of myth and legend, this land has the power to heal and excite anyone who meets it, just as its done for centuries. Come and discover New Zealand’s most unique geothermal experience.','$125','99','https://www.newzealand.com/assets/Operator-Database/img-1606349373-1212-8834-_dsc7255__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.jpg','64 7 888 1505','office@Newzealandtontours.com')");
        db.execSQL("insert into tb_scence values (9,'Waimangu Volcanic Valley','Waimangu connects people with the beating heart of the world’s youngest geothermal valley, its unique history and legacy.','$280','245','https://www.newzealand.com/assets/Operator-Database/img-1625976127-3817-25183-waimangu-volcanic-valley-have-you-ever-miles-holden-2__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.jpg','64 7 888 1505','office@Newzealandtontours.com')");
        db.execSQL("insert into tb_scence values (10,'Cathedral Cove','Cathedral Cove is arguably one of the most picturesque spots (and there are many) in The Coromandel.','$10','0','https://www.newzealand.com/assets/Operator-Database/039200ceea/img-1576377032-1775-7164-p-D8C813F4-CE2D-F3F3-EEFBC4C5818F1211-2544003__aWxvdmVrZWxseQo_CropResizeWzk0MCw1MzAsNzUsImpwZyJd.jpg','64 7 888 1505','office@Newzealandtontours.com')");

        //booking
        db.execSQL("CREATE TABLE tb_booking(_id integer primary key autoincrement,scenceId interger(10), userId interger(10), date varchar(30))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS note");
        onCreate(db);
    }

}
