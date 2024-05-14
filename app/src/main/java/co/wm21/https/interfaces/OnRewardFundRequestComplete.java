package co.wm21.https.interfaces;

public interface OnRewardFundRequestComplete {
    void onRewardFundRequestSuccess(Object obj);

    void onRewardFundRequestError(String errMsg);
}
