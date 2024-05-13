package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnRewardAchievementRequestComplete;
import com.wm21ltd.wm21.interfaces.OnRewardAchievementView;
import com.wm21ltd.wm21.networks.Models.RewardAchievementDataListModel;
import com.wm21ltd.wm21.serviceapis.InvokeRewardAchievementApi;

import java.util.List;

public class RewardAchievementPresenter {
    OnRewardAchievementView mView;

    public RewardAchievementPresenter(OnRewardAchievementView mView) {
        this.mView = mView;
    }

    public void onRewardAchievementResponseData(String userID){
        mView.onRewardAchievementStartLoading();
        new InvokeRewardAchievementApi(userID, new OnRewardAchievementRequestComplete() {
            @Override
            public void onRewardAchievementRequestSuccess(Object obj) {
                mView.onRewardAchievementStopLoading();
                mView.onRewardAchievementData((List<RewardAchievementDataListModel>) obj);
            }

            @Override
            public void onRewardAchievementRequestError(String errMsg) {
                mView.onRewardAchievementStopLoading();
                mView.onRewardAchievementShowMessage(errMsg);
            }
        });
    }
}
