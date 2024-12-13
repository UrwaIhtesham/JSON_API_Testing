package com.example.l215404.assignment5.VolleyHelpers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyResponseHelper {
    //varibale of request helper class
    private final VolleyRequestHelper volleyRequestHelper;

    //constructor
    public VolleyResponseHelper(VolleyRequestHelper volleyRequestHelper) {
        this.volleyRequestHelper = volleyRequestHelper;
    }

    //function to get posts jsopn object
    public void getPosts(Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        //url to fetch posts
        String url = "https://jsonplaceholder.typicode.com/posts";
        //GET method to fetch all the posts
        JsonArrayRequest JSONrequest = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }

    //delete postfunction
    public void deletePost(int postId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        //url to fetch the certain post
        String url = "https://jsonplaceholder.typicode.com/posts/" + postId;
        //DELETE method to dlete the post
        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.DELETE, url, null, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }

    //fetching comments for a certain post
    public void getComments(int postId, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        //url to ftech comments of a certain post
        String url = "https://jsonplaceholder.typicode.com/posts/" +postId + "/comments";
        //GET method
        JsonArrayRequest JSONrequest = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }

    //update post function
    public void updatePost(int postId, JSONObject updatedPost, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        //url to fetch the certain post
        String url = "https://jsonplaceholder.typicode.com/posts/" + postId;
        //PUT method to update the post
        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.PUT, url, updatedPost, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }

    //get post by certain id function
    public  void getPostById(int postId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        //url to fetch post by pot id
        String url = "https://jsonplaceholder.typicode.com/posts/" + postId;
        //GET method to fetch the post
        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }
}
