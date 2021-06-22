package com.dedesaepulloh.githubuserssearch.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dedesaepulloh.githubuserssearch.model.UserModel;

import java.util.List;

@Dao
public interface FavoriteDAO {

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    List<UserModel> getAll();

    @Query("SELECT * FROM favorite WHERE login = :login")
    UserModel getUserByLogin(String login);

    @Query("SELECT * FROM favorite ORDER BY id DESC")
    Cursor selectAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserModel... users);

    @Query("DELETE FROM favorite WHERE id = :id")
    int deleteById(long id);
    @Query("SELECT * FROM favorite WHERE id = :id")
    int selectById(long id);


}
