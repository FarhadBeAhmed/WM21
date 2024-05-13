package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnMyFranchiseRequestComplete;
import com.wm21ltd.wm21.interfaces.OnMyFranchiseView;
import com.wm21ltd.wm21.serviceapis.InvokeMyFranchiseInfoApi;

import java.util.HashMap;

public class MyFranchiseInfoPresenterr {
    OnMyFranchiseView mView;

    public MyFranchiseInfoPresenterr(OnMyFranchiseView mView) {
        this.mView = mView;
    }

    public void onMyFranchiseInfoResponseData(String userID) {
        mView.startLoading();
        new InvokeMyFranchiseInfoApi(userID, new OnMyFranchiseRequestComplete() {
            @Override
            public void onMyFranchiseRequestComplete(Object obj) {
                mView.stopLoading();
                mView.onResponseData((HashMap) obj);
            }

            @Override
            public void onMyFranchiseRequestError(String errMsg) {
                mView.stopLoading();
                mView.showMessage(errMsg);
            }
        });
    }
}
