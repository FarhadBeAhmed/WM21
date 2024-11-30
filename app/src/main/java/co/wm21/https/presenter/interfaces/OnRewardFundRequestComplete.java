package co.wm21.https.presenter.interfaces;

public interface OnRewardFundRequestComplete {
    void onRewardFundRequestSuccess(Object obj);

    void onRewardFundRequestError(String errMsg);
}
