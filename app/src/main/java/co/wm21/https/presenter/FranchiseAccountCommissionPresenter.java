package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnFranchiseAccountRequestComplete;
import com.wm21ltd.wm21.interfaces.OnFranchiseAccountView;
import com.wm21ltd.wm21.networks.Models.FranchiseAccountDataListModel;
import com.wm21ltd.wm21.serviceapis.InvokeFranchiseAccountApi;

import java.util.List;

public class FranchiseAccountCommissionPresenter {
    OnFranchiseAccountView mView;

    public FranchiseAccountCommissionPresenter(OnFranchiseAccountView mView) {
        this.mView = mView;
    }

    public void onFranchiseAccountRequestData(String userID, String limitData){
        mView.onFranchiseAccountStartLoading();
        new InvokeFranchiseAccountApi(userID, limitData, new OnFranchiseAccountRequestComplete() {
            @Override
            public void onFranchiseAccountRequestSuccess(Object obj) {
                mView.onFranchiseAccountStopLoading();
                mView.onFranchiseAccountData((List<FranchiseAccountDataListModel>) obj);
            }

            @Override
            public void onFranchiseAccountRequestError(String errMsg) {
                mView.onFranchiseAccountStopLoading();
                mView.onFranchiseAccountShowMessage(errMsg);
            }
        });
    }
}
