package com.dedesaepulloh.githubuserssearch.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = UserModel.TABLE_NAME)
public class UserModel implements Parcelable {

    public static final String TABLE_NAME = "favorite";

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public Integer id;

    @SerializedName("login")
    @Expose
    @ColumnInfo(name = "login")
    private String login;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("avatar_url")
    @Expose
    @ColumnInfo(name = "avatar_url")
    private String avatarUrl;

    @SerializedName("html_url")
    @Expose
    private String htmlUrl;

    @SerializedName("followers")
    @Expose
    @ColumnInfo(name = "follower")
    private Integer followers;

    @SerializedName("following")
    @Expose
    @ColumnInfo(name = "following")
    private Integer following;

    @SerializedName("location")
    @Expose
    @ColumnInfo(name = "location")
    private String location;

    @SerializedName("company")
    @Expose
    @ColumnInfo(name = "company")
    private String company;

    @SerializedName("public_repos")
    @Expose
    @ColumnInfo(name = "repository")
    private Integer repository;

    public UserModel() {

    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getRepository() {
        return repository;
    }

    public void setRepository(Integer repository) {
        this.repository = repository;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserModel(int id, String login, String name, String avatar_url) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.avatarUrl = avatar_url;
    }

    public UserModel(String login, String name, String avatarUrl, String htmlUrl, Integer followers, Integer following, String company, String location, Integer repository) {
        this.login = login;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
        this.followers = followers;
        this.following = following;
        this.company = company;
        this.location = location;
        this.repository = repository;
    }

    protected UserModel(Parcel in) {
        id = in.readInt();
        login = in.readString();
        name = in.readString();
        avatarUrl = in.readString();
        htmlUrl = in.readString();
        company = in.readString();
        location = in.readString();

    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(login);
        dest.writeString(name);
        dest.writeString(avatarUrl);
        dest.writeString(htmlUrl);
        dest.writeString(company);
        dest.writeString(location);
    }


}
