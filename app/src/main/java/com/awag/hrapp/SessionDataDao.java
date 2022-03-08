package com.awag.hrapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface SessionDataDao {
    @Query("SELECT * FROM sessiondata")
    LiveData<List<SessionData>> getAllSession();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SessionData sessionData);

    @Delete
    void delete(SessionData sessionData);

    class Converters {
        @TypeConverter
        public static List<Long> longListFromString(String value) {
            Type listType = new TypeToken<List<Long>>() {
            }.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String stringFromLongList(List<Long> list) {
            Gson gson = new Gson();
            String json = gson.toJson(list);
            return json;
        }

        @TypeConverter
        public static List<Float> floatListFromString(String value) {
            Type listType = new TypeToken<List<Float>>() {
            }.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String stringFromFloatList(List<Float> list) {
            Gson gson = new Gson();
            String json = gson.toJson(list);
            return json;
        }
    }

}
