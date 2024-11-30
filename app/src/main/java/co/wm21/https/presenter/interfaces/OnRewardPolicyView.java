package co.wm21.https.presenter.interfaces;


import co.wm21.https.view.fragments.member.model.RewardPolicyResponse;

public interface OnRewardPolicyView {
    void onRewardPolicyData(RewardPolicyResponse rewardModel);

    void onRewardPolicyStartLoading();

    void onRewardPolicyStopLoading();

    void onRewardPolicyShowMessage(String errMsg);
}
