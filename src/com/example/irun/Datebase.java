package com.example.irun;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 31 on 2015/10/24.
 */
public class Datebase {

  /*  void createdatabase()

    {

    try {
    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.irun/db.db", null);
    } catch (Exception e){e.getMessage();}
    }
    */
    void createtable(){

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.irun/db.db", null);
        String userimfor="create table userimfor(id integer primary key autoincrement,userid text)";
        String record="create table record(id integer primary key autoincrement,step float,distance float,calories float)";
        db.execSQL(userimfor);
        db.execSQL(record);
    }


    void insert(float a,float b,float c){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.irun/db.db", null);
        String insert="insert into record(step,distance,calories) values('"+a+"','"+b+"','"+c+"')";
        db.execSQL(insert);
        }

    void delete(SQLiteDatabase db) {

        String sql = "delete from record where _id = 1";

        db.execSQL(sql);
    }




}
