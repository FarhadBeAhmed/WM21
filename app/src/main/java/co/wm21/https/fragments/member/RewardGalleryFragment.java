package com.wm21ltd.wm21.fragments;


import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wm21ltd.wm21.R;
import com.wm21ltd.wm21.adapters.RewardGalleryApapter;
import com.wm21ltd.wm21.helpers.CheckInternetConnection;
import com.wm21ltd.wm21.interfaces.OnBottomReachedListener;
import com.wm21ltd.wm21.interfaces.OnRewardGalleryView;
import com.wm21ltd.wm21.networks.Models.RewardGalleryDataListModel;
import com.wm21ltd.wm21.presenters.RewardGalleryPresenter;
import com.wm21ltd.wm21.stores.AppSessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RewardGalleryFragment extends Fragment implements OnRewardGalleryView {

    View mView;
    AppSessionManager appSessionManager;
    CheckInternetConnection checkInternetConnection;
    RewardGalleryPresenter rewardGalleryPresenter;

    @BindView(R.id.rv_rewardGallery)
    RecyclerView recyclerViewGallery;
    private List<RewardGalleryDataListModel> rgList = new ArrayList<>();
    private RewardGalleryApapter rAdapter;
    MaterialDialog dialog;
    int loadMore = 0;


    public RewardGalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reward_gallery, container, false);
        ButterKnife.bind(this, mView);
        appSessionManager = new AppSessionManager(getActivity());
        checkInternetConnection = new CheckInternetConnection();
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();
        rewardGalleryPresenter = new RewardGalleryPresenter(this);
        initializedFields();
        return mView;
    }

    private void initializedFields() {
        rAdapter = new RewardGalleryApapter(rgList, getActivity());
        recyclerViewGallery.setAdapter(rAdapter);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        parseData(0);
        rAdapter.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                parseData(loadMore = loadMore + 10);
            }
        });
    }

    private void parseData(int loadMoreValue) {
        if (checkInternetConnection.isInternetAvailable(getActivity())) {
            rewardGalleryPresenter.onRewardGalleryDataResponse(appSessionManager.getUserDetails().get(AppSessionManager.KEY_USERID),
                    loadMoreValue + ",10");
        } else {
            Snackbar.make(getView(), "(*_*) Internet connection problem!", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRewardGalleryData(List<RewardGalleryDataListModel> rewardGalleryDataListModels) {
        rgList.addAll(rewardGalleryDataListModels);
        rAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRewardGalleryStartLoading() {
        dialog.show();
    }

    @Override
    public void onRewardGalleryStopLoading() {
        dialog.dismiss();
    }

    @Override
    public void onRewardGalleryShowMessage(String errMsg) {
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }
}
