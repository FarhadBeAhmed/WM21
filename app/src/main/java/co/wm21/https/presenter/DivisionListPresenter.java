package co.wm21.https.presenter;

import com.google.gson.JsonArray;

import co.wm21.https.presenter.interfaces.OnDivisionListRequestComplete;
import co.wm21.https.presenter.interfaces.OnDivisionListView;
import co.wm21.https.serviceapis.InvokeDivisionListApi;

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
