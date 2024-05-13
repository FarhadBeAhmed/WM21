package com.wm21ltd.wm21.interfaces;

import com.wm21ltd.wm21.networks.Models.RewardFundDataListModel;

import java.util.List;

public interface OnRewardFundView {
    void onRewardFundData(List<RewardFundDataListModel> rewardList);

    void onRewardFundStartLoading();

    void onRewardFundStopLoading();

    void onRewardFundShowMessage(String errMsg);
}
