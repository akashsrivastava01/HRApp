package com.awag.hrapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.wear.ambient.AmbientModeSupport;

import com.awag.hrapp.databinding.ActivityMainBinding;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity implements SensorEventListener, AmbientModeSupport.AmbientCallbackProvider, View.OnClickListener {

    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    private Sensor heartRate;

    private Boolean isListenerRegistered = false;

    private AmbientModeSupport.AmbientController ambientController;

    private TextView tvStartMonitoring;
    private TextView tvHeartRate;
    private Button btnStart;
    private Button btnStop;

    private List<Double> hrData;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> availableSensor = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d("SENSOR", availableSensor.toString());

        ambientController = AmbientModeSupport.attach(this);

        heartRate = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        initializeViewElements();
        hrData = new ArrayList<>();
        calendar = Calendar.getInstance();
    }

    private void initializeViewElements() {
        tvStartMonitoring = findViewById(R.id.tv_start_monitoring);
        tvHeartRate = findViewById(R.id.tv_heart_rate);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SENSOR", "onStop Invoked");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d("SENSOR", "EVENT VALUES : " + sensorEvent.values[0]);
        if (sensorEvent.values[0] != 0.0) {
            hrData.add((double) sensorEvent.values[0]);
            tvHeartRate.setText(Integer.toString((int) sensorEvent.values[0]));
        } else {
            tvHeartRate.setText("-");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public AmbientModeSupport.AmbientCallback getAmbientCallback() {
        return new AmbientCallback();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_start) {
            startView();
            if (!isListenerRegistered) {
                isListenerRegistered = sensorManager.registerListener(this, heartRate, SensorManager.SENSOR_DELAY_FASTEST);
                if (isListenerRegistered) {
                    Log.d("SENSOR", "sensor listener enabled");
                } else {
                    Log.d("SENSOR", "sensor listener failed");
                }
            }
        } else if (view.getId() == R.id.btn_stop) {
            stopView();
            Log.d("SENSOR", "sensor disabled onStop()");
            sensorManager.unregisterListener(this);
            isListenerRegistered = false;
        }
    }

    private void stopView() {
        tvStartMonitoring.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.GONE);
        tvHeartRate.setVisibility(View.GONE);

        double avg = hrData.stream().mapToDouble(val -> val).average().orElse(0.0);
        Log.d("SENSOR", "session values : " + hrData);
        Log.d("SENSOR", "session average : " + avg);
        hrData.clear();
    }

    private void startView() {
        tvStartMonitoring.setVisibility(View.GONE);
        btnStart.setVisibility(View.GONE);
        btnStop.setVisibility(View.VISIBLE);
        tvHeartRate.setVisibility(View.VISIBLE);
    }

    private class AmbientCallback extends AmbientModeSupport.AmbientCallback {
        @Override
        public void onEnterAmbient(Bundle ambientDetails) {
            // Handle entering ambient mode
            Log.d("SENSOR", "onEnterAmbient mode");
        }

        @Override
        public void onExitAmbient() {
            // Handle exiting ambient mode
            Log.d("SENSOR", "onExitAmbient mode");
        }

        @Override
        public void onUpdateAmbient() {
            // Update the content
            Log.d("SENSOR", "onUpdateAmbient mode");
        }
    }
}