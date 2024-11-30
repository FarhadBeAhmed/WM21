package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.LocationModel;
import co.wm21.https.presenter.interfaces.OnLocationListRequestComplete;
import co.wm21.https.presenter.interfaces.OnLocationListView;
import co.wm21.https.serviceapis.InvokeLocationListApi;

public class LocationListPresenter {
    OnLocationListView locationListView;

    public LocationListPresenter(OnLocationListView locationListView) {
        this.locationListView = locationListView;
    }

    public void locationDataLoad(String id,String value) {
        locationListView.onLocationListStartLoading();
        new InvokeLocationListApi(id,value, new OnLocationListRequestComplete() {
            @Override
            public void onLocationListRequestComplete(Object obj) {
                locationListView.onLocationListStopLoading();
                locationListView.onLocationListDataLoad((List<LocationModel>) obj);
            }

            @Override
            public void onLocationListRequestError(String errMsg) {
                locationListView.onLocationListStopLoading();
                locationListView.onLocationListShowMessage(errMsg);
            }
        });
    }
}
