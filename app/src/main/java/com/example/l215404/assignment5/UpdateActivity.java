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

    private EditText editTitle;
    private EditText editBody;
    private Button updateButton;
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

        VolleyRequestHelper volleyRequestHelper = VolleyRequestHelper.getInstance(this);
        volleyResponseHelper = new VolleyResponseHelper(volleyRequestHelper);

        postId = getIntent().getIntExtra("postId", -1);
        String currentTitle = getIntent().getStringExtra("title");
        String currentBody = getIntent().getStringExtra("body");

        Log.d("UpdateActivity", "postId: " + postId);
        Log.d("UpdateActivity", "title: " + currentTitle);
        Log.d("UpdateActivity", "body: " + currentBody);

        if (postId != -1) {
            editTitle.setText(currentTitle);
            editBody.setText(currentBody);
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePost();
            }
        });
    }
    private void updatePost() {
        String updatedTitle = editTitle.getText().toString();
        String updateBody = editBody.getText().toString();

        if (updatedTitle.isEmpty() || updateBody.isEmpty()) {
            Toast.makeText(UpdateActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject updatedPost = new JSONObject();
            updatedPost.put("title", updatedTitle);
            updatedPost.put("body", updateBody);

            volleyResponseHelper.updatePost(postId, updatedPost, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(UpdateActivity.this, "Post updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(UpdateActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(UpdateActivity.this, "Error creating update request", Toast.LENGTH_SHORT).show();
        }
    }
}