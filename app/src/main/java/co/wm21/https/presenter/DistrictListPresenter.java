package com.wm21ltd.wm21.presenters;

import com.google.gson.JsonArray;
import com.wm21ltd.wm21.interfaces.OnDistrictListRequestComplete;
import com.wm21ltd.wm21.interfaces.OnDistrictListView;
import com.wm21ltd.wm21.serviceapis.InvokeDistrictListApi;

public class DistrictListPresenter {
    OnDistrictListView mView;

    public DistrictListPresenter(OnDistrictListView mView) {
        this.mView = mView;
    }

    public void onDistrDataResponse(String divisionID, String countryID) {
        mView.onDistrictListStartLoading();
        new InvokeDistrictListApi(countryID, divisionID, new OnDistrictListRequestComplete() {
            @Override
            public void onDistrictListRequestSuccess(Object obj) {
                mView.onDistrictListStopLoading();
                mView.onDistrictListDataLoad((JsonArray) obj);
            }

            @Override
            public void onDistrictListRequestError(String errMsg) {
                mView.onDistrictListStopLoading();
                mView.onDistrictListShowMessage(errMsg);
            }
        });
    }
}
