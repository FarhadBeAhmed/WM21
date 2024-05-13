package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnRewardFundRequestComplete;
import com.wm21ltd.wm21.interfaces.OnRewardFundView;
import com.wm21ltd.wm21.networks.Models.RewardFundDataListModel;
import com.wm21ltd.wm21.serviceapis.InvokeRewardFundApi;

import java.util.List;

public class RewardFundPresenter {
    OnRewardFundView mView;

    public RewardFundPresenter(OnRewardFundView mView) {
        this.mView = mView;
    }

    public void onRewardFundRequestData(String userID){
        mView.onRewardFundStartLoading();

        new InvokeRewardFundApi(userID, new OnRewardFundRequestComplete() {
            @Override
            public void onRewardFundRequestSuccess(Object obj) {
                mView.onRewardFundStopLoading();
                mView.onRewardFundData((List<RewardFundDataListModel>) obj);
            }

            @Override
            public void onRewardFundRequestError(String errMsg) {
                mView.onRewardFundStopLoading();
                mView.onRewardFundShowMessage(errMsg);
            }
        });
    }
}
