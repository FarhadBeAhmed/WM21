package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.LocationModel;

public interface OnLocationListView {
    void onLocationListDataLoad(List<LocationModel> locationModels);

    void onLocationListStartLoading();

    void onLocationListStopLoading();

    void onLocationListShowMessage(String errmsg);
}
