package com.dedesaepulloh.githubuserssearch.ui.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.db.FavoriteDAO;
import com.dedesaepulloh.githubuserssearch.db.FavoriteDatabase;
import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.dedesaepulloh.githubuserssearch.rest.ApiClient;
import com.dedesaepulloh.githubuserssearch.rest.ApiInterface;
import com.dedesaepulloh.githubuserssearch.ui.adapter.SectionsPagerAdapter;
import com.dedesaepulloh.githubuserssearch.ui.favorite.FavoriteActivity;
import com.dedesaepulloh.githubuserssearch.ui.setting.SettingActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity{

    private TextView tvName, tvRepoFollow, tvLocation, tvUsername, tvCompany;
    private Button btnAddFavorite, btnDeleteFavorite;
    public static final String EXTRA_USER = "extra_user";
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        tvName = findViewById(R.id.tv_name);
        tvUsername = findViewById(R.id.tv_username);
        tvRepoFollow = findViewById(R.id.tv_repo_follow);
        tvLocation = findViewById(R.id.tv_location);
        tvCompany = findViewById(R.id.tv_company);
        CircleImageView imgAvatar = findViewById(R.id.img_avatar);
        btnAddFavorite = findViewById(R.id.btn_add_favorite);
        btnDeleteFavorite = findViewById(R.id.btn_delete_favorite);

        UserModel userModel = getIntent().getParcelableExtra(EXTRA_USER);
        assert userModel != null;
        getDetailUser(userModel.getLogin());
        Glide.with(getApplicationContext())
                .load(userModel.getAvatarUrl())
                .apply(new RequestOptions().override(150, 150))
                .into(imgAvatar);

        SectionsPagerAdapter sectionsPageAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), userModel);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        String title = getString(R.string.detail_title);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        FavoriteDAO favoriteDAO = Room.databaseBuilder(this, FavoriteDatabase.class, "dbGithubUser")
                .allowMainThreadQueries()
                .build()
                .favoriteDAO();
        UserModel check = favoriteDAO.getUserByLogin(username);
//        long result = favoriteDAO.selectById(userModel.getId());
        if (check != null) {
            btnAddFavorite.setVisibility(View.GONE);
            btnDeleteFavorite.setVisibility(View.VISIBLE);
        } else {
            btnAddFavorite.setVisibility(View.VISIBLE);
            btnDeleteFavorite.setVisibility(View.GONE);
        }

        btnAddFavorite.setOnClickListener(view -> {
            favoriteDAO.insertAll(userModel);
            Toast.makeText(DetailUserActivity.this, "Successfully add to favorite!", Toast.LENGTH_SHORT).show();
            btnAddFavorite.setVisibility(View.GONE);
            btnDeleteFavorite.setVisibility(View.VISIBLE);
        });

        btnDeleteFavorite.setOnClickListener(view -> {
            favoriteDAO.deleteById(userModel.getId());
            Toast.makeText(DetailUserActivity.this, "Successfully delete from favorite!", Toast.LENGTH_SHORT).show();
            btnAddFavorite.setVisibility(View.VISIBLE);
            btnDeleteFavorite.setVisibility(View.GONE);
        });

    }

    private void getDetailUser(String username) {
        ApiInterface Service;
        retrofit2.Call<UserModel> Call;
        try {
            Service = ApiClient.getApi().create(ApiInterface.class);
            Call = Service.getDetailUser(username);
            Call.enqueue(new Callback<UserModel>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(retrofit2.Call<UserModel> call, Response<UserModel> response) {
                    String followers = getString(R.string.followers);
                    String following = getString(R.string.following);
                    String repository = getString(R.string.repository);
                    UserModel mUser = response.body();
                    assert mUser != null;
                    tvName.setText(mUser.getName());
                    tvUsername.setText(mUser.getLogin());
                    tvLocation.setText(mUser.getLocation());
                    tvCompany.setText(mUser.getCompany());
                    tvRepoFollow.setText(mUser.getRepository() + " " + repository + " " + " " + " " + mUser.getFollowers() + " " + followers + " " + " " + " " + mUser.getFollowing() + " " + following);

                }

                @Override
                public void onFailure(retrofit2.Call<UserModel> call, Throwable t) {
                    Log.d("Message", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_setting) {
            Intent intentSetting = new Intent(DetailUserActivity.this, SettingActivity.class);
            startActivity(intentSetting);
        }

        if (item.getItemId() == R.id.favorite) {
            Intent favoriteIntent = new Intent(DetailUserActivity.this, FavoriteActivity.class);
            startActivity(favoriteIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
