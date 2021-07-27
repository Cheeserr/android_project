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

public class StudentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> data1 = new ArrayList<>();
    ArrayList<Integer> data2 = new ArrayList<>();
    ArrayList<Integer> data3 = new ArrayList<>();

    SQLiteOpenHelper databaseHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);

        listBanks();

        recyclerView = findViewById(R.id.recyclerView);
        BankAdapter bankAdapter = new BankAdapter(this, data1, data2, data3);
        recyclerView.setAdapter(bankAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button takeAQuiz = findViewById(R.id.takeAQuiz);
        takeAQuiz.setOnClickListener(v -> chooseQuiz());
    }

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
}