package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


//class CustomAdapter extends BaseAdapter {
//    List<String> items;
//
//    public CustomAdapter(List<String> items) {
//        super();
//        this.items = items;
//    }
//
//    @Override
//    public int getCount() {
//        return items.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return items.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return items.get(i).hashCode();
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        TextView textView = new TextView(viewGroup.getContext());
//        textView.setText(items.get(i));
//        return textView;
//    }
//}

public class CustomAdapter extends BaseAdapter implements Filterable {
    List<SetData> setData;
    List<SetData> setDataFiltered;
    int resource;
    Context context;
    Fragment fragment;
    CustomAdapter( int resource, List<SetData> setData)
    {
        //super(context, resource, setData);
        //this.context = context;
        this.resource = resource;
        this.setData = setData;
        this.setDataFiltered = setData;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource, null, false);
        TextView titleView = view.findViewById(R.id.textTitle);
        TextView statusView = view.findViewById(R.id.textStatus);
        TextView priorityView = view.findViewById(R.id.textPriority);
        TextView categoryView = view.findViewById(R.id.textCategory);
        TextView deadlineView = view.findViewById(R.id.textDeadline);
        final SetData setDataNew = setDataFiltered.get(position);
        titleView.setText(setDataNew.getTitle());
        statusView.setText(setDataNew.getStatus());
        priorityView.setText(setDataNew.getPriority());
        categoryView.setText(setDataNew.getCategory());
        deadlineView.setText(setDataNew.getDeadline());
        return view;
    }
    @Override
    public int getCount() {
        return setDataFiltered.size();
    }
    @Override
    public Object getItem(int position) {
        return setDataFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = setData.size();
                    filterResults.values = setData;
                }else{
                    List<SetData> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(SetData setdata:setData){
                        if(setdata.getTitle().contains(searchStr)){
                            resultsModel.add(setdata);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                setDataFiltered = (List<SetData>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
