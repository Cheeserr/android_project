package com.example.quizz_app;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BankViewModel extends AndroidViewModel {

    private BankRepository mRepository;

    private final LiveData<List<BankTable>> mAllBanks;

    public BankViewModel(Application application){
        super(application);
        mRepository = new BankRepository(application);
        mAllBanks = mRepository.getAllBanks();
    }

    LiveData<List<BankTable>> getALlBanks(){
        return mAllBanks;
    }

    public void insert(BankTable bankTable) {
        mRepository.insert(bankTable);
    }
}
