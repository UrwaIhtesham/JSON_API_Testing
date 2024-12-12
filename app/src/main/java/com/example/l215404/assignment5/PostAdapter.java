package com.example.l215404.assignment5;

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
    private List<Post> postList;
    private onPostActionListener listener;

    public interface onPostActionListener {
        void onDelete(int postId);
        void onUpdate(Post post);
        void onShowComments(int postId);
    }

    public PostAdapter(List<Post> posts, onPostActionListener listener) {
        this.postList = posts;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());

        holder.delete.setOnClickListener( v -> listener.onDelete(post.getId()));
        holder.update.setOnClickListener( v -> listener.onUpdate(post));
        holder.comments.setOnClickListener( v -> listener.onShowComments(post.getId()));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;
        Button delete, update, comments;

        public PostViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titlePost);
            body = itemView.findViewById(R.id.bodyPost);
            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);
            comments = itemView.findViewById(R.id.comments);
        }
    }
}
