package com.example.quizz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class TeacherActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        Button teacherButton = findViewById(R.id.createBankButton);
        teacherButton.setOnClickListener(v -> createBank(null));

        Switch simpleSwitch = findViewById(R.id.viewSwitch);


    }


    // FR1
    void createBank(String name){
        SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues bankValues = new ContentValues();

            bankValues.put("Name", name);
            bankValues.put("NUMBEROFQUESTIONS", 0);
            db.insert("QUESTIONBANKS", null, bankValues);
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // FR2
    void addQuestion(){

    }
    // FR3
    void listQuestions(){

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
        try {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("QUESTIONS", new String[] {"_id","BANKID","QUESTION"},
                    null, null, null, null, null);
            if(cursor.moveToFirst()){
                int bankID = cursor.getInt(0);
                String question = cursor.getString(1);
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    // FR3
    void removeQuestion(){

    }
    // FR4
    void listBanks(){

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
        try {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("QUESTIONBANKS", new String[] {"_id","NAME","NUMBEROFQUESTIONS"},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            String nameBank = cursor.getString(0);
            int numberOfQuestions = cursor.getInt(1);
        }
        cursor.close();
        db.close();
         } catch(SQLiteException e){
         Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
         toast.show();
         }

    }
    // FR5
    void deleteBank(){

    }


}