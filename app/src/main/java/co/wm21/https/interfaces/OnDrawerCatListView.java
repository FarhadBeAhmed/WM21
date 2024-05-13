package co.wm21.https.interfaces;

import com.google.gson.JsonArray;

public interface OnDivisionListView {
    void onDivisionListDataLoad(JsonArray jsonArray);

    void onDivisionListStartLoading();

    void onDivisionListStopLoading();

    void onDivisionListShowMessage(String errmsg);
}
