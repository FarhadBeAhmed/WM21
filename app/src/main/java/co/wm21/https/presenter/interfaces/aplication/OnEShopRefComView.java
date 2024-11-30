package co.wm21.https.presenter.interfaces.aplication;

import co.wm21.https.FHelper.networks.Models.application.EShopRefComResponse;
import co.wm21.https.FHelper.networks.Models.application.EShopStatusResponse;

public interface OnEShopRefComView {
    void onEShopRefComDataLoad(EShopRefComResponse response);

    void onStartLoading();

    void onStopLoading();

    void onError(String errmsg);
}
