package co.wm21.https.interfaces;

import com.google.gson.JsonArray;

public interface OnDistrictListView {
    void onDistrictListDataLoad(JsonArray jsonArray);

    void onDistrictListStartLoading();

    void onDistrictListStopLoading();

    void onDistrictListShowMessage(String errMsg);
}
