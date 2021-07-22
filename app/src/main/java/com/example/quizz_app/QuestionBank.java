package com.example.quizz_app;

import java.util.ArrayList;

public class QuestionBank {

        String id;
        int totalQuestions = 0;
        ArrayList<Question> questions = new ArrayList<>();

        public QuestionBank(String idInit) {
            id = idInit;
        }

    void addQuestion(Question question){
            totalQuestions++;
            questions.add(question);
        }

        void removeQuestion(Question question){
            totalQuestions--;
            questions.remove(question);
        }

        String getID(){
            return id;
        }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "id='" + id + '\'' +
                ", totalQuestions=" + totalQuestions +
                ", questions=" + questions +
                '}';
    }
}

