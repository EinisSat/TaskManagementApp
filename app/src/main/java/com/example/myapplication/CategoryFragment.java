package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.Update;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements AdapterView.OnItemClickListener {

    Button _button;
    ListView lvCategories;
    TextView filter;
    static AppDatabase db;
    Dialog dialog, dialog2;
    String deleteCategory;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "taskDatabase.db").allowMainThreadQueries().build();


        lvCategories = (ListView) view.findViewById(R.id.lvCategories);
        _button = (Button) view.findViewById(R.id.addCat);
        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        List<Category> category = db.categoryDao().getAll();
        List<String> catNames = new ArrayList<>();
        for (int i = 0; i < category.size();i++)
        {
            catNames.add(category.get(i).getName());
        }

        CustomMundaneAdapter listAdapter = new CustomMundaneAdapter(R.layout.list_categories, catNames);
        lvCategories.setAdapter(listAdapter);
        lvCategories.setOnItemClickListener(this);

        // DIALOG 1 STUFF
        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.category_create_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(view.getContext().getDrawable(R.drawable.custom_dialog_box));
        dialog.setCancelable(false);
//        Category categ = new Category(new Random().nextInt(), "Free time");
//        db.categoryDao().insert(categ);
        Button btnDialogConfirm = dialog.findViewById(R.id.btnDialogConfirm);
        Button btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);

        EditText categoryName = dialog.findViewById(R.id.categoryCreate);
        btnDialogCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        btnDialogConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Category categ = new Category(new Random().nextInt(), categoryName.getText().toString());
                db.categoryDao().insert(categ);
                InitializeList();
                dialog.dismiss();
            }
        });
        // DIALOG 1 OVER

        // DIALOG 2 STUFF
        dialog2 = new Dialog(view.getContext());
        dialog2.setContentView(R.layout.delete_category_dialog);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(view.getContext().getDrawable(R.drawable.custom_dialog_box));
        dialog2.setCancelable(false);
        Button btnDialogConfirm1 = dialog2.findViewById(R.id.btnDialogConfirm);
        Button btnDialogCancel1 = dialog2.findViewById(R.id.btnDialogCancel);

        btnDialogCancel1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                dialog2.dismiss();
            }
        });
        btnDialogConfirm1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Category deletionCat = db.categoryDao().loadAllByName(deleteCategory).get(0);
                List<Task> change = db.taskDao().loadAllByCategory(deletionCat.getId());
                for (Task var: change) {
                    db.taskDao().update(var.getId(), var.getName(), var.getDescription(), var.getPriority(),
                    var.getDate(), 0, var.getStatus(), 0);
                }
                db.categoryDao().deleteId(deletionCat.getId());
                InitializeList();
                dialog2.dismiss();
            }
        });
        // DIALOG 2 OVER
        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        deleteCategory = parent.getItemAtPosition(position).toString();
        //Log.d("shart", "This" + deleteCategory);
        //Intent intent = new Intent(getActivity().getBaseContext(), TaskView.class);
        //intent.putExtra("Data", task);
        dialog2.show();
    }
    public void InitializeList()
    {
        List<Category> category = db.categoryDao().getAll();
        List<String> catNames = new ArrayList<>();
        for (int i = 0; i < category.size();i++)
        {
            catNames.add(category.get(i).getName());
        }
        CustomMundaneAdapter listAdapter = new CustomMundaneAdapter(R.layout.list_categories, catNames);
//        CustomAdapter customAdapter = new CustomAdapter(banana);
        //lvTasks.setAdapter(customAdapter);
        lvCategories.setAdapter(listAdapter);
    }
}