package com.wm21ltd.wm21.interfaces;

import java.util.HashMap;

public interface OnMyFranchiseView {
    void onResponseData(HashMap hashMap);

    void startLoading();

    void stopLoading();

    void showMessage(String errMsg);
}
