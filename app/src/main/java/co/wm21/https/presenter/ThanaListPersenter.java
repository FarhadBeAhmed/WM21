package co.wm21.https.presenter;

import com.google.gson.JsonArray;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.presenter.interfaces.OnThanaListView;
import co.wm21.https.serviceapis.InvokeThanaListApi;

public class ThanaListPersenter {
    OnThanaListView mView;

    public ThanaListPersenter(OnThanaListView mView) {
        this.mView = mView;
    }

    public void onThanaResponseData(String countryID, String divisionID, String districtID){
        mView.onThanaListStartLoading();
        new InvokeThanaListApi(countryID, divisionID, districtID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.onThanaListStopLoading();
                mView.onThanaListData((JsonArray) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.onThanaListStopLoading();
                mView.onThanaListShowMessage(errMsg);
            }
        });
    }
}
