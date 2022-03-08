package com.awag.hrapp;

import static android.content.Context.SENSOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RunningViewFragment extends Fragment implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager;
    private Sensor heartRate;
    private TextView tvHeartRate;
    private TextView tvStopWatch;
    private ImageView ivStop;

    private boolean stopWatchRunning;

    ViewGroup root;
    private Boolean isListenerRegistered = false;

    private ArrayList<HrData> hrData;
    private List<Long> timeStampArray;
    private List<Float> heartRateArray;

    private int seconds = 0;

    public RunningViewFragment() {
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
        root = (ViewGroup) inflater.inflate(R.layout.fragment_running_view, container, false);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        heartRate = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        initializeViewElements();
        initializeHrMonitoring();
        stopWatchRunning = true;
        runTimer();
        hrData = new ArrayList<>();
        timeStampArray = new ArrayList<>();
        heartRateArray = new ArrayList<>();
        return root;
    }

    private void initializeHrMonitoring() {
        if (!isListenerRegistered) {
            isListenerRegistered = sensorManager.registerListener(this, heartRate, SensorManager.SENSOR_DELAY_FASTEST);
            if (isListenerRegistered) {
                Log.d("SENSOR", "sensor listener enabled");
            } else {
                Log.d("SENSOR", "sensor listener failed");
            }
        }
    }

    private void initializeViewElements() {
        tvHeartRate = root.findViewById(R.id.tv_heart_rate);

        tvStopWatch = root.findViewById(R.id.tv_stop_watch);

        ivStop = root.findViewById(R.id.iv_stop);
        ivStop.setOnClickListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("SENSOR", "EVENT VALUES : " + sensorEvent.values[0]);

//        timeStampArray.add(sensorEvent.timestamp);
//        timeStampArray.add(Calendar.getInstance().getTimeInMillis());
//        heartRateArray.add(sensorEvent.values[0]);

        if (sensorEvent.values[0] != 0.0) {
            hrData.add(new HrData(sensorEvent.timestamp, sensorEvent.values[0]));
            String hrReading = Integer.toString((int) sensorEvent.values[0]);
            tvHeartRate.setText(hrReading);

            timeStampArray.add(Calendar.getInstance().getTimeInMillis());
            heartRateArray.add(sensorEvent.values[0]);

        } else {
            tvHeartRate.setText("---");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_stop) {
            Log.d("SENSOR", "sensor disabled onStop()");
            sensorManager.unregisterListener(this);
            isListenerRegistered = false;

            stopWatchRunning = false;

            Log.d("SENSOR", "session values : " + hrData);

            setCompletedView();

            hrData.clear();
        }
    }

    private void setCompletedView() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putSerializable("TIME_STAMP_DATA", (Serializable) timeStampArray);
        bundle.putSerializable("HEART_RATE_DATA", (Serializable) heartRateArray);
        CompletedViewFragment completedViewFragment = new CompletedViewFragment();
        completedViewFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container_view, completedViewFragment)
                .setReorderingAllowed(true)
                .commit();
    }

    private void runTimer() {

        final Handler handler
                = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override

            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                // Format the seconds into hours, minutes,
                // and seconds.
                String time
                        = String
                        .format(Locale.getDefault(),
                                "%d:%02d:%02d", hours,
                                minutes, secs);

                // Set the text view text.
                tvStopWatch.setText(time);

                // If running is true, increment the
                // seconds variable.
                if (stopWatchRunning) {
                    seconds++;
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000);
            }
        });
    }
}