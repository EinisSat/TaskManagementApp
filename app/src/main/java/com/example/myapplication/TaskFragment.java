package com.example.myapplication;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.room.Room;

import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements AdapterView.OnItemClickListener {
    Button _button;
    ListView lvTasks;
    TextView filter;
    static AppDatabase db;
    ImageView iv1, iv2;
    List<Task> tasks;
    List<Category> category;
    Dialog dialog, dialog2;
    String[] sortingSpin = {"Name", "Deadline", "Priority"};
    String[] categoriesStr;
    int[] categoriesInt;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment();
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);


        // Database initialisation
        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class,
                "taskDatabase.db").allowMainThreadQueries().build();
//        @Query("SELECT * FROM task JOIN category ON user.id = book.user_id")
//        public Map<Category, List<Task>> loadUserAndBookNames();

//        Category categ = new Category(new Random().nextInt(), "Free time");
//        db.categoryDao().insert(categ);
//        categ = new Category(new Random().nextInt(), "Work");
//        db.categoryDao().insert(categ);
//        categ = new Category(new Random().nextInt(), "Chores");
//        db.categoryDao().insert(categ);
//        categ = new Category(new Random().nextInt(), "Test Category");
//        db.categoryDao().insert(categ);

//        Task task = new Task(new Random().nextInt(), "Shit", "Ass", 1,
//                LocalDateTime.now(), -42972721,1);
//        db.taskDao().insert(task);
        // Add task button
        _button = (Button) view.findViewById(R.id.add);
        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(intent);
            }
        });

        // DIALOG1 STUFF
        dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.sort_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(view.getContext().getDrawable(R.drawable.custom_dialog_box));
        dialog.setCancelable(false);

        Button btnDialogConfirm = dialog.findViewById(R.id.btnDialogConfirm);
        Button btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);

        Spinner sortSpinner = dialog.findViewById(R.id.sortSpinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(view.getContext(), R.layout.customspinnertext, sortingSpin);
        sortSpinner.setAdapter(adapter1);

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
                String spin1 = sortSpinner.getSelectedItem().toString();
                switch(spin1) {
                    case "Name":
                        db.taskDao().sortByName();
                        InitializeList(db.taskDao().sortByName());
                        lvTasks.invalidateViews();
                        break;
                    case "Deadline":
                        db.taskDao().sortByDeadline();
                        InitializeList(db.taskDao().sortByDeadline());
                        lvTasks.invalidateViews();
                        break;
                    case "Priority":
                        db.taskDao().sortByPriority();
                        InitializeList(db.taskDao().sortByPriority());
                        lvTasks.invalidateViews();
                        break;
                }
                dialog.dismiss();
            }
        });
        // DIALOG 2 OVER

        // DIALOG 2 STUFF
        dialog2 = new Dialog(view.getContext());
        dialog2.setContentView(R.layout.filter_catagory_dialog);
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(view.getContext().getDrawable(R.drawable.custom_dialog_box));
        dialog2.setCancelable(false);

        Button btnDialogConfirm1 = dialog2.findViewById(R.id.btnDialogConfirm);
        Button btnDialogCancel1 = dialog2.findViewById(R.id.btnDialogCancel);

        List<Category> categoryData = db.categoryDao().getAll();
        categoriesStr = new String[categoryData.size()];
        categoriesInt = new int[categoryData.size()];
        int temp = 0;
        for (Category cat: categoryData)
        {
            categoriesStr[temp] = cat.getName();
            categoriesInt[temp] = cat.getId();
            temp++;
        }
        Spinner categoryDd = dialog2.findViewById(R.id.filterSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(view.getContext(), R.layout.customspinnertext, categoriesStr);
        categoryDd.setAdapter(adapter2);
//
//        //categoryDd.setSelection();
//
//
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
                String spin1 = categoryDd.getSelectedItem().toString();
                Category catSingle = db.categoryDao().loadAllByName(spin1).get(0);
                InitializeList(db.taskDao().loadAllByCategory(catSingle.getId()));
                lvTasks.invalidateViews();
                dialog2.dismiss();
            }
        });
        // DIALOG 2 OVER

        tasks = db.taskDao().getAll();
        Category singleCategory;
        List<SetData> setData;
        setData = new ArrayList<>();
        for (Task var : tasks) {
            String string = var.toString();
            String[] splits = string.split("<!>", 5);
            String categoryString;
            if(splits[3].equals("0"))
                categoryString = "Unassigned";
            else
            {
                category = db.categoryDao().loadAllByIds(var.getCategoryId());
                singleCategory = category.get(0);
                categoryString = singleCategory.getName();
            }
            setData.add(new SetData(var, splits[0], splits[1], splits[2], categoryString, splits[4]));
        }

        lvTasks = view.findViewById(R.id.lvTasks);
        CustomAdapter customAdapter = new CustomAdapter(R.layout.list_items, setData);
        lvTasks.setAdapter(customAdapter);


        // NOTIFICATION
        LocalDateTime now = LocalDateTime.now();
        now = now.plusHours(1);
        List<Task> dueTask = db.taskDao().getAll();



        //if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
        //}
        //NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);
        //NotificationManager manager = getSystemService(NotificationManager.class);
        //manager.createNotificationChannel(channel);

        //NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,
        //        "My notification");
        //builder.setContentTitle("Your task is due soon!");
        //builder.setContentText("The task will be due in 1 hour.");
        //builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        //.setAutoCancel(true);

        //NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);

        //managerCompat.notify(1, builder.build());
        //managerCompat.notify(1, builder.build());
