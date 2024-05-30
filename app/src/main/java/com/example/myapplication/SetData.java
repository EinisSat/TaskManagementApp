package com.example.myapplication;

public class SetData {
    Task origTask;
    String title, status, priority, category, deadline;
    SetData(Task origTask, String title, String status, String priority, String category, String deadline)
    {
        this.origTask = origTask;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.deadline = deadline;
    }
    public String getTitle(){
        return title;
    }
    public String getStatus(){
        return status;
    }
    public String getPriority(){
        return priority;
    }
    public String getCategory(){
        return category;
    }
    public String getDeadline(){
        return deadline;
    }

    @Override
    public String toString() {
        return origTask.getIdString();
    }
}
