package com.example.auction.ui.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.ui.Service.AuthService;
import com.example.auction.ui.model.Client;


public class Login extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin,buttonRegistrationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Получаем ссылки на элементы интерфейса
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        buttonLogin = findViewById(R.id.button3);
        buttonRegistrationView = findViewById(R.id.button2);

        // Устанавливаем обработчик нажатия на кнопку "Войти"
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // Устанавливаем обработчик нажатия на кнопку "Войти"
        buttonRegistrationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public  void  login()
    {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        AuthService.login(this,email,password, new AuthService.LoginCallback() {
            @Override
            public void onSuccess(Client user) {
                runOnUiThread(() -> {
                    Toast.makeText(Login.this, "Добро пожаловать, " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    // Переход на следующий экран
                    Intent intent = new Intent(Login.this, MainActivity.class);

                    startActivity(intent);
                    finish();
                });
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show());
            }
        });
    }
    private String getCurrentUserId() {
        SharedPreferences sharedPreferences = Login.this.getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id", null); // Replace with actual key used to store the user ID
    }
}