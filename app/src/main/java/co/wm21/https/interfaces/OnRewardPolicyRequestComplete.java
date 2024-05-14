package co.wm21.https.interfaces;

public interface OnRewardPolicyRequestComplete {
    void onRewardPolicyRequestSuccess(Object obj);

    void onRewardPolicyRequestError(String errMsg);
}
