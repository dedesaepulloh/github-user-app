package com.dedesaepulloh.githubuserssearch.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    @SerializedName("items")
    @Expose
    private List<UserModel> listUsers;
    private Context context;
    private OnItemClickCallback onItemClickCallback;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserAdapter() {
    }

    public List<UserModel> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<UserModel> listUsers) {
        this.listUsers = listUsers;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        holder.bind(listUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listUsers.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final CircleImageView imgAvatar;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name);
            imgAvatar = itemView.findViewById(R.id.img_photo);
        }

        void bind(final UserModel userModel) {
            tvName.setText(userModel.getLogin());
            Glide.with(itemView.getContext())
                    .load(userModel.getAvatarUrl())
                    .apply(new RequestOptions().override(60, 60))
                    .into(imgAvatar);

            itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(listUsers.get(getAdapterPosition())));
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(UserModel data);
    }
}
