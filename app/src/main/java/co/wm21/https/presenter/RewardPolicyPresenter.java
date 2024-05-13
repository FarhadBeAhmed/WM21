package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnRewardPolicyRequestComplete;
import com.wm21ltd.wm21.interfaces.OnRewardPolicyView;
import com.wm21ltd.wm21.networks.Models.RewardPolicyDataListModel;
import com.wm21ltd.wm21.serviceapis.InvokeRewardPolicyApi;

import java.util.List;

public class RewardPolicyPresenter {
    OnRewardPolicyView mView;

    public RewardPolicyPresenter(OnRewardPolicyView mView) {
        this.mView = mView;
    }

    public void onRewardPolicyRequestData(){
        mView.onRewardPolicyStartLoading();
        new InvokeRewardPolicyApi(new OnRewardPolicyRequestComplete() {
            @Override
            public void onRewardPolicyRequestSuccess(Object obj) {
                mView.onRewardPolicyStopLoading();
                mView.onRewardPolicyData((List<RewardPolicyDataListModel>) obj);
            }

            @Override
            public void onRewardPolicyRequestError(String errMsg) {
                mView.onRewardPolicyStopLoading();
                mView.onRewardPolicyShowMessage(errMsg);
            }
        });
    }
}
