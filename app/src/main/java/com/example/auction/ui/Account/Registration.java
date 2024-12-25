package com.example.auction.ui.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auction.R;
import com.example.auction.ui.Service.RegService;
import com.example.auction.ui.model.Client;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonRegister, buttonBackToLogin;

    // Регулярное выражение для валидации email
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        editTextEmail = findViewById(R.id.editTextEmailAddresss);
        editTextPassword = findViewById(R.id.editTextTextPasswords);

        buttonRegister = findViewById(R.id.button3);
        buttonBackToLogin = findViewById(R.id.button2);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        buttonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void register() {
        if (editTextEmail == null || editTextPassword == null) {
            Toast.makeText(this, "Ошибка инициализации полей ввода", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка корректности email с использованием регулярного выражения
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            Toast.makeText(this, "Пожалуйста, введите корректный email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создаём объект нового пользователя
        Client newUser = new Client();
        newUser.setEmail(email);
        newUser.setPasswordHash(password);

        // Вызываем метод регистрации
        RegService.register(newUser, new RegService.RegisterCallback() {
            @Override
            public void onSuccess(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show();
                    // Переход на экран логина после успешной регистрации
                    Intent intent = new Intent(Registration.this, Login.class);
                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(Registration.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }
}
