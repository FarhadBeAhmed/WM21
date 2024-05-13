package com.wm21ltd.wm21.interfaces;

public interface OnRewardPolicyRequestComplete {
    void onRewardPolicyRequestSuccess(Object obj);

    void onRewardPolicyRequestError(String errMsg);
}
