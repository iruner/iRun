package com.example.irun;

import android.database.Cursor;
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
        String account="create table account(userid text primary key ,password text)";
        String record="create table record(id integer primary key autoincrement,step float,distance float,calories float)";
        db.execSQL(userimfor);
        db.execSQL(record);
        db.execSQL(account);
    }


    void insert(float a,float b,float c){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.irun/db.db", null);
        String insert="insert into record(step,distance,calories) values('"+a+"','"+b+"','"+c+"')";
        db.execSQL(insert);
        }
    void insert1(String a,String b){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.irun/db.db", null);
        String insert="insert into account(userid,password) values('"+a+"','"+b+"')";
        db.execSQL(insert);
    }
    String select(String a){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.irun/db.db", null);

        Cursor result = db.query("account", null, "userid=" + a, null, null, null, null);
        result.moveToFirst();
        String b=null;
        while (!result.isAfterLast()){
            b=result.getString(1);
            result.moveToNext();
        }
        return b;
    }
    void delete(SQLiteDatabase db) {

        String sql = "delete from record where _id = 1";

        db.execSQL(sql);
    }




}
