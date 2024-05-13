package com.wm21ltd.wm21.presenters;

import com.google.gson.JsonArray;
import com.wm21ltd.wm21.interfaces.OnThanaListRequestComplete;
import com.wm21ltd.wm21.interfaces.OnThanaListView;
import com.wm21ltd.wm21.serviceapis.InvokeThanaListApi;

public class ThanaListPersenter {
    OnThanaListView mView;

    public ThanaListPersenter(OnThanaListView mView) {
        this.mView = mView;
    }

    public void onThanaResponseData(String countryID, String divisionID, String districtID){
        mView.onThanaListStartLoading();
        new InvokeThanaListApi(countryID, divisionID, districtID, new OnThanaListRequestComplete() {
            @Override
            public void onThanaListRequestSuccess(Object obj) {
                mView.onThanaListStopLoading();
                mView.onThanaListData((JsonArray) obj);
            }

            @Override
            public void onThanaListRequestError(String errMsg) {
                mView.onThanaListStopLoading();
                mView.onThanaListShowMessage(errMsg);
            }
        });
    }
}
