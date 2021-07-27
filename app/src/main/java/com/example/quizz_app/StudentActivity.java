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
        takeAQuiz.setOnClickListener(v -> chooseQuiz());
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

    void chooseQuiz(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Id of Question Bank");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Continue", (dialog, which) -> quiz(Integer.parseInt(input.getText().toString())));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    void quiz(int id){
        // TODO
    }

    @Override
    public void onNoteClick(int position) {
        System.out.println(position);
    }
}