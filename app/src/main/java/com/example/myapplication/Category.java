package com.example.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
//, foreignKeys = {@ForeignKey(entity = Category.class,
//        parentColumns = "id",
//        childColumns = "category",
//        onDelete = ForeignKey.CASCADE)}
@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @ColumnInfo(name = "name")
    private String name;

    public Category(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    public int getId() { return this.id; }
    public void setId(int id) {this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) {this.name = name; }

}