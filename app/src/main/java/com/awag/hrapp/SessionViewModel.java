package com.awag.hrapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SessionViewModel extends AndroidViewModel {

    private SessionDataRepository sessionDataRepository;
    private final LiveData<List<SessionData>> allSessionData;

    public SessionViewModel(@NonNull Application application) {
        super(application);
        sessionDataRepository = new SessionDataRepository(application);
        allSessionData = sessionDataRepository.getAllSessionData();
    }

    public LiveData<List<SessionData>> getAllSessionData() {
        return allSessionData;
    }

    public void insert(SessionData sessionData) {
        sessionDataRepository.insert(sessionData);
    }
}
