package co.wm21.https.presenter;



import java.util.List;

import co.wm21.https.fragments.member.model.RewardPolicyDataListModel;
import co.wm21.https.interfaces.OnRewardPolicyRequestComplete;
import co.wm21.https.interfaces.OnRewardPolicyView;
import co.wm21.https.serviceapis.InvokeRewardPolicyApi;

public class RewardPolicyPresenter {
    OnRewardPolicyView mView;

    public RewardPolicyPresenter(OnRewardPolicyView mView) {
        this.mView = mView;
    }

    public void onRewardPolicyRequestData(String user_id){
        mView.onRewardPolicyStartLoading();
        new InvokeRewardPolicyApi(user_id,new OnRewardPolicyRequestComplete() {
            @Override
            public void onRewardPolicyRequestSuccess(Object obj) {
                mView.onRewardPolicyStopLoading();
                mView.onRewardPolicyData((List<RewardPolicyDataListModel>) obj);
            }

            @Override
            public void onRewardPolicyRequestError(String errMsg) {
                mView.onRewardPolicyStopLoading();
                mView.onRewardPolicyShowMessage(errMsg);
            }
        });
    }
}
