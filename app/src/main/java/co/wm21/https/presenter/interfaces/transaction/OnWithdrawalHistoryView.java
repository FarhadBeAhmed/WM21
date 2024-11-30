package co.wm21.https.presenter.interfaces.transaction;

import co.wm21.https.FHelper.networks.Models.transaction.WithdrawalHistoryResponse;

public interface OnWithdrawalHistoryView {
    void onWithdrawalHistoryDataLoad(WithdrawalHistoryResponse response);

    void onStartLoading();

    void onStopLoading();

    void onError(String errmsg);
}
