package com.dedesaepulloh.githubuserssearch.utils.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dedesaepulloh.githubuserssearch.db.FavoriteDAO;
import com.dedesaepulloh.githubuserssearch.db.FavoriteDatabase;

import java.util.Objects;

import static com.dedesaepulloh.githubuserssearch.model.UserModel.TABLE_NAME;

@SuppressLint("Registered")
public class FavoriteProvider extends ContentProvider {
    private static final int CODE_USER = 1;
    private static final int CODE_ITEM_USER = 2;

    private static final String AUTHORITY = "com.dedesaepulloh.githubuserssearch.utils.provider";

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, CODE_USER);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/*", CODE_ITEM_USER);

    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public boolean onCreate() {
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final int code = uriMatcher.match(uri);
        if (code == CODE_USER || code == CODE_ITEM_USER) {
            final Context context = getContext();

            if (context == null) {
                return null;
            }

            FavoriteDAO mFavoriteDAO = FavoriteDatabase.getDatabase(context).favoriteDAO();
            Cursor cursor = null;

            if (code == CODE_USER) {
                cursor = mFavoriteDAO.selectAll();
                Log.d("Provider-data ", "Data-size : " + cursor.getCount());
            }
            Objects.requireNonNull(cursor).setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

}
