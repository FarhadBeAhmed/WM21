package com.wm21ltd.wm21.interfaces;

import com.google.gson.JsonArray;

public interface OnDivisionListView {
    void onDivisionListDataLoad(JsonArray jsonArray);

    void onDivisionListStartLoading();

    void onDivisionListStopLoading();

    void onDivisionListShowMessage(String errmsg);
}
