package com.dedesaepulloh.githubuserssearch.rest;

import com.dedesaepulloh.githubuserssearch.model.ResultModel;
import com.dedesaepulloh.githubuserssearch.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/search/users")
    Call<ResultModel> getListUser(@Query("q") String username);

    @GET("/users/{username}")
    Call<UserModel> getDetailUser(@Path("username") String username);

    @GET("/users/{username}/followers")
    Call<List<UserModel>> getListFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    Call<List<UserModel>> getListFollowing(@Path("username") String username);
}
