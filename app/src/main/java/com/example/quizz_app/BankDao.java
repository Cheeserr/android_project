package com.example.quizz_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BankDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BankTable bankTable);

    @Query("DELETE FROM BankTable")
    void deleteAll();

    @Query("SELECT * FROM BankTable ORDER BY id ASC")
    LiveData<List<BankTable>> getAlphabetizedWords();
}
