package com.example.l215404.assignment5.VolleyHelpers;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestHelper {

    //varibales and queue for requests
    private static VolleyRequestHelper instance;
    private RequestQueue requestQueue;

    //constructor
    public VolleyRequestHelper(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    //getInstance function
    public static synchronized VolleyRequestHelper getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyRequestHelper(context);
        }
        //returning instance
        return instance;
    }

    //getter for requestQueue
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    //adding to request queue based on tag
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    //adding to request queue based on request
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    //cancel all the requests remove all the requests from queue
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }
}
