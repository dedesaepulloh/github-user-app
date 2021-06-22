package com.dedesaepulloh.githubuserssearch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.dedesaepulloh.githubuserssearch.ui.adapter.UserAdapter;
import com.dedesaepulloh.githubuserssearch.ui.detail.DetailUserActivity;
import com.dedesaepulloh.githubuserssearch.ui.favorite.FavoriteActivity;
import com.dedesaepulloh.githubuserssearch.ui.setting.SettingActivity;
import com.dedesaepulloh.githubuserssearch.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtSearch;
    private RecyclerView rvItemSearch;
    private ProgressBar progressBar;

    private UserViewModel userViewModel;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch = findViewById(R.id.edt_search);
        ImageButton imgBtnSearch = findViewById(R.id.img_btn_search);
        rvItemSearch = findViewById(R.id.rv_item_search);
        progressBar = findViewById(R.id.progressBar);

        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UserViewModel.class);
        rvItemSearch.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvItemSearch.setLayoutManager(new LinearLayoutManager(this));

        imgBtnSearch.setOnClickListener(this);
        showLoading(false);

        userViewModel.getListUsers().observe(this, list -> {
            adapter = new UserAdapter();
            adapter.setListUsers(list);
            rvItemSearch.setAdapter(adapter);
            showLoading(false);

            adapter.setOnItemClickCallback((UserModel data) -> {
                showLoading(true);
                Intent mIntent = new Intent(MainActivity.this, DetailUserActivity.class);
                mIntent.putExtra(DetailUserActivity.EXTRA_USER, data);
                startActivity(mIntent);
            });
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_btn_search) {
            showLoading(true);
            if (edtSearch.getText().toString().isEmpty()) {
                edtSearch.setError("Username Required");
                edtSearch.setFocusable(true);
                showLoading(false);
            } else {
                String username = edtSearch.getText().toString();
                userViewModel.setListUsers(username, getApplicationContext());
            }
        }
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_setting) {
            Intent intentSetting = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intentSetting);
        }

        if (item.getItemId() == R.id.favorite) {
            Intent intentFavorite = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intentFavorite);
        }
        return super.onOptionsItemSelected(item);
    }
}
