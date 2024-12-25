package com.example.auction.ui.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id")
    private int id;
    @SerializedName("id_auc")
    private String id_auc;
    @SerializedName("last_price")
    private String last_price;
    @SerializedName("author")
    private String author;


    // Конструктор
    public Comment(Context context, String idTopics, String comments) {
        SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        this.id_auc = idTopics;
        this.last_price = comments; //
        this.author = prefs.getString("email", "Unknown"); // Default to "Unknown" if username is not found
    }

    // Геттеры и сеттеры
    public String getAucId() {
        return id_auc;
    }

    public void setAucId(String id_auc) {
        this.id_auc = id_auc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvatar() {
        return last_price;
    }

    public void setAvatar(String last_price) {
        this.last_price = last_price;
    }
}
