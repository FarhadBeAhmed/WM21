package com.wm21ltd.wm21.interfaces;

public interface OnDistrictListRequestComplete {
    void onDistrictListRequestSuccess(Object obj);

    void onDistrictListRequestError(String errMsg);
}
