package com.example.auction.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.auction.R;
import com.example.auction.ui.model.Comment;

import java.util.List;

public class BetAdapter extends BaseAdapter {
    private final Context context;
    private final List<Comment> topics;

    public BetAdapter(Context context, List<Comment> topics) {
        this.context = context;
        this.topics = topics;
    }

    @Override
    public int getCount() {
        return topics != null ? topics.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return topics != null && position >= 0 && position < topics.size() ? topics.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.last_bet_activity, parent, false);
            holder = new ViewHolder();
            holder.ids = convertView.findViewById(R.id.id_auc);
            holder.price = convertView.findViewById(R.id.comment);
            holder.author = convertView.findViewById(R.id.author);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position >= 0 && position < topics.size()) {
            Comment topic = topics.get(position);


            holder.ids.setText(topic.getAucId());
            holder.price.setText(topic.getAvatar());
            holder.author.setText(topic.getAuthor());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView ids;
        TextView price;
        TextView author;
    }
}
