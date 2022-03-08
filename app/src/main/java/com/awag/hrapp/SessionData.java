package com.awag.hrapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class SessionData {
    @PrimaryKey
    public long sessionId;

    @ColumnInfo(name = "session_duration")
    public String duration;

    @ColumnInfo(name = "session_avg_hr")
    public String avgHr;

    @ColumnInfo(name = "session_timestamp")
    public List<Long> timeStamp;

    @ColumnInfo(name = "session_heart_rate")
    public List<Float> heartRate;
}
