package com.wm21ltd.wm21.interfaces;

import com.wm21ltd.wm21.networks.Models.RewardPolicyDataListModel;

import java.util.List;

public interface OnRewardPolicyView {
    void onRewardPolicyData(List<RewardPolicyDataListModel> rewardModel);

    void onRewardPolicyStartLoading();

    void onRewardPolicyStopLoading();

    void onRewardPolicyShowMessage(String errMsg);
}
