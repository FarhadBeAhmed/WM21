package co.wm21.https.presenter;

import com.google.gson.JsonArray;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.presenter.interfaces.OnDivisionListView;
import co.wm21.https.serviceapis.InvokeDivisionListApi;

public class DivisionListPresenter {
    OnDivisionListView mView;

    public DivisionListPresenter(OnDivisionListView mView) {
        this.mView = mView;
    }

    public void onDivisionDataLoad(String counrtyID) {
        mView.onDivisionListStartLoading();
        new InvokeDivisionListApi(counrtyID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.onDivisionListStopLoading();
                mView.onDivisionListDataLoad((JsonArray) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.onDivisionListStopLoading();
                mView.onDivisionListShowMessage(errMsg);
            }
        });
    }
}
