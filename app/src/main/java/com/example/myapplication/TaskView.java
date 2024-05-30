package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class TaskView extends AppCompatActivity {

    Button _button;
    Button _editButton;
    Button _deleteButton;
    Button _addImage;
    ImageView imageDisplay;
    Dialog dialog;
    Task taskout;
    int SELECT_PICTURE = 200;
    static AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nameView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "taskDatabase.db").allowMainThreadQueries().build();

        //TextView textView = findViewById(R.id.titleDisplay);
        String data = getIntent().getStringExtra("Data");
        int id = Integer.parseInt(data);
        //textView.setText(""+(id));
        List<Task> task = db.taskDao().loadAllByIds(id);
        Task singleTask = task.get(0);
        String categoryString;
        if (singleTask.getCategoryId() == 0)
        {
            categoryString = "Unassigned";
        }
        else
        {
            List<Category> category = db.categoryDao().loadAllByIds(singleTask.getCategoryId());
            Category singleCategory = category.get(0);
            categoryString = singleCategory.getName();
        }


        // DIALOG PORTION
        dialog = new Dialog(TaskView.this);
        dialog.setContentView(R.layout.delete_dialog_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_box));
        dialog.setCancelable(false);

        Button btnDialogConfirm = dialog.findViewById(R.id.btnDialogConfirm);
        Button btnDialogCancel = dialog.findViewById(R.id.btnDialogCancel);

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
                db.taskDao().delete(id);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("Data", "Hello World");
                startActivity(intent);
            }
        });
        // DIALOG OVER


        TextView titleView = findViewById(R.id.titleDisplay);
        TextView descriptionView = findViewById(R.id.descriptionDisplay);
        TextView statusView = findViewById(R.id.statusDisplay);
        TextView priorityView = findViewById(R.id.priorityDisplay);
        TextView categoryView = findViewById(R.id.categoryDisplay);
        TextView deadlineView = findViewById(R.id.deadlineDisplay);
        imageDisplay = findViewById(R.id.taskImage);
        if(singleTask.getImageId() != 0)
        {
            ImageBa imageBack = db.imageDao().loadAllByIds(singleTask.getImageId()).get(0);
            Bitmap bm = BitmapFactory.decodeByteArray(imageBack.getImage(), 0, imageBack.getImage().length);
            imageDisplay.setImageBitmap(bm);
        }



        titleView.setText(singleTask.getName());
        descriptionView.setText(singleTask.getDescription());
        statusView.setText(singleTask.getStatusString());
        priorityView.setText(singleTask.getPriorityString());
        categoryView.setText(categoryString);
        deadlineView.setText(singleTask.dateFormat());

        _button = (Button) findViewById(R.id.goBack);
        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("Data", "Hello World");
                int imageid = 0;
                if (singleTask.getImageId() != 0)
                    imageid = singleTask.getImageId();
                db.taskDao().update(singleTask.getId(), singleTask.getName(), singleTask.getDescription(),
                        singleTask.getPriority(), singleTask.getDate(), singleTask.getCategoryId(),2, imageid);
                startActivity(intent);
            }
        });
        _editButton = (Button) findViewById(R.id.editTask);
        _editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditTaskActivity.class);
                intent.putExtra("Data", data);
                startActivity(intent);
            }
        });

        _deleteButton = (Button) findViewById(R.id.deleteTask);
        _deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        imageDisplay = (ImageView) findViewById(R.id.taskImage);

        _addImage = (Button) findViewById(R.id.addImage);
        _addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        taskout = singleTask;

    }
    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        launchSomeActivity.launch(i);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            // compare the resultCode with the
//            // SELECT_PICTURE constant
//            if (requestCode == SELECT_PICTURE) {
//                // Get the url of the image from data
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    // update the preview image in the layout
//                    imageDisplay.setImageURI(selectedImageUri);
//                }
//            }
//        }
//    }
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        Bitmap bmp = selectedImageBitmap;
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imageBa = stream.toByteArray();
//                        bmp.recycle();
                        ImageBa image = new ImageBa(new Random().nextInt(), imageBa);
                        db.imageDao().insert(image);
                        db.taskDao().update(taskout.getId(), taskout.getName(), taskout.getDescription(),
                                taskout.getPriority(), taskout.getDate(), taskout.getCategoryId(),2, image.getId());
                        ImageBa imageBack = db.imageDao().loadAllByIds(image.getId()).get(0);
                        Bitmap bm = BitmapFactory.decodeByteArray(imageBack.getImage(), 0, imageBack.getImage().length);
                        imageDisplay.setImageBitmap(bm);
                    }
                }
            });
}