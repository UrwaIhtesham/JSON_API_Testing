package com.example.l215404.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

    //Variables
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    private List<Post> postList;
    //Response helper class variable
    private VolleyResponseHelper volleyResponseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //volley requiest helper clas svariable
        VolleyRequestHelper volleyRequestHelper = VolleyRequestHelper.getInstance(this);
        volleyResponseHelper = new VolleyResponseHelper(volleyRequestHelper);

        //setting recycler view
        recyclerView = findViewById(R.id.postRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //setting adapter
        postList = new ArrayList<>();
        adapter = new PostAdapter(postList, this);
        recyclerView.setAdapter(adapter);

        //fetching all the posts from the url
        fetchPosts();
    }

    //fetch posts function
    private void fetchPosts() {
        volleyResponseHelper.getPosts(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //putting yhe posts one by one in the list
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject postObj = response.getJSONObject(i);
                        String title = postObj.getString("title");
                        String body = postObj.getString("body");
                        int id = postObj.getInt("id");
                        int userId = postObj.getInt("userId");

                        Log.d("MainActivity", "JSONOBJ: " + id + " " + userId + " " + title + " " + body);
                        postList.add(new Post(id, userId, title, body));
                    }
                    //updating adapter
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    //displaying exception
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error parsing posts", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //displaying error
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to fetch posts", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //when delete button is clicked
    @Override
    public void onDelete(int postId) {
        volleyResponseHelper.deletePost(postId, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //making a toast
                Toast.makeText(MainActivity.this, "Post deleted successfully", Toast.LENGTH_SHORT).show();

                //if the post is deleted, its removed from the list
                for (Post post : postList) {
                    if (post.getId() == postId) {
                        //remove3 a certain post from list
                        postList.remove(post);
                        break;
                    }
                }
                //update adaptere
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //displaying error
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to delete post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //when updatesd button is clicked
    @Override
    public void onUpdate(Post post) {
        //logging the id, title and body
        Log.d("MainActivity", "postId (MAIN): " + post.getId());
        Log.d("MainActivity", "post Title (MAIN): " + post.getTitle());
        Log.d("MainActivity", "post Body (MAIN): " + post.getBody());
        //navigating the post to up[date activity
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        //putting intent data
        intent.putExtra("postId", post.getId());
        intent.putExtra("title", post.getTitle());
        intent.putExtra("body", post.getBody());
        startActivity(intent);
    }

    //when comments button is clicked
    @Override
    public void onShowComments(int postId) {
        //navigating to comments activity
        Intent i = new Intent(MainActivity.this, CommentsActivity.class);
        //putting intent data
        i.putExtra("postId", postId);
        startActivity(i);
    }

}