package com.awag.hrapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StartViewFragment extends Fragment implements View.OnClickListener {

    ImageView imgStart;
    LinearLayout layoutPrevious;
    TextView tvDate;

    public StartViewFragment() {
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_start_view, container, false);
        initializeViewElements(root);
        setViewDate();
        return root;
    }

    private void setViewDate() {
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tvDate.setText(dateFormat.format(cal.getTime()));
    }

    private void initializeViewElements(ViewGroup root) {
        imgStart = root.findViewById(R.id.img_start);
        imgStart.setOnClickListener(this);
        layoutPrevious = root.findViewById(R.id.layout_previous);
        layoutPrevious.setOnClickListener(this);

        tvDate = root.findViewById(R.id.tv_date);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_start) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_view, RunningViewFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }
        if (view.getId() == R.id.layout_previous) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container_view, PreviousSessionsFragment.class, null)
                    .setReorderingAllowed(true)
                    .commit();
        }
    }
}