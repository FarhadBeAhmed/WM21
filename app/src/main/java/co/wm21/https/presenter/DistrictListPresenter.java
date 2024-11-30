package co.wm21.https.presenter;

import com.google.gson.JsonArray;

import co.wm21.https.presenter.interfaces.OnDistrictListRequestComplete;
import co.wm21.https.presenter.interfaces.OnDistrictListView;
import co.wm21.https.serviceapis.InvokeDistrictListApi;

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
