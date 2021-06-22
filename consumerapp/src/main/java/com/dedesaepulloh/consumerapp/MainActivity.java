package com.dedesaepulloh.consumerapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final String AUTHORITY = "com.dedesaepulloh.githubuserssearch.utils.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + "favorite");

    private static final int LOADER = 1;
    private FavoriteAdapter adapter;
    private RecyclerView rvFavorite;



    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFavorite = findViewById(R.id.rv_favorite);

        rvFavorite.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new FavoriteAdapter(this);
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setLayoutManager(new LinearLayoutManager(rvFavorite.getContext()));
        rvFavorite.setAdapter(adapter);


        LoaderManager.getInstance(this).initLoader(LOADER, null, mLoaderCallbacks);

    }

    private final LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<Cursor>() {

                @Override
                @NonNull
                public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
                    if (id == LOADER) {
                        return new CursorLoader(
                                getApplicationContext(),
                                CONTENT_URI,
                                null,
                                null,
                                null,
                                null);
                    }else {
                        throw new IllegalArgumentException();
                    }
                }

                @Override
                public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
                    if (loader.getId() == LOADER) {
                        adapter.setData(data);
                        if (adapter.getItemCount() == 0) {
                            rvFavorite.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onLoaderReset(@NonNull Loader<Cursor> loader) {
                    if (loader.getId() == LOADER) {
                        adapter.setData(null);
                    }
                }

            };


}
