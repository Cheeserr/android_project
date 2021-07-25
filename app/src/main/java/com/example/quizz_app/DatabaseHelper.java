package com.example.quizz_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Question Banks";
            private static final int DB_VERSION = 1;

    DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE QUESTIONBANKS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "NUMBEROFQUESTIONS INTEGER);");

        db.execSQL("CREATE TABLE QUESTIONS ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "BANKID INTEGER, "
                + "QUESTION TEXT, "
                + "ANSWEAR TEXT);");

        ContentValues bankValues = new ContentValues();

        bankValues.put("Name", "Biology");
        bankValues.put("NUMBEROFQUESTIONS", 5);
        db.insert("QUESTIONBANKS", null, bankValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
