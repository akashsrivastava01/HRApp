package com.awag.hrapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CompletedViewFragment extends Fragment implements View.OnClickListener {

    private ViewGroup root;
    //    private ArrayList<HrData> sessionData;
    private ArrayList<Long> timeStampData;
    private ArrayList<Float> heartRateData;

    private TextView tvDate;
    private TextView tvHeartRate;
    private Button btnHomePage;
    private TextView tvDuration;
//    LineChart chartHeartRate;

    private SessionViewModel sessionViewModel;
    private SessionData sessionData;

    public CompletedViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = (ViewGroup) inflater.inflate(R.layout.fragment_completed_view, container, false);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//        String millisInString  = dateFormat.format(new Date());

        timeStampData = (ArrayList<Long>) getArguments().getSerializable("TIME_STAMP_DATA");
        heartRateData = (ArrayList<Float>) getArguments().getSerializable("HEART_RATE_DATA");

        // Logging all the sensor data
        if (getArguments() != null) {
//            sessionData = getArguments().getParcelableArrayList("SESSION_DATA");
            for (int i = 0; i < timeStampData.size(); i++) {
                Log.d("SESSION_DATA", i + "");
                Log.d("SESSION_DATA", dateFormat.format(new Date(timeStampData.get(i))));
                Log.d("SESSION_DATA", String.valueOf(heartRateData.get(i)));
                Log.d("SESSION_DATA", "---");
            }
        }

        initializeView();

        sessionViewModel = new ViewModelProvider(this).get(SessionViewModel.class);
        sessionViewModel.getAllSessionData().observe(getViewLifecycleOwner(), sessionData1 -> Log.d("LIVE_DATA", sessionData1.toString()));

        if (timeStampData.size() != 0) {
            setSessionSummary();
        }

        return root;
    }

    private void setSessionSummary() {
        // Average HR to be shown on the UI
        String avgHr = Integer.toString((int) Math.round(heartRateData.stream()
                .mapToDouble(d -> d)
                .average()
                .orElse(0.0)));

        // Session Duration to be shown on the UI
        Long durationMili = timeStampData.get(timeStampData.size() - 1) - timeStampData.get(0);

        String duration;

        if (durationMili <= 60000) {
            duration = TimeUnit.MILLISECONDS.toSeconds(timeStampData.get(timeStampData.size() - 1) - timeStampData.get(0)) + " SEC";
        } else {
            duration = TimeUnit.MILLISECONDS.toMinutes(timeStampData.get(timeStampData.size() - 1) - timeStampData.get(0)) + " MIN";
        }

        if (duration != null) {
            tvDuration.setText(duration);
        }
        tvHeartRate.setText(avgHr);

        // Setting up the SessionData object to store in room database

        sessionData = new SessionData();

        sessionData.sessionId = timeStampData.get(0);
        sessionData.avgHr = avgHr;
        sessionData.duration = duration;
        sessionData.heartRate = heartRateData;
        sessionData.timeStamp = timeStampData;


        // Insert the SessionData object in the room database

        sessionViewModel.insert(sessionData);

    }

    private void initializeView() {
        tvDate = root.findViewById(R.id.tv_date);
        tvHeartRate = root.findViewById(R.id.tv_heart_rate);
        btnHomePage = root.findViewById(R.id.btn_homepage);
        tvDuration = root.findViewById(R.id.tv_duration);
        btnHomePage.setOnClickListener(this);
//        chartHeartRate = root.findViewById(R.id.chart_heart_rate);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_homepage) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

            StartViewFragment startViewFragment = new StartViewFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_view, startViewFragment)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }
}