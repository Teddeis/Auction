package com.example.auction.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.auction.R;
import com.example.auction.databinding.FragmentHomeBinding;
import com.example.auction.ui.CommentAdapter;
import com.example.auction.ui.Details;
import com.example.auction.ui.Service.CommentService;
import com.example.auction.ui.Service.TopicService;
import com.example.auction.ui.TopicAdapter;
import com.example.auction.ui.model.Comment;
import com.example.auction.ui.model.Topic;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ListView listViewTopics;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Использование ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Инициализация ListView
        listViewTopics = root.findViewById(R.id.list);

        // Загрузка тем
        loadLot();

        return root;
    }

    private void loadLot() {
        TopicService.getTopics(new TopicService.TopicCallback() {

            @Override
            public void onSuccess(List<Topic> topics) {
                getActivity().runOnUiThread(() -> {
                    TopicAdapter adapter = new TopicAdapter(getContext(), topics);
                    listViewTopics.setAdapter(adapter);

                    // Обработка кликов на элементы списка
                    listViewTopics.setOnItemClickListener((parent, view, position, id) -> {
                        // Получение выбранной темы
                        Topic selectedTopic = topics.get(position);

                        // Переход на DetailsActivity
                        Intent intent = new Intent(getActivity(), Details.class);
                        intent.putExtra("id", selectedTopic.getIds());
                        intent.putExtra("topic_description", selectedTopic.getContent());
                        intent.putExtra("price", selectedTopic.getLikesCount());
                        intent.putExtra("author", selectedTopic.getAuthor());
                        startActivity(intent);
                    });
                });
            }

            @Override
            public void onFailure(String message) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}