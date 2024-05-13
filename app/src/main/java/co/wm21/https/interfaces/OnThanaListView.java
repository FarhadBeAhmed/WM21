package com.wm21ltd.wm21.interfaces;

import com.google.gson.JsonArray;

public interface OnThanaListView {
    void onThanaListData(JsonArray jsonArrayThana);

    void onThanaListStartLoading();

    void onThanaListStopLoading();

    void onThanaListShowMessage(String errMsg);
}
