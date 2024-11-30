package co.wm21.https.presenter.application;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.application.EShopRefComResponse;
import co.wm21.https.FHelper.networks.Models.application.EShopStatusResponse;
import co.wm21.https.presenter.interfaces.aplication.OnEShopRefComView;
import co.wm21.https.presenter.interfaces.aplication.OnEShopStatusView;
import co.wm21.https.serviceapis.InvokeEShopRefComApi;
import co.wm21.https.serviceapis.InvokeEShopStatusApi;

public class EShopRefComPresenter {
    OnEShopRefComView onView;

    public EShopRefComPresenter(OnEShopRefComView onView) {
        this.onView = onView;
    }

    public void getDataLoad(String memberID) {
        onView.onStartLoading();
        new InvokeEShopRefComApi(memberID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onView.onStopLoading();
                onView.onEShopRefComDataLoad((EShopRefComResponse) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onView.onStopLoading();
                onView.onError(errMsg);
            }
        });
    }
}
