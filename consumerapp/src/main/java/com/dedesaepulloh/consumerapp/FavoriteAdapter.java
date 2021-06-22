package com.dedesaepulloh.consumerapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Cursor cursor;
    private final Context context;

    void setData(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        if (cursor.moveToPosition(position)){
            holder.tvName.setText(cursor.getString(cursor.getColumnIndexOrThrow("login")));
            Glide.with(context)
                    .load(cursor.getString(cursor.getColumnIndexOrThrow("avatar_url")))
                    .apply(new RequestOptions().override(60, 60))
                    .into(holder.imgAvatar);
            holder.view.setOnClickListener(v -> Toast.makeText(context, holder.tvName.getText() , Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final CircleImageView imgAvatar;
        final View view;
        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name);
            imgAvatar = itemView.findViewById(R.id.img_photo);
            view = itemView;
        }

    }
}
