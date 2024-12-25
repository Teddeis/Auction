package com.example.auction.ui.SupaBase;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpHelper {
    public static final OkHttpClient client = new OkHttpClient();

    public static void post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.SUPABASE_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).enqueue(callback);
    }
}
