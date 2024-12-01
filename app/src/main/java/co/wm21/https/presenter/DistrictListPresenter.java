package co.wm21.https.presenter;

import com.google.gson.JsonArray;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.presenter.interfaces.OnDistrictListView;
import co.wm21.https.serviceapis.InvokeDistrictListApi;

public class DistrictListPresenter {
    OnDistrictListView mView;

    public DistrictListPresenter(OnDistrictListView mView) {
        this.mView = mView;
    }

    public void onDistrDataResponse(String divisionID, String countryID) {
        mView.onDistrictListStartLoading();
        new InvokeDistrictListApi(countryID, divisionID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.onDistrictListStopLoading();
                mView.onDistrictListDataLoad((JsonArray) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.onDistrictListStopLoading();
                mView.onDistrictListShowMessage(errMsg);
            }
        });
    }
}
