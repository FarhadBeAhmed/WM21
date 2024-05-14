package co.wm21.https.presenter;


import java.util.HashMap;

import co.wm21.https.interfaces.OnMyFranchiseRequestComplete;
import co.wm21.https.interfaces.OnMyFranchiseView;
import co.wm21.https.serviceapis.InvokeMyFranchiseInfoApi;

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
