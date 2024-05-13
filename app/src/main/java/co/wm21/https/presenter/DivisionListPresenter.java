package com.wm21ltd.wm21.presenters;

import com.google.gson.JsonArray;
import com.wm21ltd.wm21.interfaces.OnDivisionListRequestComplete;
import com.wm21ltd.wm21.interfaces.OnDivisionListView;
import com.wm21ltd.wm21.serviceapis.InvokeDivisionListApi;

public class DivisionListPresenter {
    OnDivisionListView mView;

    public DivisionListPresenter(OnDivisionListView mView) {
        this.mView = mView;
    }

    public void onDivisionDataLoad(String counrtyID) {
        mView.onDivisionListStartLoading();
        new InvokeDivisionListApi(counrtyID, new OnDivisionListRequestComplete() {
            @Override
            public void onDivisionListRequestComplete(Object obj) {
                mView.onDivisionListStopLoading();
                mView.onDivisionListDataLoad((JsonArray) obj);
            }

            @Override
            public void onDevisionListRequestError(String errMsg) {
                mView.onDivisionListStopLoading();
                mView.onDivisionListShowMessage(errMsg);
            }
        });
    }
}
