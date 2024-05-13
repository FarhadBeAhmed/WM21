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
import com.wm21ltd.wm21.adapters.RewardPolicyAdapter;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.interfaces.OnRewardPolicyView;
import com.wm21ltd.wm21.networks.Models.RewardPolicyDataListModel;
import com.wm21ltd.wm21.presenters.RewardPolicyPresenter;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardPolicyFragment extends Fragment implements OnRewardPolicyView {

    View mView;
    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    RewardPolicyPresenter rewardPolicyPresenter;
    @BindView(R.id.rv_rewardPolicy)
    RecyclerView recyclerViewRewardPolicy;
    private List<RewardPolicyDataListModel> rewardsModel = new ArrayList<>();
    private RewardPolicyAdapter rAdapter;
    MaterialDialog dialog;

    public RewardPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reward_policy, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();
        rewardPolicyPresenter = new RewardPolicyPresenter(this);
        initializedFields();
        return mView;
    }

    private void initializedFields() {
        rAdapter = new RewardPolicyAdapter(rewardsModel, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewRewardPolicy.setAdapter(rAdapter);
        recyclerViewRewardPolicy.setLayoutManager(layoutManager);
        parseData();
    }

    private void parseData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            rewardPolicyPresenter.onRewardPolicyRequestData();
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardPolicyData(List<RewardPolicyDataListModel> rewardModel) {
        rewardsModel.clear();
        rewardsModel.addAll(rewardModel);
        rAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRewardPolicyStartLoading() {
        dialog.show();
    }

    @Override
    public void onRewardPolicyStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onRewardPolicyShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
