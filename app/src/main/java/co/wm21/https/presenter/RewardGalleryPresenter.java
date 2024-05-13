package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnRewardGalleryRequestComplete;
import com.wm21ltd.wm21.interfaces.OnRewardGalleryView;
import com.wm21ltd.wm21.networks.Models.RewardGalleryDataListModel;
import com.wm21ltd.wm21.serviceapis.InvokeRewardGalleryApi;

import java.util.List;

public class RewardGalleryPresenter {
    OnRewardGalleryView mView;

    public RewardGalleryPresenter(OnRewardGalleryView mView) {
        this.mView = mView;
    }

    public void onRewardGalleryDataResponse(String userID, String limitData){
        mView.onRewardGalleryStartLoading();

        new InvokeRewardGalleryApi(userID, limitData, new OnRewardGalleryRequestComplete() {
            @Override
            public void onRewardGalleryRequestSuccess(Object obj) {
                mView.onRewardGalleryStopLoading();
                mView.onRewardGalleryData((List<RewardGalleryDataListModel>) obj);
            }

            @Override
            public void onRewardGalleryRequestError(String errMsg) {
                mView.onRewardGalleryStopLoading();
                mView.onRewardGalleryShowMessage(errMsg);
            }
        });
    }
}
