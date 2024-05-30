package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ImageDao {
    @Query("SELECT * FROM images")
    List<ImageBa> getAll();

    //@Query("SELECT * FROM tasks WHERE id IN (:userIds)")
    @Query("SELECT * FROM images WHERE id=:userIds")
    List<ImageBa> loadAllByIds(int userIds);

    @Insert
    void insert(ImageBa images);
    @Insert
    void insertAll(ImageBa...images);

    @Delete
    void delete(ImageBa images);
    @Query("DELETE FROM images WHERE id=:catId")
    void deleteId(int catId);
    @Query("DELETE FROM images")
    void deleteAll();
}
