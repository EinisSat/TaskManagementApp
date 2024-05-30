package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Query;
import androidx.room.Room;

import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        //implements AdapterView.OnItemClickListener
{
    ActivityMainBinding binding;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ReplaceFragment(new TaskFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//           switch (item.getItemId())
//           {
//               case R.id.tasks:
//                   ReplaceFragment(new TaskFragment());
//                   break;
//               case R.id.categories:
//                   ReplaceFragment(new CategoryFragment());
//                   break;
//               case R.id.notifications:
//                   ReplaceFragment(new NotificationFragment());
//                   break;
//           }
            if (item.getItemId() == R.id.tasks)
                ReplaceFragment(new TaskFragment());
            else if (item.getItemId() == R.id.categories)
                ReplaceFragment(new CategoryFragment());
            else if (item.getItemId() == R.id.notifications)
                ReplaceFragment(new NotificationFragment());

            return true;
        });
    }
    private void ReplaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}