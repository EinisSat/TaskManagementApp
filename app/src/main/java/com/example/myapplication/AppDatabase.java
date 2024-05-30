package com.example.myapplication;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Ignore;
import androidx.room.Room;
import androidx.room.TypeConverters;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class, Category.class, ImageBa.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract CategoryDao categoryDao();
    public abstract ImageDao imageDao();
    private static final String DB_NAME = "taskDatabase.db";
    private static AppDatabase single_instance;

    private static synchronized AppDatabase getInstance(Context context)
    {
        if (single_instance == null)
           single_instance = create(context);

        return single_instance;
    }
    @Ignore
    private static AppDatabase create(final Context context)
    {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME).build();
    }

}
