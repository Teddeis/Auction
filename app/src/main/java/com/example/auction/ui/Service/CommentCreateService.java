package com.example.auction.ui.Service;

import static com.example.auction.ui.SupaBase.SupabaseConfig.SUPABASE_KEY;
import static com.example.auction.ui.SupaBase.SupabaseConfig.SUPABASE_URL;

import androidx.annotation.NonNull;

import com.example.auction.ui.SupaBase.HttpHelper;
import com.example.auction.ui.model.Comment;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentCreateService {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void createComment(Comment newComment, CreateCommentCallback callback) {
        String url = SUPABASE_URL + "/rest/v1/last_bet";

        // Преобразуем объект Topic в JSON
        String jsonBody = new Gson().toJson(newComment);
        RequestBody body = RequestBody.create(jsonBody, JSON);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + SUPABASE_KEY)
                .addHeader("apikey", SUPABASE_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        HttpHelper.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure("Ошибка сети: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onSuccess("Комментарий создан");
                } else {
                    callback.onFailure("Ошибка создания комментария: Код " + response.code());
                }
            }
        });
    }

    // Интерфейс для создания новой темы
    public interface CreateCommentCallback {
        void onSuccess(String message);
        void onFailure(String message);
    }
}

