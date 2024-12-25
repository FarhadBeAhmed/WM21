package co.wm21.https.presenter;


import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.RewardGalleryDataListModel;
import co.wm21.https.presenter.interfaces.OnRewardGalleryView;
import co.wm21.https.serviceapis.InvokeRewardGalleryApi;

public class RewardGalleryPresenter {
    OnRewardGalleryView mView;

    public RewardGalleryPresenter(OnRewardGalleryView mView) {
        this.mView = mView;
    }

    public void onRewardGalleryDataResponse(String userID, String limitData){
        mView.onRewardGalleryStartLoading();

        new InvokeRewardGalleryApi(userID, limitData, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.onRewardGalleryStopLoading();
                mView.onRewardGalleryData((List<RewardGalleryDataListModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.onRewardGalleryStopLoading();
                mView.onRewardGalleryShowMessage(errMsg);
            }
        });
    }
}
