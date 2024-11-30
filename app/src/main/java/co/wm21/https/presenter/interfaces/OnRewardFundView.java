package co.wm21.https.presenter.interfaces;


import java.util.List;

import co.wm21.https.FHelper.networks.Models.RewardFundDataListModel;

public interface OnRewardFundView {
    void onRewardFundData(List<RewardFundDataListModel> rewardList);

    void onRewardFundStartLoading();

    void onRewardFundStopLoading();

    void onRewardFundShowMessage(String errMsg);
}
