package com.example.myapplication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomMundaneAdapter extends BaseAdapter {

    List<String> items;
    int resource;

    public CustomMundaneAdapter(int resource, List<String> items) {
        super();
        this.resource = resource;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).hashCode();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, null, false);
        TextView titleView = view.findViewById(R.id.textTitle);
        titleView.setText(items.get(position));
        return view;
    }
}
