package com.example.quizz_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class bankAdapter extends RecyclerView.Adapter<bankAdapter.MyViewHolder> {

    ArrayList<String> data1;
    ArrayList<Integer> data2;
    ArrayList<Integer> data3;
    Context context;

    public bankAdapter(Context ct, ArrayList<String> s1, ArrayList<Integer> s2, ArrayList<Integer> s3){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myText1, myText2, myText3;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.id);
            myText2 = itemView.findViewById(R.id.questionBank);
            myText3 = itemView.findViewById(R.id.noQuestions);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bankAdapter.MyViewHolder holder, int position) {
        holder.myText1.setText(String.valueOf(data3.get(position)));
        holder.myText2.setText(data1.get(position));
        holder.myText3.setText(String.valueOf(data2.get(position)));
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }


}
