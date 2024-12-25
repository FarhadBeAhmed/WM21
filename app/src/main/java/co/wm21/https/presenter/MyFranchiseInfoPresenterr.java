package co.wm21.https.presenter;


import java.util.HashMap;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.presenter.interfaces.OnMyFranchiseView;
import co.wm21.https.serviceapis.InvokeMyFranchiseInfoApi;

public class MyFranchiseInfoPresenterr {
    OnMyFranchiseView mView;

    public MyFranchiseInfoPresenterr(OnMyFranchiseView mView) {
        this.mView = mView;
    }

    public void onMyFranchiseInfoResponseData(String userID) {
        mView.startLoading();
        new InvokeMyFranchiseInfoApi(userID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.stopLoading();
                mView.onResponseData((HashMap) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.stopLoading();
                mView.showMessage(errMsg);
            }
        });
    }
}
