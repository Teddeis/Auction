package com.example.auction.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auction.R;
import com.example.auction.ui.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private final Context context;
    private final List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    // ViewHolder class for the individual items
    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView comment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            comment = itemView.findViewById(R.id.comment);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.comments, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // Get the comment for the current position
        Comment currentComment = comments.get(position);

        // Safely bind the data to the views
        holder.author.setText(currentComment.getAuthor() != null ? currentComment.getAuthor() : "Аноним");
        holder.comment.setText(currentComment.getAvatar() != null ? currentComment.getAvatar() : "Ставка нулевая");
    }

    @Override
    public int getItemCount() {
        return comments != null ? comments.size() : 0;
    }
}
