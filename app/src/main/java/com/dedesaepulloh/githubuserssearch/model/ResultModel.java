package com.dedesaepulloh.githubuserssearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultModel {
    @SerializedName("items")
    @Expose
    private List<UserModel> result;

    public List<UserModel> getResult() {
        return result;
    }

    public void setResult(List<UserModel> result) {
        this.result = result;
    }

}
