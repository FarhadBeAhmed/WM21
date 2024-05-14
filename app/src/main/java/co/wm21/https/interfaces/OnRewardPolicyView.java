package co.wm21.https.interfaces;


import java.util.List;

import co.wm21.https.fragments.member.model.RewardPolicyDataListModel;

public interface OnRewardPolicyView {
    void onRewardPolicyData(List<RewardPolicyDataListModel> rewardModel);

    void onRewardPolicyStartLoading();

    void onRewardPolicyStopLoading();

    void onRewardPolicyShowMessage(String errMsg);
}
