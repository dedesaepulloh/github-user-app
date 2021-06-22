package com.dedesaepulloh.githubuserssearch.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.db.FavoriteDAO;
import com.dedesaepulloh.githubuserssearch.db.FavoriteDatabase;
import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.dedesaepulloh.githubuserssearch.ui.adapter.FavoriteAdapter;
import com.dedesaepulloh.githubuserssearch.ui.detail.DetailUserActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        RecyclerView rvFavorite = findViewById(R.id.rv_favorite);
        progressBar = findViewById(R.id.progressBar_favorite);

        rvFavorite.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setHasFixedSize(true);

        FavoriteAdapter adapter = new FavoriteAdapter();
        adapter.notifyDataSetChanged();
        rvFavorite.setAdapter(adapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.user_favorite));
        }

        FavoriteDAO favoriteDAO = Room.databaseBuilder(this, FavoriteDatabase.class, "dbGithubUser")
                .allowMainThreadQueries()
                .build()
                .favoriteDAO();
        List<UserModel> listUser = favoriteDAO.getAll();
        ArrayList<UserModel> arrayUser = new ArrayList<>(listUser);

        showLoading(true);
        if (listUser.isEmpty()) {
            showLoading(false);
        }

        adapter.setListUser(arrayUser);
        adapter.setOnItemClickCallback(data -> {
            Intent intent = new Intent(FavoriteActivity.this, DetailUserActivity.class);
            intent.putExtra(DetailUserActivity.EXTRA_USER, data);
            startActivity(intent);
        });

        showLoading(false);
    }

    private void showLoading(Boolean state) {
        if (state) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.GONE);
    }

}


