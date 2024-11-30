package co.wm21.https.presenter.interfaces.aplication;

import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse;
import co.wm21.https.FHelper.networks.Models.application.EShopStatusResponse;

public interface OnBDPStatusView {
    void onBDPStatusDataLoad(BDPStatusResponse response);

    void onStartLoading();

    void onStopLoading();

    void onError(String errmsg);
}
