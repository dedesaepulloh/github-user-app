package com.dedesaepulloh.githubuserssearch.ui.adapter;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.db.FavoriteDAO;
import com.dedesaepulloh.githubuserssearch.db.FavoriteDatabase;
import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final ArrayList<UserModel> listUser = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setListUser(ArrayList<UserModel> listUser) {
        this.listUser.clear();
        this.listUser.addAll(listUser);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.bind(listUser.get(position));
        holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(listUser.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        final TextView tvName;
        final CircleImageView imgAvatar;
        final Button btnDeleteFavorite;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name);
            imgAvatar = itemView.findViewById(R.id.img_photo);
            btnDeleteFavorite = itemView.findViewById(R.id.btn_delete_fav);
        }

        void bind(final UserModel userModel) {
            tvName.setText(userModel.getLogin());
            Glide.with(itemView.getContext())
                    .load(userModel.getAvatarUrl())
                    .apply(new RequestOptions().override(60, 60))
                    .into(imgAvatar);

            btnDeleteFavorite.setOnClickListener(view -> {
                final AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                alert.setTitle("Delete");
                alert.setMessage("Delete From Favorite ?");
                alert.setCancelable(false);
                alert.setPositiveButton("Yes", (dialogInterface, i) -> {
                    FavoriteDAO favoriteDAO = Room.databaseBuilder(itemView.getContext(), FavoriteDatabase.class, "dbGithubUser")
                            .allowMainThreadQueries()
                            .build()
                            .favoriteDAO();

                    favoriteDAO.deleteById(userModel.getId());

                    listUser.remove(userModel);
                    notifyDataSetChanged();
                    Snackbar.make(view, "Deleted From Favorite", Snackbar.LENGTH_SHORT).show();

                });
                alert.setNegativeButton("No", ((dialogInterface, i) -> alert.setCancelable(true)));
                alert.show();
            });
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(UserModel data);
    }
}


