package com.example.chris.nimbus;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.joda.time.DateTime;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public DateView dateView;
    View v;
    Handler handler = new Handler();
    Runnable runnable;
    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_main, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateView = v.findViewById(R.id.time_view);
        runnable = new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                String dateText = date.getMonth()+". "+ String.valueOf(date.getDayOfMonth())+ ", "+ String.valueOf(date.getYear());
                dateView.upDateTime(date.getDay(),date.getTime(),dateText);
                dateView.updateSweepAngles(date.getSecOfMin(),date.getMinOfHour(),date.getHourOfDay());
                dateView.invalidate();
                handler.postDelayed(runnable,1000);
            }
        };
        handler.post(runnable);
}


}
