package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.RewardFundDataListModel;
import co.wm21.https.presenter.interfaces.OnRewardFundView;
import co.wm21.https.serviceapis.InvokeRewardFundApi;

public class RewardFundPresenter {
    OnRewardFundView mView;

    public RewardFundPresenter(OnRewardFundView mView) {
        this.mView = mView;
    }

    public void onRewardFundRequestData(String userID){
        mView.onRewardFundStartLoading();

        new InvokeRewardFundApi(userID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.onRewardFundStopLoading();
                mView.onRewardFundData((List<RewardFundDataListModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.onRewardFundStopLoading();
                mView.onRewardFundShowMessage(errMsg);
            }
        });
    }
}
