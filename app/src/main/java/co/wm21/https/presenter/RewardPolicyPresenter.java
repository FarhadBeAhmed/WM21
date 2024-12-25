package co.wm21.https.presenter;



import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.view.fragments.member.model.RewardPolicyResponse;
import co.wm21.https.presenter.interfaces.OnRewardPolicyView;
import co.wm21.https.serviceapis.InvokeRewardPolicyApi;

public class RewardPolicyPresenter {
    OnRewardPolicyView mView;

    public RewardPolicyPresenter(OnRewardPolicyView mView) {
        this.mView = mView;
    }

    public void onRewardPolicyRequestData(String user_id){
        mView.onRewardPolicyStartLoading();
        new InvokeRewardPolicyApi(user_id,new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.onRewardPolicyStopLoading();
                mView.onRewardPolicyData((RewardPolicyResponse) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.onRewardPolicyStopLoading();
                mView.onRewardPolicyShowMessage(errMsg);
            }
        });
    }
}
