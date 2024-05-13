package com.wm21ltd.wm21.interfaces;

public interface OnRewardAchievementRequestComplete {
    void onRewardAchievementRequestSuccess(Object obj);

    void onRewardAchievementRequestError(String errMsg);
}
