package co.wm21.https.presenter.interfaces;

public interface OnRewardPolicyRequestComplete {
    void onRewardPolicyRequestSuccess(Object obj);

    void onRewardPolicyRequestError(String errMsg);
}
