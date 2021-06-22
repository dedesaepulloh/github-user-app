package com.dedesaepulloh.githubuserssearch.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.dedesaepulloh.githubuserssearch.rest.ApiClient;
import com.dedesaepulloh.githubuserssearch.rest.ApiInterface;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class FollowerViewModel extends ViewModel {
    private final MutableLiveData<List<UserModel>> listFollower = new MutableLiveData<>();

    public MutableLiveData<List<UserModel>> getListFollower() {
        return listFollower;
    }

    public void setListFollowers(String username, Context context) {
        ApiInterface Service;
        retrofit2.Call<List<UserModel>> Call;
        try {
            Service = ApiClient.getApi().create(ApiInterface.class);
            Call = Service.getListFollowers(username);
            Call.enqueue(new Callback<List<UserModel>>() {
                @Override
                public void onResponse(retrofit2.Call<List<UserModel>> call, Response<List<UserModel>> response) {
                    Log.d("Response", "" + " " + response.body());
                    List<UserModel> listUser;
                    listUser = response.body();
                    listFollower.postValue(listUser);
                }

                @Override
                public void onFailure(retrofit2.Call<List<UserModel>> call, Throwable t) {
                    Log.d("Message", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
