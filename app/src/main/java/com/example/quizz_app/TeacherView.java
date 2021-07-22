package com.example.quizz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class TeacherView extends AppCompatActivity {

    ArrayList<QuestionBank> banks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view);

        Button teacherButton = findViewById(R.id.createBankButton);
        teacherButton.setOnClickListener(v -> createBank());
    }



    void createBank(){

            AddBankDialog exampleDialog = new AddBankDialog();
            exampleDialog.show(getSupportFragmentManager(), "example");

        //String id = "1";
        //banks.add(new QuestionBank(id));
    }

    void deleteBank(){
        QuestionBank questionBank = null;
        banks.remove(questionBank);
    }

    void addQuestion(){
        QuestionBank questionBank = null;
        Question question = null;
        assert questionBank != null;
        questionBank.addQuestion(question);
    }

    void removeQuestion(){
        QuestionBank questionBank = null;
        Question question = null;
        assert questionBank != null;
        questionBank.removeQuestion(question);
    }

}