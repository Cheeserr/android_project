package com.example.quizz_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity implements BankAdapter.OnNoteListener {

    RecyclerView recyclerView;
    ArrayList<QuestionBank> questionBanks = new ArrayList<>();

    SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);

    int pressedNode = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);

        listBanks();

        recyclerView = findViewById(R.id.recyclerView);
        BankAdapter myAdapter = new BankAdapter(this, questionBanks, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button takeAQuiz = findViewById(R.id.takeAQuiz);
        takeAQuiz.setOnClickListener(v -> chooseQuiz(pressedNode));
    }

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

    void chooseQuiz(int id){
        if(id >= 0) {
            String bankName = questionBanks.get(id).mName;
            String bankID = questionBanks.get(id).mId;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to take Quiz in " + bankName);

            builder.setPositiveButton("Continue", (dialog, which) -> quiz(bankID));
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        }else{
            Toast toast = Toast.makeText(this, "Choose Bank to take quizz in!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    void quiz(String id){
        // TODO
    }

    @Override
    public void onNoteClick(int position) {
        if(position == pressedNode){
            pressedNode = -1;
        }else{
            pressedNode = position;
        }
        System.out.println(pressedNode);
    }
}