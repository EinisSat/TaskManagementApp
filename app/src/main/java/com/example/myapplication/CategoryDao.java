package com.example.myapplication;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    List<Category> getAll();

    //@Query("SELECT * FROM tasks WHERE id IN (:userIds)")
    @Query("SELECT * FROM categories WHERE id=:userIds")
    List<Category> loadAllByIds(int userIds);

    @Query("SELECT * FROM categories WHERE id=:userIds")
    List<Category> loadAllByDate(int userIds);
    @Query("SELECT * FROM categories WHERE name=:name")
    List<Category> loadAllByName(String name);

    @Insert
    void insert(Category category);
    @Insert
    void insertAll(Category...categories);

    @Delete
    void delete(Category categories);
    @Query("DELETE FROM categories WHERE id=:catId")
    void deleteId(int catId);
    @Query("DELETE FROM categories")
    void deleteAll();
}
