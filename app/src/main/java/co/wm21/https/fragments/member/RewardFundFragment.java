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
import com.wm21ltd.wm21.adapters.RewardFundAdapter;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.interfaces.OnRewardFundView;
import com.wm21ltd.wm21.networks.Models.RewardFundDataListModel;
import com.wm21ltd.wm21.presenters.RewardFundPresenter;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardFundFragment extends Fragment implements OnRewardFundView {

    View mView;
    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    RewardFundPresenter rewardFundPresenter;

    @BindView(R.id.rv_rewardFund)
    RecyclerView recyclerViewRewardFund;

    private List<RewardFundDataListModel> rewardModel = new ArrayList<>();
    private RewardFundAdapter rAdapter;
    MaterialDialog dialog;

    public RewardFundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reward_fund, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();
        rewardFundPresenter = new RewardFundPresenter(this);
        initializedFields();
        return mView;
    }

    private void initializedFields() {
        rAdapter = new RewardFundAdapter(rewardModel, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewRewardFund.setAdapter(rAdapter);
        recyclerViewRewardFund.setLayoutManager(layoutManager);
        parseData();
    }

    private void parseData() {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            rewardFundPresenter.onRewardFundRequestData(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID));
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardFundData(List<RewardFundDataListModel> rewardList) {
        rewardModel.clear();
        rewardModel.addAll(rewardList);
        rAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRewardFundStartLoading() {
        dialog.show();
    }

    @Override
    public void onRewardFundStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onRewardFundShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
