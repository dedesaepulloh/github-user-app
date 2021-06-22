package com.dedesaepulloh.githubuserssearch.ui.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.dedesaepulloh.githubuserssearch.ui.fragment.ListFollowerFragment;
import com.dedesaepulloh.githubuserssearch.ui.fragment.ListFollowingFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final UserModel userModel;

    public SectionsPagerAdapter(Context mContext, FragmentManager fm, UserModel userModel) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = mContext;
        this.userModel = userModel;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.followers,
            R.string.following
    };


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ListFollowerFragment();
                Bundle bundleFollowers = new Bundle();
                bundleFollowers.putString(ListFollowerFragment.EXTRA_FOLLOWERS, userModel.getLogin());
                fragment.setArguments(bundleFollowers);
                break;

            case 1:
                fragment = new ListFollowingFragment();
                Bundle bundleFollowing = new Bundle();
                bundleFollowing.putString(ListFollowingFragment.EXTRA_FOLLOWING, userModel.getLogin());
                fragment.setArguments(bundleFollowing);
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
