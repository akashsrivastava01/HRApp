package com.awag.hrapp;

import android.os.Parcel;
import android.os.Parcelable;

public class HrData implements Parcelable {
    long timeStamp;
    double heartRate;

    public HrData(long timeStamp, double heartRate) {
        this.timeStamp = timeStamp;
        this.heartRate = heartRate;
    }

    protected HrData(Parcel in) {
        this.timeStamp = in.readLong();
        this.heartRate = in.readDouble();
    }

    public static final Creator<HrData> CREATOR = new Creator<HrData>() {
        @Override
        public HrData createFromParcel(Parcel in) {
            return new HrData(in);
        }

        @Override
        public HrData[] newArray(int size) {
            return new HrData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(timeStamp);
        parcel.writeDouble(heartRate);
    }
}
