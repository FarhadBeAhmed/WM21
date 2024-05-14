package co.wm21.https.presenter;

import co.wm21.https.interfaces.OnMemberDetailsRequestComplete;
import co.wm21.https.interfaces.OnMemberDetailsView;
import co.wm21.https.serviceapis.InvokeMemberDetailsApi;

public class MemberDetailsPresenter {
    private OnMemberDetailsView onMemberDetailsView;

    public MemberDetailsPresenter(OnMemberDetailsView onMemberDetailsView) {
        this.onMemberDetailsView = onMemberDetailsView;
    }

    public void getMemberDetailsResponse (String userId, String rank, String limit){
        onMemberDetailsView.onStartLoading();
        new InvokeMemberDetailsApi(userId, rank, limit, new OnMemberDetailsRequestComplete() {
            @Override
            public void onMemberDetailsRequestSuccess(Object obj) {
                onMemberDetailsView.onResponseData(obj);
                onMemberDetailsView.onStopLoading();
            }

            @Override
            public void onMemberDetailsRequestError(String errMsg) {
                onMemberDetailsView.onStopLoading();
                onMemberDetailsView.onShowMessage(errMsg);
            }
        });

    }
}
