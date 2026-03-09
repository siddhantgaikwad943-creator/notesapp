package com.example.notesapp;

public class Note {

    private int id;
    private String title;
    private String content;
    private String timestamp;
    private String color;

    public Note(int id, String title, String content, String timestamp, String color) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.color = color;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getTimestamp() { return timestamp; }
    public String getColor() { return color; }
}