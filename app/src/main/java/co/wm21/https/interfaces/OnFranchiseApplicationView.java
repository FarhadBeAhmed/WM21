package com.wm21ltd.wm21.interfaces;

import java.util.HashMap;

public interface OnFranchiseApplicationView {
    void onFranchiseApplicationData(HashMap hashMap);

    void onFranchiseApplicationStartLoading();

    void onFranchiseApplicationStopLoading();

    void onFranchiseApplicationShowMessage(String errMsg);
}
