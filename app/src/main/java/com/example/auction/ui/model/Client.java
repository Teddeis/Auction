package com.example.auction.ui.model;
import com.google.gson.annotations.SerializedName;

public class Client {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password_hash;

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }
}
