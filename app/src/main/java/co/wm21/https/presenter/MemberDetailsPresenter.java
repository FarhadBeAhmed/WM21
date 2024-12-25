package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.presenter.interfaces.OnMemberDetailsView;
import co.wm21.https.serviceapis.InvokeMemberDetailsApi;

public class MemberDetailsPresenter {
    private OnMemberDetailsView onMemberDetailsView;

    public MemberDetailsPresenter(OnMemberDetailsView onMemberDetailsView) {
        this.onMemberDetailsView = onMemberDetailsView;
    }

    public void getMemberDetailsResponse (String userId, String rank, String limit){
        onMemberDetailsView.onStartLoading();
        new InvokeMemberDetailsApi(userId, rank, limit, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onMemberDetailsView.onResponseData(obj);
                onMemberDetailsView.onStopLoading();
            }

            @Override
            public void onRequestError(String errMsg) {
                onMemberDetailsView.onStopLoading();
                onMemberDetailsView.onShowMessage(errMsg);
            }
        });

    }
}
