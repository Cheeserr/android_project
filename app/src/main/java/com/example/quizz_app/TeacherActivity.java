package com.example.quizz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> data1 = new ArrayList<>();
    ArrayList<Integer> data2 = new ArrayList<>();
    ArrayList<Integer> data3 = new ArrayList<>();

    SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        listBanks();

        recyclerView = findViewById(R.id.recyclerView);
        bankAdapter bankAdapter = new bankAdapter(this, data1, data2, data3);
        bankAdapter questionAdapter = new bankAdapter(this, data1, data2, data3);
        recyclerView.setAdapter(bankAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button createBank = findViewById(R.id.createBankButton);
        createBank.setOnClickListener(v -> {
            inputDialogBank();
            bankAdapter.notifyDataSetChanged();
        });


        Button deleteBank = findViewById(R.id.deleteBank);
        deleteBank.setOnClickListener(v -> {
            deleteDialog();
            bankAdapter.notifyDataSetChanged();
        });

        Button addQuestion = findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(v -> {
            inputDialogQuestion();
            questionAdapter.notifyDataSetChanged();
        });


        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch simpleSwitch = findViewById(R.id.viewSwitch);
        simpleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                simpleSwitch.setText(R.string.questionListViewText);
                listQuestions();
                recyclerView.setAdapter(questionAdapter);
                questionAdapter.notifyDataSetChanged();
            }else{
                simpleSwitch.setText(R.string.listViewText);
                listBanks();
                recyclerView.setAdapter(bankAdapter);
                bankAdapter.notifyDataSetChanged();
            }
        });
    }


    // FR1
    void inputDialogBank(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type name of your Question Bank");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Continue", (dialog, which) -> createBank(input.getText().toString()));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    void createBank(String input){
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues bankValues = new ContentValues();

            bankValues.put("Name", input);
            bankValues.put("NUMBEROFQUESTIONS", 0);
            db.insert("QUESTIONBANKS", null, bankValues);
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        listBanks();
    }

    // FR2
    void inputDialogQuestion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your Question");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Continue", (dialog, which) -> {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                    builder2.setTitle("Type name of your Question Bank");

                    final EditText input2 = new EditText(this);
                    input2.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder2.setView(input2);

                    builder2.setPositiveButton("Continue", (dialog2, which2) -> {
                        if(addQuestionToBank(input2.getText().toString()))
                        addQuestion(input.getText().toString(), input2.getText().toString());
                    });

            builder2.setNegativeButton("Cancel", (dialog2, which2) -> dialog.cancel());

            builder2.show();
                });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    void addQuestion(String questionInput, String idInput){
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues questionValues = new ContentValues();

            questionValues.put("QUESTION", questionInput);
            questionValues.put("BANKID", idInput);
            db.insert("QUESTIONS", null, questionValues);
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        listQuestions();
    }

    boolean addQuestionToBank(String id){
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues noOfQuestions = new ContentValues();

            Cursor cursor = db.query("QUESTIONBANKS", new String[]{"_id", "NUMBEROFQUESTIONS"},
                    null, null, null, null, null, null);
            int number;
            if(cursor.moveToFirst()){
                do {
                    number = cursor.getInt(0);
                    if(number == Integer.parseInt(id)){
                        number = cursor.getInt(1);
                        noOfQuestions.put("NUMBEROFQUESTIONS", number + 1);
                        db.update("QUESTIONBANKS", noOfQuestions, "_id = ?", new String[] {id});
                        return true;
                    }
                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return false;
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }
    // FR3
    void listQuestions(){
        try {
            data1.clear();
            data2.clear();
            data3.clear();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("QUESTIONS", new String[] {"_id","BANKID","QUESTION"},
                    null, null, null, null, null);
            if(cursor.moveToFirst()){
                data1.add(cursor.getString(2));
                data2.add(cursor.getInt(1));
                data3.add(cursor.getInt(0));
            }
            while(cursor.moveToNext()){
                data1.add(cursor.getString(2));
                data2.add(cursor.getInt(1));
                data3.add(cursor.getInt(0));
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
        try {
            data1.clear();
            data2.clear();
            data3.clear();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("QUESTIONBANKS", new String[] {"_id", "NAME","NUMBEROFQUESTIONS"},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            data1.add(cursor.getString(1));
            data2.add(cursor.getInt(2));
            data3.add(cursor.getInt(0));
        }
        while(cursor.moveToNext()){
            data1.add(cursor.getString(1));
            data2.add(cursor.getInt(2));
            data3.add(cursor.getInt(0));
        }
        cursor.close();
        db.close();
         } catch(SQLiteException e){
         Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
         toast.show();
         }

    }
    // FR5
    void deleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type id of your Question Bank");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Continue", (dialog, which) -> deleteBank(input.getText().toString()));

        builder.show();
    }

    void deleteBank(String id) {

        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            db.delete("QUESTIONBANKS", "_id = " + id, null);
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        listBanks();
    }
}