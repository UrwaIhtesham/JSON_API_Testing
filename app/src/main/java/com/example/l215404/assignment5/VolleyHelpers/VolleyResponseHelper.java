package com.example.l215404.assignment5.VolleyHelpers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyResponseHelper {
    private final VolleyRequestHelper volleyRequestHelper;

    public VolleyResponseHelper(VolleyRequestHelper volleyRequestHelper) {
        this.volleyRequestHelper = volleyRequestHelper;
    }

    public void getPosts(Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonArrayRequest JSONrequest = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }

    public void deletePost(int postId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + postId;
        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.DELETE, url, null, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }

    public void getComments(int postId, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        String url = "https://jsonplaceholder.typicode.com/posts/" +postId + "/comments";
        JsonArrayRequest JSONrequest = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }

    public void updatePost(int postId, JSONObject updatedPost, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + postId;
        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.PUT, url, updatedPost, listener, errorListener);
        volleyRequestHelper.addToRequestQueue(JSONrequest);
    }
}
