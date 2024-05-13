package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnFranchiseApplicationRequestComplete;
import com.wm21ltd.wm21.interfaces.OnFranchiseApplicationView;
import com.wm21ltd.wm21.serviceapis.InvokeFranchiseApplicationApi;

import java.util.HashMap;

public class FranchiseApplicationPresenter {
    OnFranchiseApplicationView mView;

    public FranchiseApplicationPresenter(OnFranchiseApplicationView mView) {
        this.mView = mView;
    }

    public void onFranchiseApplicationResponseData(String serviceID, String userID, String formType, String divisionID,
                                                   String districtID, String thanaID, String applicantName, String applicantAddress,
                                                   String applicantLicense, String category){
        mView.onFranchiseApplicationStartLoading();
        new InvokeFranchiseApplicationApi(serviceID, userID, formType, divisionID, districtID, thanaID, applicantName, applicantAddress, applicantLicense, category, new OnFranchiseApplicationRequestComplete() {
            @Override
            public void onFranchiseApplicationRequestSuccess(Object obj) {
                mView.onFranchiseApplicationStopLoading();
                mView.onFranchiseApplicationData((HashMap) obj);
            }

            @Override
            public void onFranchiseApplicationRequestError(String errMsg) {
                mView.onFranchiseApplicationStopLoading();
                mView.onFranchiseApplicationShowMessage(errMsg);
            }
        });
    }
}
