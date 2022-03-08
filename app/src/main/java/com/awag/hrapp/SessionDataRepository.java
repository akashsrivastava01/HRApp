package com.awag.hrapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SessionDataRepository {

    private SessionDataDao sessionDataDao;
    private LiveData<List<SessionData>> allSessionData;

    SessionDataRepository(Application application) {
        SessionRoomDatabase sessionRoomDatabase = SessionRoomDatabase.getDatabase(application);
        sessionDataDao = sessionRoomDatabase.sessionDataDao();
        allSessionData = sessionDataDao.getAllSession();
    }

    LiveData<List<SessionData>> getAllSessionData() {
        return allSessionData;
    }

    void insert(SessionData sessionData) {
        SessionRoomDatabase.databaseWriteExecutor.execute(() -> sessionDataDao.insert(sessionData));
    }

}
