package com.wm21ltd.wm21.interfaces;

public interface OnRewardFundRequestComplete {
    void onRewardFundRequestSuccess(Object obj);

    void onRewardFundRequestError(String errMsg);
}
