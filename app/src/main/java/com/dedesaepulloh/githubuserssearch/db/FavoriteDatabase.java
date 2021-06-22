package com.dedesaepulloh.githubuserssearch.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dedesaepulloh.githubuserssearch.model.UserModel;

@Database(entities = {UserModel.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDAO favoriteDAO();

    private static volatile FavoriteDatabase INSTANCE;

    public static FavoriteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteDatabase.class, "dbGithubUser")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
