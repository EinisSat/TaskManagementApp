package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapterBackup extends ArrayAdapter<SetData> {
    List<SetData> setData;
    int resource;
    Context context;
    CustomAdapterBackup(Context context, int resource, List<SetData> setData)
    {
        super(context, resource, setData);
        this.context = context;
        this.resource = resource;
        this.setData = setData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, null, false);
        TextView titleView = view.findViewById(R.id.textTitle);
        TextView statusView = view.findViewById(R.id.textStatus);
        TextView priorityView = view.findViewById(R.id.textPriority);
        TextView categoryView = view.findViewById(R.id.textCategory);
        TextView deadlineView = view.findViewById(R.id.textDeadline);
        final SetData setDataNew = setData.get(position);
        titleView.setText(setDataNew.getTitle());
        statusView.setText(setDataNew.getStatus());
        priorityView.setText(setDataNew.getPriority());
        categoryView.setText(setDataNew.getCategory());
        deadlineView.setText(setDataNew.getDeadline());
        return view;
    }
}
