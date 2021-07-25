package com.example.quizz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {

    SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    /**try {

    } catch(SQLiteException e){
        Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
        toast.show();
    } */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        Button teacherButton = findViewById(R.id.createBankButton);
        teacherButton.setOnClickListener(v -> createBank());
    }


    // FR1
    void createBank(){
    }

    // FR2
    void addQuestion(){

    }
    // FR3
    void listQuestions(){

    }
    // FR3
    void removeQuestion(){

    }
    // FR4
    void listBanks(){

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);
        try {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("QUESTIONBANKS", new String[] {"_id","NAME","NUMBEROFQUESTIONS"},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            String nameBank = cursor.getString(0);
            int numberOfQuestions = cursor.getInt(1);
        }

         } catch(SQLiteException e){
         Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
         toast.show();
         }
    }
    // FR5
    void deleteBank(){

    }


}