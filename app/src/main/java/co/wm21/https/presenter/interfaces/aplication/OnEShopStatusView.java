package co.wm21.https.presenter.interfaces.aplication;

import co.wm21.https.FHelper.networks.Models.application.EShopStatusResponse;
import co.wm21.https.FHelper.networks.Models.transaction.WithdrawalHistoryResponse;

public interface OnEShopStatusView {
    void onEShopStatusDataLoad(EShopStatusResponse response);

    void onStartLoading();

    void onStopLoading();

    void onError(String errmsg);
}
