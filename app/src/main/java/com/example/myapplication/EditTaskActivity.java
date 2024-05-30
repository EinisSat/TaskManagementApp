package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class EditTaskActivity extends AppCompatActivity {
    private Button _returnButton, _deadlineButton, _timeButton;
    private TextView text, timeText;
    EditText name, description;
    int priority, categoryId, id;
    LocalDateTime fullTime = LocalDateTime.now();
    static AppDatabase db;
    String[] priorities = {"High", "Medium", "Low"};
    String[] categoriesStr;
    int[] categoriesInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nameView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "taskDatabase.db").allowMainThreadQueries().build();
        String data = getIntent().getStringExtra("Data");
        Task task = db.taskDao().loadAllByIds(Integer.parseInt(data)).get(0);

        name = findViewById(R.id.editName);
        description = findViewById(R.id.editDescription);
        text = findViewById(R.id.deadlineView);
        timeText = findViewById(R.id.timeView);


        id = task.getId();
        name.setText(task.getName());
        description.setText(task.getDescription());
        priority = task.getPriority();
        fullTime = task.getDate();
        categoryId = task.getCategoryId();

        text.setText(String.valueOf(fullTime.getYear())
                +"."+String.valueOf(fullTime.getMonthValue()+1)
                +"."+String.valueOf(fullTime.getDayOfMonth()));
        timeText.setText(String.valueOf(fullTime.getHour())
                +"."+String.valueOf(fullTime.getMinute()));
//        Task task = new Task(new Random().nextInt(), name.getText().toString().trim(),
//                description.getText().toString().trim(), priority, fullTime, catSingle.getId(),1);


        List<Category> categoryData = db.categoryDao().getAll();
        categoriesStr = new String[categoryData.size()];
        categoriesInt = new int[categoryData.size()];
        int temp = 0;



        Spinner priorityDd = findViewById(R.id.priority);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.customspinnertext, priorities);
        priorityDd.setAdapter(adapter1);

        Spinner categoryDd = findViewById(R.id.category);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.customspinnertext, categoriesStr);
        categoryDd.setAdapter(adapter2);

        priorityDd.setSelection(priority-1);
        //categoryDd.setSelection();
        for (Category cat: categoryData)
        {
            categoriesStr[temp] = cat.getName();
            categoriesInt[temp] = cat.getId();
            if (categoriesInt[temp] == categoryId)
                categoryDd.setSelection(temp);
            temp++;
        }

        // Add task button
        _returnButton = (Button) findViewById(R.id.goBack);
        _returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spin1 = priorityDd.getSelectedItem().toString();
                switch(spin1) {
                    case "High":
                        priority = 1;
                        break;
                    case "Medium":
                        priority = 2;
                        break;
                    case "Low":
                        priority = 3;
                        break;
                }
                String spin2 = categoryDd.getSelectedItem().toString();
                //Log.d("myTag", "This " + categoryDd.getSelectedItem());
                Category catSingle = db.categoryDao().loadAllByName(spin2).get(0);
                db.taskDao().update(id, name.getText().toString().trim(),
                        description.getText().toString().trim(), priority, fullTime, catSingle.getId(),1, 0);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("Data", "Hello World");
                startActivity(intent);
            }
        });

        text = findViewById(R.id.deadlineView);
        _deadlineButton = findViewById(R.id.dateButton);
        _deadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });

        timeText = findViewById(R.id.timeView);
        _timeButton = findViewById(R.id.timeButton);
        _timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimeDialog();
            }
        });
    }
    private void openDateDialog()
    {
        LocalDateTime today = LocalDateTime.now();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                text.setText(String.valueOf(year)+"."+String.valueOf(month+1)+"."+String.valueOf(dayOfMonth));
                fullTime = LocalDateTime.of(year, month+1, dayOfMonth, fullTime.getHour(), fullTime.getMinute());
            }
        }, today.getYear(), today.getMonthValue()-1, today.getDayOfMonth());

        dialog.show();
    }
    private void openTimeDialog()
    {
        LocalDateTime today = LocalDateTime.now();
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeText.setText(String.valueOf(hourOfDay)+"."+String.valueOf(minute));
                fullTime = LocalDateTime.of(fullTime.getYear(), fullTime.getMonthValue(),
                        fullTime.getDayOfMonth(), hourOfDay, minute);
            }
        }, today.getHour(), today.getMinute(), true);

        dialog.show();
    }
}