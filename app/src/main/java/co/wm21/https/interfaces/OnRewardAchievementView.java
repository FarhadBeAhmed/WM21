package co.wm21.https.interfaces;



import java.util.List;

import co.wm21.https.FHelper.networks.Models.RewardAchievementDataListModel;

public interface OnRewardAchievementView {
    void onRewardAchievementData(List<RewardAchievementDataListModel> achievementData);

    void onRewardAchievementStartLoading();

    void onRewardAchievementStopLoading();

    void onRewardAchievementShowMessage(String errMsg);
}
