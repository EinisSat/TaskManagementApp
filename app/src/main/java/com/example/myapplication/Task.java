package com.example.myapplication;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Comparator;
import java.time.LocalDateTime;

@Entity(tableName = "tasks"
        //, foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "id",
//        childColumns = "category",
//        onDelete = ForeignKey.CASCADE)}
      )
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "priority")
    private int priority;
    @ColumnInfo(name = "date")
    private LocalDateTime date;
    @ColumnInfo(name = "category")
    private int categoryId;
    @ColumnInfo(name = "status")
    private int status;
    @ColumnInfo(name = "imageId")
    private int imageId;

    public Task(int id, String name, String description,
                       int priority, LocalDateTime date, int categoryId, int status, int imageId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.categoryId = categoryId;
        this.status = status;
        if(LocalDateTime.now().isAfter(date) && this.status != 2)
            this.status = 3;
        this.imageId = imageId;
    }
    public String dateFormat()
    {
        String formattedDate = "" + date.getYear() + "-"
                + date.getMonthValue() + "-"
                + date.getDayOfMonth() + " "
                + date.getHour() + ":"
                + date.getMinute();

        return formattedDate;
    }
    @Override
    public String toString() {
        return name + "<!>" + getStatusString() + "<!>" + getPriorityString() + "<!>" + getCategoryString() + "<!>" + dateFormat();
    }
    public void setId(@NonNull int id) {this.id = id;}
    public int getId() { return this.id; }
    public String getIdString() { return "" + this.id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.name = description;
    }

    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int imageId) {
        this.status = imageId;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public static Comparator<Task> comparePriority = (Task a1, Task a2) -> {
        // didėjanti tvarka, pradedant nuo mažiausios
        if (a1.priority < a2.priority) {
            return -1;
        }
        if (a1.priority > a2.priority) {
            return +1;
        }
        return 0;
    };
    public String getPriorityString()
    {
        switch(getPriority())
        {
            case 1:
                return "High";
            case 2:
                return "Medium";
            case 3:
                return "Low";
            default:
                return "High";
        }
    }
    public String getStatusString()
    {
        switch(getStatus())
        {
            case 1:
                return "Ongoing";
            case 2:
                return "Complete";
            case 3:
                return "Overdue";
            default:
                return "Ongoing";
        }
    }
    public String getCategoryString()
    {

        return ""+getCategoryId();
        //switch(getCategoryId())
        //{
        //    case 1:
        //        return "Chores";
        //    case 2:
        //        return "Work";
        //    case 3:
        //        return "Free Time";
        //    default:
        //        return "Chores";
        //}
    }
}
