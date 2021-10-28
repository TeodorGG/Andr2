package com.example.lab2_;

import java.io.Serializable;


public class Model implements Serializable {
    private String date;
    private String time;
    private String title_task;
    private String description;

    public Model(String date, String time, String title_task, String description) {
        this.date = date;
        this.time = time;
        this.title_task = title_task;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle_task() {
        return title_task;
    }

    public void setTitle_task(String title_task) {
        this.title_task = title_task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Model{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", title_task='" + title_task + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
