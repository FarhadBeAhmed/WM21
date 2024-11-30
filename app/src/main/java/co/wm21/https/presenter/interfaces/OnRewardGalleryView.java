package co.wm21.https.presenter.interfaces;



import java.util.List;

import co.wm21.https.FHelper.networks.Models.RewardGalleryDataListModel;

public interface OnRewardGalleryView {
    void onRewardGalleryData(List<RewardGalleryDataListModel> rewardGalleryDataListModels);

    void onRewardGalleryStartLoading();

    void onRewardGalleryStopLoading();

    void onRewardGalleryShowMessage(String errMsg);
}
