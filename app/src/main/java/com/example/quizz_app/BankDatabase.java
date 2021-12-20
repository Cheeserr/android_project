package com.example.quizz_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BankTable.class}, version = 1, exportSchema = false)
public abstract class BankDatabase extends RoomDatabase {
    public abstract BankDao bankDao();

    private static volatile BankDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BankDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BankDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BankDatabase.class, "bank_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
