package com.example.quizz_app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bankTable")
public class BankTable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;


    public BankTable(int id) {
        this.id = id;
    }

    public Integer getId(){
        return this.id;
    }
}

