package com.wm21ltd.wm21.fragments;


import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.adapters.RewardAchievementAdapter;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.interfaces.OnRewardAchievementView;
import com.wm21ltd.wm21.networks.Models.RewardAchievementDataListModel;
import com.wm21ltd.wm21.presenters.RewardAchievementPresenter;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardAchievementFragment extends Fragment implements OnRewardAchievementView {

    View mView;
    CheckInternetConnection checkInternetConnection;
    AppSessionManager appSessionManager;
    MaterialDialog dialog;
    @BindView(R.id.rv_RewardAchievement)
    RecyclerView recyclerViewRewardAchievement;

    private List<RewardAchievementDataListModel> dataListModels = new ArrayList<>();
    private RewardAchievementAdapter mAdapter;
    RewardAchievementPresenter rewardAchievementPresenter;

    public RewardAchievementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_achievement, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();
        rewardAchievementPresenter = new RewardAchievementPresenter(this);
        initializedFields();
        return mView;
    }

    private void initializedFields() {
        mAdapter = new RewardAchievementAdapter(dataListModels, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewRewardAchievement.setAdapter(mAdapter);
        recyclerViewRewardAchievement.setLayoutManager(layoutManager);
        parseData();
    }

    private void parseData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            rewardAchievementPresenter.onRewardAchievementResponseData(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID));
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardAchievementData(List<RewardAchievementDataListModel> achievementData) {
        dataListModels.clear();
        dataListModels.addAll(achievementData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRewardAchievementStartLoading() {
        dialog.show();
    }

    @Override
    public void onRewardAchievementStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onRewardAchievementShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
