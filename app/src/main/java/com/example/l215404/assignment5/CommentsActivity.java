package com.example.l215404.assignment5;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.l215404.assignment5.Models.Comment;
import com.example.l215404.assignment5.VolleyHelpers.VolleyRequestHelper;
import com.example.l215404.assignment5.VolleyHelpers.VolleyResponseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    private RecyclerView commentsRecyclerView;
    private CommentsAdapter adapter;
    private List<Comment> commentsList;
    private VolleyResponseHelper volleyResponseHelper;

    private TextView titlePost;
    private TextView bodyPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comments);

        VolleyRequestHelper volleyRequestHelper=VolleyRequestHelper.getInstance(this);
        volleyResponseHelper = new VolleyResponseHelper(volleyRequestHelper);

        int postId = getIntent().getIntExtra("postId", -1);

        titlePost =findViewById(R.id.postTitle);
        bodyPost = findViewById(R.id.postBody);

        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentsList = new ArrayList<>();
        adapter = new CommentsAdapter(commentsList);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(adapter);

        fetchPostById(postId);
        fetchComments(postId);
    }

    private void fetchPostById(int postId) {
        volleyResponseHelper.getPostById(postId, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String title = response.getString("title");
                    String body = response.getString("body");
                    titlePost.setText(title);
                    bodyPost.setText(body);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CommentsActivity.this, "Error parsing post details", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CommentsActivity.this, "Failed to fetch post details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchComments(int postId) {
        volleyResponseHelper.getComments(postId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject commentObj = response.getJSONObject(i);
                        int id = commentObj.getInt("id");
                        String name = commentObj.getString("name");
                        String email = commentObj.getString("email");
                        String body = commentObj.getString("body");

                        commentsList.add(new Comment(id, postId, name, email, body));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CommentsActivity.this, "Error parsing comments", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(CommentsActivity.this, "Failed to fetch comments", Toast.LENGTH_SHORT).show();
            }
        });
    }
}