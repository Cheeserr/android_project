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
                + "QUESTION TEXT," +
                "A1 TEXT, " +
                "A2 TEXT, " +
                "A3 TEXT, " +
                "A4 TEXT, " +
                "A5 TEXT, " +
                "A6 TEXT, " +
                "A7 TEXT, " +
                "A8 TEXT, " +
                "A9 TEXT, " +
                "A10 TEXT);");


        ContentValues bankValues = new ContentValues();

        bankValues.put("NAME", "Biology");
        bankValues.put("NUMBEROFQUESTIONS", 1);
        db.insert("QUESTIONBANKS", null, bankValues);

        ContentValues questionValues = new ContentValues();

        questionValues.put("BANKID", "0");
        questionValues.put("QUESTION", "Dokad noca tupta jez");
        db.insert("QUESTIONS", null, questionValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
