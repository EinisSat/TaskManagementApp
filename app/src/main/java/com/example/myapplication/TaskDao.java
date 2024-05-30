package com.example.myapplication;
import java.time.LocalDateTime;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    //@Query("SELECT * FROM tasks WHERE id IN (:userIds)")
    @Query("SELECT * FROM tasks WHERE id=:userIds")
    List<Task> loadAllByIds(int userIds);

    @Query("SELECT * FROM tasks WHERE id=:userIds")
    List<Task> loadAllByDate(int userIds);
    @Query("SELECT * FROM tasks WHERE category=:catId")
    List<Task> loadAllByCategory(int catId);

    @Query("UPDATE tasks SET name=:name, description=:description, priority=:priority, date=:date," +
            "category=:categoryId, status=:status, imageId=:imageId WHERE id=:taskId")
    void update(int taskId, String name, String description,
                int priority, LocalDateTime date, int categoryId, int status, int imageId);
    @Insert
    void insert(Task task);
    @Insert
    void insertAll(Task...tasks);
    @Query("SELECT * FROM tasks ORDER BY name")
    List<Task> sortByName();
    @Query("SELECT * FROM tasks ORDER BY date")
    List<Task> sortByDeadline();
    @Query("SELECT * FROM tasks ORDER BY priority")
    List<Task> sortByPriority();
    @Query("DELETE FROM tasks WHERE id=:taskId")
    void delete(int taskId);
    @Query("DELETE FROM tasks")
    void deleteAll();
}
