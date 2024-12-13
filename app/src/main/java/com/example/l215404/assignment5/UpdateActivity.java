package com.example.l215404.assignment5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.l215404.assignment5.VolleyHelpers.VolleyRequestHelper;
import com.example.l215404.assignment5.VolleyHelpers.VolleyResponseHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateActivity extends AppCompatActivity {

    //Variables
    private EditText editTitle;
    private EditText editBody;
    private Button updateButton;
    //Volley Response Helper Class object
    private VolleyResponseHelper volleyResponseHelper;

    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);

        editTitle = findViewById(R.id.TitlePostUpdate);
        editBody = findViewById(R.id.BodyPostUpdate);
        updateButton = findViewById(R.id.updatePostButton);

        //Volley Request Helper
        //This is used to get instance and send it to Response Helper to get Response
        VolleyRequestHelper volleyRequestHelper = VolleyRequestHelper.getInstance(this);
        volleyResponseHelper = new VolleyResponseHelper(volleyRequestHelper);

        //getting intent data coming from Main Activity
        postId = getIntent().getIntExtra("postId", -1);
        String current_Title = getIntent().getStringExtra("title");
        String current_Body = getIntent().getStringExtra("body");

        //Logging all the intent data one by one
        Log.d("UpdateActivity", "postId: " + postId);
        Log.d("UpdateActivity", "title: " + current_Title);
        Log.d("UpdateActivity", "body: " + current_Body);

        //setting the title and body if postId is not null
        if (postId != -1) {
            editTitle.setText(current_Title);
            editBody.setText(current_Body);
        }

        //when update Button is clicked, it should update the post
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePost();
            }
        });
    }

    //update post function, this is updating the post using response helper class
    private void updatePost() {
        //getting edittext data
        String updated_Title = editTitle.getText().toString();
        String update_Body = editBody.getText().toString();

        //title can't be empty
        if (updated_Title.isEmpty()) {
            Log.d("UpdateActivity", "Updated title: " + updated_Title);
            Toast.makeText(UpdateActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //body also can't be empty
        if (update_Body.isEmpty()) {
            Log.d("UpdateActivity", "Updated Body: " + update_Body);
            Toast.makeText(UpdateActivity.this, "Body cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            //initializing a json object
            JSONObject updated_Post = new JSONObject();
            updated_Post.put("title", updated_Title);
            updated_Post.put("body", update_Body);

            //using responsehelper to update the post
            volleyResponseHelper.updatePost(postId, updated_Post, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //using intent to navigate back to the mainactivity
                    Toast.makeText(UpdateActivity.this, "Post updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //displaying error
                    error.printStackTrace();
                    Toast.makeText(UpdateActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            //displayoing any exception
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Error creating update request", Toast.LENGTH_SHORT).show();
        }
    }
}