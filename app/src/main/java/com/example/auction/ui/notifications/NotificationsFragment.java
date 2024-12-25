package com.example.auction.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auction.ui.BetAdapter;
import com.example.auction.databinding.FragmentNotificationsBinding;
import com.example.auction.ui.CommentAdapter;
import com.example.auction.ui.Service.BetService;
import com.example.auction.ui.Service.CommentService;
import com.example.auction.ui.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView recyclerViewComments; // Используем RecyclerView вместо ListView

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Инициализация RecyclerView
        recyclerViewComments = binding.recyclerViewComments;
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(requireContext())); // Устанавливаем LayoutManager для RecyclerView

        // Загрузка комментариев
        loadNotification();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadNotification() {
        try {
            CommentService.getComment(new CommentService.CommentCallback() {
                @Override
                public void onSuccess(List<Comment> comments) {
                    if (comments == null || comments.isEmpty()) {
                        Toast.makeText(requireContext(), "Нет данных", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        CommentAdapter adapter = new CommentAdapter(requireContext(), comments);
                        recyclerViewComments.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(requireContext(), "Ошибка загрузки комментариев: " + message, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Произошла ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
