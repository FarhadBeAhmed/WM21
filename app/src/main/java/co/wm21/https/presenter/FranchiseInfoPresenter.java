package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnFranchiseInfoRequestComplete;
import com.wm21ltd.wm21.interfaces.OnFranchiseInfoView;
import com.wm21ltd.wm21.serviceapis.InvokeFranchiseInfoApi;

import java.util.HashMap;

public class FranchiseInfoPresenter {
    OnFranchiseInfoView mView;

    public FranchiseInfoPresenter(OnFranchiseInfoView mView) {
        this.mView = mView;
    }

    public void onFranchiseInfoDataResponse(String divisionID, String districtID, String thanaID){
        mView.onFranchiseInfoStartLoading();
        new InvokeFranchiseInfoApi(divisionID, districtID, thanaID, new OnFranchiseInfoRequestComplete() {
            @Override
            public void onFranchiseInfoRequestSuccess(Object obj) {
                mView.onFranchiseInfoStopLoading();
                mView.onFranchiseInfoData((HashMap) obj);
            }

            @Override
            public void onFranchiseInfoRequestError(String errMsg) {
                mView.onFranchiseInfoStopLoading();
                mView.onFranchiseInfoShowMessage(errMsg);
            }
        });
    }
}
