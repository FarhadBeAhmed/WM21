package co.wm21.https.presenter;




import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataListModel;
import co.wm21.https.presenter.interfaces.OnRewardAchievementView;
import co.wm21.https.serviceapis.InvokeRewardAchievementApi;

public class RewardAchievementPresenter {
    OnRewardAchievementView mView;

    public RewardAchievementPresenter(OnRewardAchievementView mView) {
        this.mView = mView;
    }

    public void onRewardAchievementResponseData(String userID){
        mView.onRewardAchievementStartLoading();
        new InvokeRewardAchievementApi(userID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.onRewardAchievementStopLoading();
                mView.onRewardAchievementData((List<RewardAchievementDataListModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.onRewardAchievementStopLoading();
                mView.onRewardAchievementShowMessage(errMsg);
            }
        });
    }
}
