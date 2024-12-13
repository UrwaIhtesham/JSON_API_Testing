package com.example.l215404.assignment5.Models;

public class Post {
    //attributes
    private int id;
    private int userId;
    private String title;
    private String body;

    //constructor
    public Post(int id, int userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    //getters and setters of id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //getters and setters of user id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //getters and setters of title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //getters and setters of body
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
