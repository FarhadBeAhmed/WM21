package co.wm21.https.presenter;




import java.util.List;

import co.wm21.https.FHelper.networks.Models.RewardAchievementDataListModel;
import co.wm21.https.presenter.interfaces.OnRewardAchievementRequestComplete;
import co.wm21.https.presenter.interfaces.OnRewardAchievementView;
import co.wm21.https.serviceapis.InvokeRewardAchievementApi;

public class RewardAchievementPresenter {
    OnRewardAchievementView mView;

    public RewardAchievementPresenter(OnRewardAchievementView mView) {
        this.mView = mView;
    }

    public void onRewardAchievementResponseData(String userID){
        mView.onRewardAchievementStartLoading();
        new InvokeRewardAchievementApi(userID, new OnRewardAchievementRequestComplete() {
            @Override
            public void onRewardAchievementRequestSuccess(Object obj) {
                mView.onRewardAchievementStopLoading();
                mView.onRewardAchievementData((List<RewardAchievementDataListModel>) obj);
            }

            @Override
            public void onRewardAchievementRequestError(String errMsg) {
                mView.onRewardAchievementStopLoading();
                mView.onRewardAchievementShowMessage(errMsg);
            }
        });
    }
}
