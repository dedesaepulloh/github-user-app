package com.dedesaepulloh.githubuserssearch.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.model.ResultModel;
import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.dedesaepulloh.githubuserssearch.rest.ApiClient;
import com.dedesaepulloh.githubuserssearch.rest.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Callback;
import retrofit2.Response;


public class UserViewModel extends ViewModel {
    private final MutableLiveData<List<UserModel>> listUsers = new MutableLiveData<>();

    public MutableLiveData<List<UserModel>> getListUsers() {
        return listUsers;
    }

    public void setListUsers(String username, final Context context) {
        ApiInterface Service;
        retrofit2.Call<ResultModel> Call;
        try {
            Service = ApiClient.getApi().create(ApiInterface.class);
            Call = Service.getListUser(username);
            Call.enqueue(new Callback<ResultModel>() {
                @Override
                public void onResponse(retrofit2.Call<ResultModel> call, Response<ResultModel> response) {
                    Log.d("Response", "" + " " + response.body());
                    List<UserModel> listUser;
                    assert response.body() != null;
                    listUser = response.body().getResult();
                    listUsers.postValue(listUser);
                    if (listUser.isEmpty()) {
                        Toast.makeText(context, R.string.not_found, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<ResultModel> call, Throwable t) {
                    Log.d("Message", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
