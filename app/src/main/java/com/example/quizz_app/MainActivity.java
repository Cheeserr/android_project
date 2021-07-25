package com.example.quizz_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button teacherButton = findViewById(R.id.teacherButton);
        teacherButton.setOnClickListener(v -> openTeacherActivity());

        Button studentButton = findViewById(R.id.studentButton);
        studentButton.setOnClickListener(v -> openStudentActivity());

    }


    public void openTeacherActivity(){
        Intent intent = new Intent(this, TeacherActivity.class);
        startActivity(intent);
    }

    public void openStudentActivity(){
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }
}