package com.example.merhaba;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class Database extends SQLiteOpenHelper {


        private static final int DATABASE_VERSION = 1;


        private static final String DATABASE_NAME = "user_database";//database adı
        private static final String TABLE_NAME = "users_information";
        private static String UID = "user_id";
        private static String USER_NAME = "isim";
        private static String USER_SURNAME = "soyisim";


        public Database(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                    + UID + " INTEGER PRIMARY KEY,"
                    + USER_NAME + " TEXT,"
                    + USER_SURNAME + " TEXT" +

                     ")";
            db.execSQL(CREATE_TABLE);
        }



        public void addUser(String uid, String name,String surname) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UID, uid);
            values.put(USER_NAME, name);
            values.put(USER_SURNAME, surname);

            db.insert(TABLE_NAME, null, values);
            db.close(); //Database Bağlantısını kapattık*/
        }

        public HashMap<String, String> detailUser(int id){

            HashMap<String,String> users = new HashMap<String,String>();
            String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            cursor.moveToFirst();
            if(cursor.getCount() > 0){
                users.put(USER_NAME, cursor.getString(1));
                users.put(USER_SURNAME, cursor.getString(2));
            }
            cursor.close();
            db.close();

            return users;
        }

        public ArrayList<HashMap<String, String>> getUsers(){


            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            ArrayList<HashMap<String, String>> kitaplist = new ArrayList<HashMap<String, String>>();


            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    for(int i=0; i<cursor.getColumnCount();i++)
                    {
                        map.put(cursor.getColumnName(i), cursor.getString(i));
                    }

                    kitaplist.add(map);
                } while (cursor.moveToNext());
            }
            db.close();
            return kitaplist;
        }
        public void editUser(String name,String surname,int id) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(USER_NAME, name);
            values.put(USER_SURNAME, surname);



            db.update(TABLE_NAME, values, UID + " = ?",
                    new String[] { String.valueOf(id) });
        }
        public void resetTables(){
            //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
            SQLiteDatabase db = this.getWritableDatabase();
            // Delete All Rows
            db.delete(TABLE_NAME, null, null);
            db.close();
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

        }
    }