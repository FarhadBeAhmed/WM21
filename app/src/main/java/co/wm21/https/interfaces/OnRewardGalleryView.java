package com.wm21ltd.wm21.interfaces;

import com.wm21ltd.wm21.networks.Models.RewardGalleryDataListModel;

import java.util.List;

public interface OnRewardGalleryView {
    void onRewardGalleryData(List<RewardGalleryDataListModel> rewardGalleryDataListModels);

    void onRewardGalleryStartLoading();

    void onRewardGalleryStopLoading();

    void onRewardGalleryShowMessage(String errMsg);
}
