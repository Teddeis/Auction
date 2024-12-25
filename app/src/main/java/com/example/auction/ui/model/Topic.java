package com.example.auction.ui.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

import java.time.format.DateTimeFormatter;

public class Topic {
    @SerializedName("id")
    private String id;

    @SerializedName("content")
    public String content;

    @SerializedName("price")
    private String likes_count;

    @SerializedName("author")
    public String author;

    // Getters and Setters

    public Topic(Context context, String price, String content) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        this.author = prefs.getString("email", "Unknown");
        this.content = content;
        this.likes_count = price;
    }

    public String getIds() {
        return id;
    }

    public void setIds(String id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getLikesCount() {
        return likes_count;
    }

    public void setLikesCount(String likes_count) {
        this.likes_count = likes_count;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
