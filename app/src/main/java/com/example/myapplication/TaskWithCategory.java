//package com.example.myapplication;
//import androidx.room.Embedded;
//import androidx.room.Entity;
//import androidx.room.ForeignKey;
//import androidx.room.PrimaryKey;
//import androidx.room.Relation;
//import java.util.List;
//
////, foreignKeys = {@ForeignKey(entity = Category.class,
////        parentColumns = "id",
////        childColumns = "category",
////        onDelete = ForeignKey.CASCADE)}
//@Entity(tableName = "tasks_category")
//public class TaskWithCategory {
//    @PrimaryKey
//    int fuckYou;
//    @Embedded
//    public Category category;
//    @Relation(
//            parentColumn = "id",
//            entityColumn = "category"
//    )
//    public List<Task> tasks;
//}
