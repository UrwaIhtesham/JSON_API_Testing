package com.example.l215404.assignment5;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.l215404.assignment5.Models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    //Variables
    private List<Post> postList;
    private onPostActionListener listener;

    //interface for buttons when clicked
    public interface onPostActionListener {
        //when delete button is clicked, it should delete that post from the list
        void onDelete(int postId);
        //when update button is clicked, it should go to updateActivity
        void onUpdate(Post post);
        //when comments button is clicked, it should go to CommentsActivity
        void onShowComments(int postId);
    }

    //constructor having list and listener
    public PostAdapter(List<Post> posts, onPostActionListener listener) {
        this.postList = posts;
        this.listener = listener;
    }

    //overriding onCreateViewHolder and inflating the layout
    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts, parent, false);
        return new PostViewHolder(view);
    }

    //overriding onBindViewHolder and setting text and buttons
    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post post = postList.get(position);
        //Logging the title and body
        Log.d("PostAdapter", "getTitle: " + post.getTitle());
        Log.d("PostAdapter", "getBody: " + post.getBody());
        //setting title and body using holder
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());

        //when buttons are clicked,
        holder.delete.setOnClickListener( v -> listener.onDelete(post.getId()));
        holder.update.setOnClickListener( v -> listener.onUpdate(post));
        holder.comments.setOnClickListener( v -> listener.onShowComments(post.getId()));
    }

    //overriding the getItemCount function
    @Override
    public int getItemCount() {
        //returning size of the list
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;
        Button delete, update, comments;

        public PostViewHolder(View itemView) {
            super(itemView);
            //binding variables with the layout
            title = itemView.findViewById(R.id.titlePost);
            body = itemView.findViewById(R.id.bodyPost);
            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);
            comments = itemView.findViewById(R.id.comments);
        }
    }
}
