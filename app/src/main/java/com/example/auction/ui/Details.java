package com.example.auction.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auction.MainActivity;
import com.example.auction.R;
import com.example.auction.ui.Service.CommentCreateService;
import com.example.auction.ui.Service.CommentService;
import com.example.auction.ui.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    private EditText betTextContent;
    private TextView textViewDescription2;

    private Button button2, buttonCreateBet;
    private RecyclerView recyclerViewBet;
    private String topicId;
    private double initialBid;  // Переменная для хранения первоначальной ставки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Получение данных из Intent
        topicId = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("topic_description");
        String description = getIntent().getStringExtra("price");
        initialBid = Double.parseDouble(description);  // Инициализация первоначальной ставки

        // Установка данных в TextView
        TextView sss = findViewById(R.id.topic_ds_s);
        TextView textViewTitle = findViewById(R.id.topic_title_details);
        TextView textViewDescription = findViewById(R.id.topic_content_details);

        sss.setText(topicId);
        textViewTitle.setText("Лот: " + title);
        textViewDescription.setText("Стоимость: " + description);

        // Инициализация RecyclerView
        recyclerViewBet = findViewById(R.id.topic_commentlist_details);
        recyclerViewBet.setLayoutManager(new LinearLayoutManager(this));

        // Загрузка комментариев
        loadComments();

        // Обработчик кнопки возврата
        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(Details.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        betTextContent = findViewById(R.id.topic_comment_details);
        buttonCreateBet = findViewById(R.id.buttonSt);

        buttonCreateBet.setOnClickListener(v -> {
            createBet();
        });
    }

    private void loadComments() {
        try {
            textViewDescription2 = findViewById(R.id.topic_content_details2);
            textViewDescription2.setText("Последняя ставка: " + initialBid);  // Показываем первоначальную ставку

            CommentService.getComment(new CommentService.CommentCallback() {
                @Override
                public void onSuccess(List<Comment> comments) {
                    if (comments == null || comments.isEmpty()) {
                        runOnUiThread(() -> Toast.makeText(Details.this, "Нет данных", Toast.LENGTH_SHORT).show());
                        return;
                    }

                    List<Comment> filteredComments = new ArrayList<>();
                    double highestBid = initialBid; // Начинаем с первоначальной ставки

                    for (Comment comment : comments) {
                        if (topicId != null && topicId.equals(String.valueOf(comment.getAucId()))) {
                            filteredComments.add(comment);

                            // Предполагаем, что в content хранится ставка как строка, и её нужно конвертировать в число
                            double currentBid = 0;
                            try {
                                currentBid = Double.parseDouble(comment.getAvatar()); // Используем getAvatar() для ставки
                            } catch (NumberFormatException e) {
                                // Если не удается преобразовать, пропускаем этот комментарий
                            }

                            // Обновляем самую высокую ставку
                            if (currentBid > highestBid) {
                                highestBid = currentBid;
                            }
                        }
                    }

                    final double finalHighestBid = highestBid;
                    runOnUiThread(() -> {
                        // Обновляем текст View для самой высокой ставки
                        textViewDescription2.setText("Последняя ставка: " + finalHighestBid);

                        if (!filteredComments.isEmpty()) {
                            // Создание и установка адаптера для RecyclerView
                            CommentAdapter adapter = new CommentAdapter(Details.this, filteredComments);
                            recyclerViewBet.setAdapter(adapter);
                        } else {
                            Toast.makeText(Details.this, "Ставок нет, будьте первыми.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(String message) {
                    runOnUiThread(() -> Toast.makeText(Details.this, "Ошибка загрузки комментариев: " + message, Toast.LENGTH_SHORT).show());
                }
            });
        } catch (Exception e) {
            runOnUiThread(() -> Toast.makeText(Details.this, "Произошла ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }

    private void createBet() {
        String content = betTextContent.getText().toString().trim();

        if (content.isEmpty()) {
            Toast.makeText(Details.this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Преобразуем введенную ставку в число
        double newBid = 0;
        try {
            newBid = Double.parseDouble(content); // Здесь предполагаем, что пользователь вводит число
        } catch (NumberFormatException e) {
            Toast.makeText(Details.this, "Введите корректную ставку", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверяем, чтобы ставка была больше первоначальной
        if (newBid <= initialBid) {
            Toast.makeText(Details.this, "Ставка должна быть больше первоначальной ставки!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Создание нового объекта Comment
        Comment newComment = new Comment(Details.this, topicId, content);

        // Вызов метода для создания комментария
        CommentCreateService.createComment(newComment, new CommentCreateService.CreateCommentCallback() {
            @Override
            public void onSuccess(String message) {
                Details.this.runOnUiThread(() -> {
                    Toast.makeText(Details.this, "Ставка успешно добавлена!", Toast.LENGTH_SHORT).show();
                    betTextContent.setText(""); // Очистка поля ввода
                    loadComments(); // Перезагрузить комментарии
                });
            }

            @Override
            public void onFailure(String message) {
                Details.this.runOnUiThread(() -> {
                    Toast.makeText(Details.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
