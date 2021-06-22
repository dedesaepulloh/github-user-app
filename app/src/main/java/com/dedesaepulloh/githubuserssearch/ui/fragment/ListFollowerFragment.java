package com.dedesaepulloh.githubuserssearch.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dedesaepulloh.githubuserssearch.R;
import com.dedesaepulloh.githubuserssearch.model.UserModel;
import com.dedesaepulloh.githubuserssearch.ui.adapter.UserAdapter;
import com.dedesaepulloh.githubuserssearch.ui.detail.DetailUserActivity;
import com.dedesaepulloh.githubuserssearch.viewmodel.FollowerViewModel;


public class ListFollowerFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView rvFollowers;
    private UserAdapter adapter;

    public static final String EXTRA_FOLLOWERS = "extra_followers";

    public ListFollowerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_follower, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.pb_followers);
        rvFollowers = view.findViewById(R.id.rv_followers);
        rvFollowers.setLayoutManager(new LinearLayoutManager(getContext()));

        String username = getArguments().getString(EXTRA_FOLLOWERS);

        showLoading(false);

        FollowerViewModel followerViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel.class);
        followerViewModel.setListFollowers(username, getContext());
        followerViewModel.getListFollower().observe(getViewLifecycleOwner(), list -> {
            adapter = new UserAdapter();
            adapter.notifyDataSetChanged();
            adapter.setListUsers(list);

            rvFollowers.setAdapter(adapter);
            showLoading(false);
            adapter.setOnItemClickCallback((UserModel data) -> {
                showLoading(true);
                Intent mIntentFollower = new Intent(getContext(), DetailUserActivity.class);
                mIntentFollower.putExtra(DetailUserActivity.EXTRA_USER, data);
                startActivity(mIntentFollower);
            });
        });


    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading(false);
    }
}
