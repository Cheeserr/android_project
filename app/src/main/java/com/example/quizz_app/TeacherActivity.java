package com.example.quizz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> data1 = new ArrayList<>();
    ArrayList<Integer> data2 = new ArrayList<>();
    ArrayList<Integer> data3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        Button teacherButton = findViewById(R.id.createBankButton);
        teacherButton.setOnClickListener(v -> createBank(null));

        listBanks();

        recyclerView = findViewById(R.id.recyclerView);
        MyAdapter myAdapter = new MyAdapter(this, data1, data2, data3);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        //Switch simpleSwitch = findViewById(R.id.viewSwitch);
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
        Cursor cursor = db.query("QUESTIONBANKS", new String[] {"NAME","NUMBEROFQUESTIONS"},
                null, null, null, null, null);
        int i = 0;
        if(cursor.moveToFirst()){
            data1.add(cursor.getString(0));
            data2.add(cursor.getInt(1));
            data3.add(i);
            i++;
        }
        while(cursor.moveToNext()){
            data1.add(cursor.getString(0));
            data2.add(cursor.getInt(1));
            data3.add(i);
            i++;
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