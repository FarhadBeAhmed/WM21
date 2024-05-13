package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnMemberDetailsRequestComplete;
import com.wm21ltd.wm21.interfaces.OnMemberDetailsView;
import com.wm21ltd.wm21.serviceapis.InvokeMemberDetailsApi;

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
