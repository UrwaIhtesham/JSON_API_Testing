package com.example.l215404.assignment5.Models;

public class Comment {
    //attributes
    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;

    //constructor
    public Comment(int id, int postId, String name, String email, String body) {
        this.id = id;
        this.postId = postId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    //getters and setters of id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //getters and setters of post id
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    //getters and setters of name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //getters and setters of email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //getters and setters of body
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
