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

public class TeacherActivity extends AppCompatActivity implements BankAdapter.OnNoteListener {

    RecyclerView recyclerView;

    int pressedNode = -1;

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
            myAdapter.notifyDataSetChanged();
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            if(pressedNode >= 0 ){
                if (!simpleSwitch.isChecked()) deleteBank(pressedNode);
                else removeQuestion(pressedNode);
            }else{
                Toast toast = Toast.makeText(this, "Choose item to delete", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Button addQuestion = findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(v -> {
            addQuestionInput();
            questionAdapter.notifyDataSetChanged();
        });

        simpleSwitch = findViewById(R.id.viewSwitch);
        simpleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> changeList(isChecked));
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
        myAdapter.row_index = -1;
        questionAdapter.row_index = -1;
        pressedNode = -1;
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
        recyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        simpleSwitch.setChecked(false);
    }

    // FR2
    void addQuestionInput(){
        if(pressedNode >= 0 && !simpleSwitch.isChecked()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            listBanks();
            builder.setTitle("Enter question for " + questionBanks.get(pressedNode).mName);

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("Continue", (dialog, which) -> {
                addQuestion(input.getText().toString());

            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        }else if(simpleSwitch.isChecked()){
            Toast toast = Toast.makeText(this, "Change list and choose bank to add question to!", Toast.LENGTH_SHORT);
            toast.show();
        } else{
            Toast toast = Toast.makeText(this, "Choose bank to add question to!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    void addQuestion(String questionInput){
        try {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues questionValues = new ContentValues();
            String bankId = questionBanks.get(pressedNode).mId;

            questionValues.put("QUESTION", questionInput);
            questionValues.put("BANKID", bankId);
            db.insert("QUESTIONS", null, questionValues);

            ContentValues bankValues = new ContentValues();
            int value = Integer.parseInt(questionBanks.get(pressedNode).mData);
            value++;
            bankValues.put("NUMBEROFQUESTIONS", String.valueOf(value));
            db.update("QUESTIONBANKS", bankValues, "_id = " + bankId, null);
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        listQuestions();
        recyclerView.setAdapter(questionAdapter);
        questionAdapter.notifyDataSetChanged();
        simpleSwitch.setChecked(true);
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
            String questionBankId = questionBanks.get(id).mData;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure you want to delete " + questionName);

            builder.setPositiveButton("Yes", (dialog, which) -> {
                db.delete("QUESTIONS", "_id = " + questionId, null);

                Cursor cursor = db.query("QUESTIONBANKS", new String[]{"_id", "NUMBEROFQUESTIONS"},
                        null, null, null, null, null);
                cursor.moveToFirst();
                    while(cursor.getInt(0) != Integer.parseInt(questionBankId)){
                        cursor.moveToNext();
                    }
                ContentValues bankValues = new ContentValues();
                int value = Integer.parseInt(cursor.getString(1));
                value--;
                bankValues.put("NUMBEROFQUESTIONS", String.valueOf(value));
                db.update("QUESTIONBANKS", bankValues, "_id = " + questionBankId, null);
                cursor.close();
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
                db.delete("QUESTIONS", "BANKID = " + bankId, null);
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
        System.out.println(position);
        if(position == pressedNode){
            pressedNode = -1;
        }else{
            pressedNode = position;
        }
    }
}