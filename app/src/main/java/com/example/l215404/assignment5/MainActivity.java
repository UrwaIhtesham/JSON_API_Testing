package com.example.l215404.assignment5;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.l215404.assignment5.Models.Post;
import com.example.l215404.assignment5.VolleyHelpers.VolleyRequestHelper;
import com.example.l215404.assignment5.VolleyHelpers.VolleyResponseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostAdapter.onPostActionListener{

    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<Post> postList;
    private VolleyResponseHelper volleyResponseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        VolleyRequestHelper volleyRequestHelper = VolleyRequestHelper.getInstance(this);
        volleyResponseHelper = new VolleyResponseHelper(volleyRequestHelper);


        recyclerView = findViewById(R.id.postRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        adapter = new PostAdapter(postList, this);
        recyclerView.setAdapter(adapter);

        fetchPosts();
    }

    private void fetchPosts() {
        volleyResponseHelper.getPosts(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject postObj = response.getJSONObject(i);
                        String title = postObj.getString("title");
                        String body = postObj.getString("body");
                        int id = postObj.getInt("id");
                        int userId = postObj.getInt("userId");

                        postList.add(new Post(id, userId, title, body));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error parsing posts", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to fetch posts", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDelete(int postId) {
        volleyResponseHelper.deletePost(postId, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "Post deleted successfully", Toast.LENGTH_SHORT).show();

                for (Post post : postList) {
                    if (post.getId() == postId) {
                        postList.remove(post);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to delete post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpdate(Post post) {
        try {
            JSONObject updatedPost = new JSONObject();
            updatedPost.put("title", post.getTitle());
            updatedPost.put("body", post.getBody());

            volleyResponseHelper.updatePost(post.getId(), updatedPost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(MainActivity.this, "Post updated successfully", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error creating post update", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onShowComments(int postId) {
        volleyResponseHelper.getComments(postId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Comments", response.toString());
                Toast.makeText(MainActivity.this, "Comments fetched", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to fetch comments", Toast.LENGTH_SHORT).show();
            }
        });
    }
}