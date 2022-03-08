package com.awag.hrapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SessionData.class}, version = 1, exportSchema = false)
@TypeConverters({SessionDataDao.Converters.class})
abstract class SessionRoomDatabase extends RoomDatabase {

    public abstract SessionDataDao sessionDataDao();

    private static volatile SessionRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static SessionRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SessionRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SessionRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}