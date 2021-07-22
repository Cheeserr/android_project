package com.example.quizz_app;

public class Question {

    String question;
    String[] answers;

    public Question(String questionInit, String[] answersInit){
        question = questionInit;
        if (answersInit.length + 1 >= 0)
            System.arraycopy(answersInit, 0, answers, 0, answersInit.length + 1);
    }

}
