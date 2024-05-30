package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.w3c.dom.Text;

import java.util.Calendar;

import J.N;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView textView;
    private ActivityMainBinding binding;
    private MaterialTimePicker timePicker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    Button setAlarm, cancelAlarm;
    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //getActivity().setContentView(binding.getRoot());

        createNotificationChannel(view);
        textView = (TextView) view.findViewById(R.id.textView4);
        setAlarm = (Button) view.findViewById(R.id.setAlarm);
        cancelAlarm = (Button) view.findViewById(R.id.cancelAlarm);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Select Alarm Time")
                        .build();

//                timePicker.show(getActivity().getSupportFragmentManager(), "yes");
                timePicker.show(getActivity().getSupportFragmentManager(), "yes");
                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                            textView.setText(timePicker.getHour()+":"+timePicker.getMinute());

                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                    }
                });
            }
        });
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
//                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                Intent intent = new Intent(view.getContext(), AlarmReceiver.class);
//                pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
                pendingIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                Toast.makeText(view.getContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                Intent intent = new Intent(view.getContext(), AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                if(alarmManager == null)
                {
                    alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
                }

                alarmManager.cancel(pendingIntent);
                Toast.makeText(view.getContext(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void createNotificationChannel(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "akchannel";
            String desc = "Channel for Alarm manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("yes", name, imp);
            channel.setDescription(desc);

            NotificationManager notificationManager = view.getContext().getSystemService(NotificationManager.class);
//            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}