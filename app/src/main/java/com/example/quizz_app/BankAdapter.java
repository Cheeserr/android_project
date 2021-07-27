package com.example.quizz_app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyViewHolder> {
    static int row_index = -1;

    ArrayList<QuestionBank> mQuestionBanks;
    Context context;

    private final OnNoteListener mOnNoteListener;


    public BankAdapter(Context ct, ArrayList<QuestionBank> questionBanks, OnNoteListener onNoteListener){
        context = ct;
        mQuestionBanks = questionBanks;
        this.mOnNoteListener = onNoteListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView idText, nameText, extraText;
        OnNoteListener onNoteListener;


        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            idText = itemView.findViewById(R.id.id);
            nameText = itemView.findViewById(R.id.questionBank);
            extraText = itemView.findViewById(R.id.noQuestions);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            onNoteListener.onNoteClick(getAdapterPosition());
            row_index = getAdapterPosition();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAdapter.MyViewHolder holder, int position) {
        holder.idText.setText(mQuestionBanks.get(position).mId);
        holder.nameText.setText(mQuestionBanks.get(position).mName);
        holder.extraText.setText(mQuestionBanks.get(position).mData);

        if(position % 2 == 1)
        {
            holder.itemView.findViewById(R.id.card).setBackgroundColor(Color.WHITE);
        }
        else
        {
            holder.itemView.findViewById(R.id.card).setBackgroundColor(Color.LTGRAY);
        }


        if(row_index == position){
            holder.itemView.findViewById(R.id.card).setBackgroundColor(Color.GREEN);
        }else{
            if(position % 2 == 1)
            {
                holder.itemView.findViewById(R.id.card).setBackgroundColor(Color.WHITE);
            }
            else
            {
                holder.itemView.findViewById(R.id.card).setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionBanks.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
