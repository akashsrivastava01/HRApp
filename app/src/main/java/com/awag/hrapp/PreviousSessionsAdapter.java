package com.awag.hrapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PreviousSessionsAdapter extends RecyclerView.Adapter<PreviousSessionsAdapter.RecyclerViewHolder> {

    private List<SessionData> sessionDataList;

    public PreviousSessionsAdapter(List<SessionData> sessionDataList) {
        this.sessionDataList = sessionDataList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_session_item, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        SessionData sessionData = sessionDataList.get(position);
        holder.tvSessionHr.setText(sessionData.avgHr);
        holder.tvSessionDuration.setText(sessionData.duration);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sessionData.timeStamp.get(0));
        holder.tvSessionDate.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR));
        holder.tvSessionTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
    }

    @Override
    public int getItemCount() {
        return sessionDataList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvSessionDate;
        TextView tvSessionTime;
        TextView tvSessionHr;
        TextView tvSessionDuration;

        public RecyclerViewHolder(View view) {
            super(view);
            tvSessionDate = view.findViewById(R.id.tv_session_date);
            tvSessionTime = view.findViewById(R.id.tv_session_time);
            tvSessionHr = view.findViewById(R.id.tv_session_hr);
            tvSessionDuration = view.findViewById(R.id.tv_session_duration);
        }
    }
}
