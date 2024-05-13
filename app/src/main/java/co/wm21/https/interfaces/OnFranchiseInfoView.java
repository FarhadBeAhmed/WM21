package com.wm21ltd.wm21.interfaces;

import java.util.HashMap;

public interface OnFranchiseInfoView {
    void onFranchiseInfoData(HashMap hashMap);

    void onFranchiseInfoStartLoading();

    void onFranchiseInfoStopLoading();

    void onFranchiseInfoShowMessage(String errMsg);
}
