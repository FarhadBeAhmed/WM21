package co.wm21.https.interfaces;

public interface OnRewardAchievementRequestComplete {
    void onRewardAchievementRequestSuccess(Object obj);

    void onRewardAchievementRequestError(String errMsg);
}
