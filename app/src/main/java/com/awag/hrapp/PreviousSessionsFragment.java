package com.awag.hrapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PreviousSessionsFragment extends Fragment {

    private ViewGroup root;
    private WearableRecyclerView recyclerPreviousSession;

    private List<SessionData> sessionDataList;

    private SessionViewModel sessionViewModel;

    public PreviousSessionsFragment() {
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
        root = (ViewGroup) inflater.inflate(R.layout.fragment_previous_sessions, container, false);

        // fetch previous session data from ControllerViewModel
        sessionViewModel = new ViewModelProvider(this).get(SessionViewModel.class);
        sessionViewModel.getAllSessionData().observe(getViewLifecycleOwner(), sessionData -> {
            sessionDataList = sessionData;
            initializeViewElements();
        });

        return root;
    }

    private void initializeViewElements() {
//        if (sessionViewModel.getAllSessionData().getValue() != null) {
        recyclerPreviousSession = root.findViewById(R.id.recycler_previous_sessions);
        recyclerPreviousSession.setEdgeItemsCenteringEnabled(true);
        CustomScrollingLayoutCallback customScrollingLayoutCallback =
                new CustomScrollingLayoutCallback();
        recyclerPreviousSession.setLayoutManager(new WearableLinearLayoutManager(getActivity(), customScrollingLayoutCallback));
        recyclerPreviousSession.setAdapter(new PreviousSessionsAdapter(sessionDataList));
    }
//    }
}