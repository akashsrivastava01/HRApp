package com.awag.hrapp;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.wear.ambient.AmbientModeSupport;

public class MainControllerActivity extends FragmentActivity implements AmbientModeSupport.AmbientCallbackProvider {

    private AmbientModeSupport.AmbientController ambientController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        ambientController = AmbientModeSupport.attach(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, StartViewFragment.class, null)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public AmbientModeSupport.AmbientCallback getAmbientCallback() {
        return new AmbientCallback();
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