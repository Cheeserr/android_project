package com.example.quizz_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String[] data1;
    String[] data2;
    int[] data3;
    Context context;

    public MyAdapter(Context ct, String[] s1, String[] s2, int[] s3){
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
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.myText1.setText(data3[position]);
        holder.myText2.setText(data1[position]);
        holder.myText3.setText(data2[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }


}
