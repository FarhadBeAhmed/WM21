package com.wm21ltd.wm21.interfaces;

import com.wm21ltd.wm21.networks.Models.RewardAchievementDataListModel;

import java.util.List;

public interface OnRewardAchievementView {
    void onRewardAchievementData(List<RewardAchievementDataListModel> achievementData);

    void onRewardAchievementStartLoading();

    void onRewardAchievementStopLoading();

    void onRewardAchievementShowMessage(String errMsg);
}
