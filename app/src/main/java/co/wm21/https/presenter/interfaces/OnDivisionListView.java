package co.wm21.https.presenter.interfaces;

import com.google.gson.JsonArray;

public interface OnDivisionListView {
    void onDivisionListDataLoad(JsonArray jsonArray);

    void onDivisionListStartLoading();

    void onDivisionListStopLoading();

    void onDivisionListShowMessage(String errmsg);
}
