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
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity implements BankAdapter.OnNoteListener {

    RecyclerView recyclerView;
    boolean deleteMode = false;

    ArrayList<QuestionBank> questionBanks = new ArrayList<>();

    BankAdapter questionAdapter = new BankAdapter(this, questionBanks, this);
    BankAdapter myAdapter = new BankAdapter(this, questionBanks, this);

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch simpleSwitch;
    Button delete;

    SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        listBanks();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        Button createBank = findViewById(R.id.createBankButton);
        createBank.setOnClickListener(v -> {
            createBankInput();
            deleteMode = false;
            changeDeleteMode();
            myAdapter.notifyDataSetChanged();
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            deleteMode = !deleteMode;
            changeDeleteMode();
        });

        Button addQuestion = findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(v -> {
            addQuestionInput();
            deleteMode = false;
            changeDeleteMode();
            questionAdapter.notifyDataSetChanged();
        });

        simpleSwitch = findViewById(R.id.viewSwitch);
        simpleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> changeList(isChecked));
    }

    void changeDeleteMode(){
        if (deleteMode) {
            delete.setBackgroundColor(Color.GRAY);
        }else{
            delete.setBackgroundColor(Color.WHITE);
        }
    }

    void changeList(boolean isChecked){
        if(isChecked) {
            simpleSwitch.setText(R.string.questionListViewText);
            listQuestions();
            recyclerView.setAdapter(questionAdapter);
            questionAdapter.notifyDataSetChanged();
        }else{
            simpleSwitch.setText(R.string.listViewText);
            listBanks();
            recyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }
    }


    // FR1
    void createBankInput(){
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
    void addQuestionInput(){
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
            questionBanks.clear();
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("QUESTIONS", new String[] {"_id","QUESTION","BANKID"},
                    null, null, null, null, null);
            if(cursor.moveToFirst()){
                questionBanks.add(new QuestionBank(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            while(cursor.moveToNext()){
                questionBanks.add(new QuestionBank(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    // FR3
    void removeQuestion(int id){
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            String questionName = questionBanks.get(id).mName;
            String questionId = questionBanks.get(id).mId;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure you want to delete " + questionName);

            builder.setPositiveButton("Yes", (dialog, which) -> {
                db.delete("QUESTIONS", "_id = " + questionId, null);
                db.close();
                listQuestions();
                questionAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                db.close();
                dialog.cancel();
            });

            builder.show();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // FR4
    void listBanks(){
        try {
            questionBanks.clear();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("QUESTIONBANKS", new String[] {"_id", "NAME","NUMBEROFQUESTIONS"},
                null, null, null, null, null);
        if(cursor.moveToFirst()){
            questionBanks.add(new QuestionBank(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
        while(cursor.moveToNext()){
            questionBanks.add(new QuestionBank(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
        db.close();
         } catch(SQLiteException e){
         Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
         toast.show();
         }

    }
    // FR5
    void deleteBank(int id) {
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            String bankName = questionBanks.get(id).mName;
            String bankId = questionBanks.get(id).mId;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure you want to delete " + bankName);

            builder.setPositiveButton("Yes", (dialog, which) -> {
                db.delete("QUESTIONBANKS", "_id = " + bankId, null);
                db.close();
                listBanks();
                myAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (dialog, which) -> {
                db.close();
                dialog.cancel();
            });

            builder.show();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onNoteClick(int position) {
        if(deleteMode) {
            if (simpleSwitch.isChecked()) {
                removeQuestion(position);
            } else {
                deleteBank(position);
            }
        }else{

        }
    }
}