//        for (Task var:
//                dueTask) {
//            if(var.getDate().isAfter(LocalDateTime.now()) && var.getDate().isBefore(now))
//            {
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,
//                        "My notification");
//                builder.setContentTitle("Your task is due soon!");
//                int dueMin1 = var.getDate().getMinute();
//                int dueMin2 = now.getMinute();
//                int result = -1 * (dueMin2 - dueMin1);
//
//                builder.setContentText("The task '" + var.getName() + "' will be due in " + result + " minutes.");
//                builder.setSmallIcon(R.drawable.ic_launcher_foreground);
//                builder.setAutoCancel(true);
//
//                managerCompat.notify(1, builder.build());
//                Log.d("Poon", var.getName());
//            }
//        }

        // SearchView filter
        SearchView searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                customAdapter.getFilter().filter("");
                return false;
            }
        });
        lvTasks.setOnItemClickListener(this);
        iv1 = (ImageView) view.findViewById(R.id.sortIcon);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.taskDao().sortByName();
                //InitializeList(db.taskDao().sortByName());
                //lvTasks.invalidateViews();
                dialog.show();
            }
        });

        iv2 = (ImageView) view.findViewById(R.id.filterIcon);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.taskDao().sortByName();
                dialog2.show();;
            }
        });



        return view;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String task = parent.getItemAtPosition(position).toString();
        Intent intent = new Intent(getActivity().getBaseContext(), TaskView.class);
        intent.putExtra("Data", task);
        startActivity(intent);
    }
    public void InitializeList(List<Task> taskInit)
    {
        Category singleCategory;
        List<SetData> setNewData;
        setNewData = new ArrayList<>();
        for (Task var : taskInit) {
            String string = var.toString();
            String[] splits = string.split("<!>", 5);
            category = db.categoryDao().loadAllByIds(var.getCategoryId());
            singleCategory = category.get(0);
            setNewData.add(new SetData(var, splits[0], splits[1], splits[2], singleCategory.getName(), splits[4]));
        }

        CustomAdapter customAdapter = new CustomAdapter(R.layout.list_items, setNewData);
//        CustomAdapter customAdapter = new CustomAdapter(banana);
        //lvTasks.setAdapter(customAdapter);
        lvTasks.setAdapter(customAdapter);
    }
}