package co.wm21.https.presenter.transaction;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.transaction.WithdrawalHistoryResponse;
import co.wm21.https.presenter.interfaces.transaction.OnWithdrawalHistoryView;
import co.wm21.https.serviceapis.InvokeWithdrawalHistoryApi;

public class WithdrawalHistoryPresenter {
    OnWithdrawalHistoryView onView;

    public WithdrawalHistoryPresenter(OnWithdrawalHistoryView onView) {
        this.onView = onView;
    }

    public void getDataLoad(String memberID) {
        onView.onStartLoading();
        new InvokeWithdrawalHistoryApi(memberID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onView.onStopLoading();
                onView.onWithdrawalHistoryDataLoad((WithdrawalHistoryResponse) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onView.onStopLoading();
                onView.onError(errMsg);
            }
        });
    }
}
