package com.example.l215404.assignment5;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.l215404.assignment5.Models.Comment;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    private List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.nameTextView.setText(comment.getName());
        holder.emailTextView.setText(comment.getEmail());
        holder.bodyTextView.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        Log.d("CommentsAdapter", "Comments Fetched: " + comments.size());
        return comments.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, emailTextView, bodyTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameComment);
            emailTextView = itemView.findViewById(R.id.emailComments);
            bodyTextView = itemView.findViewById(R.id.bodyComments);
        }
    }
}
