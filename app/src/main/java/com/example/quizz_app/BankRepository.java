package com.example.quizz_app;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BankRepository {
    private BankDao mBankDao;
    private LiveData<List<BankTable>> mAllBanks;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    BankRepository(Application application) {
        BankDatabase db = BankDatabase.getDatabase(application);
        mBankDao = db.bankDao();
        mAllBanks = mBankDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<BankTable>> getAllBanks() {
        return mAllBanks;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(BankTable bankTable) {
        BankDatabase.databaseWriteExecutor.execute(() -> {
            mBankDao.insert(bankTable);
        });
    }
}
