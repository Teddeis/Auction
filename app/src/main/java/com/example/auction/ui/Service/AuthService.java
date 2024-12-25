package com.example.auction.ui.Service;

import static com.example.auction.ui.SupaBase.SupabaseConfig.SUPABASE_KEY;
import static com.example.auction.ui.SupaBase.SupabaseConfig.SUPABASE_URL;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.auction.ui.SupaBase.HttpHelper;
import com.example.auction.ui.model.Client;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.*;

public class AuthService {

    // Добавьте параметр context в метод login
    public static void login(Context context, String email, String password, LoginCallback callback) {
        String url = SUPABASE_URL + "/rest/v1/client?email=eq." + email + "&password=eq." + password;

        Request request = new Request.Builder()
                .url(url)
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
                    String responseData = response.body().string();
                    try {
                        Client[] users = new Gson().fromJson(responseData, Client[].class);
                        if (users.length > 0) {
                            Client user = users[0];
                                // Сохранение данных пользователя
                                SharedPreferences prefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("email", user.getEmail()); // Сохраняем почту
                                editor.putString("id", String.valueOf(user.getId())); // Сохраняем ID пользователя

                                editor.apply();

                                callback.onSuccess(user);
                        } else {
                            callback.onFailure("Неверный email или пароль");
                        }
                    } catch (Exception e) {
                        callback.onFailure("Ошибка обработки данных: " + e.getMessage());
                    }
                } else {
                    callback.onFailure("Не удалось выполнить вход. Код ошибки: " + response.code());
                }
            }
        });
    }

    // Интерфейс обратного вызова
    public interface LoginCallback {
        void onSuccess(Client user);
        void onFailure(String message);
    }
}
