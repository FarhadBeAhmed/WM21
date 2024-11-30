package co.wm21.https.presenter.application;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse;
import co.wm21.https.FHelper.networks.Models.application.EShopRefComResponse;
import co.wm21.https.presenter.interfaces.aplication.OnBDPStatusView;
import co.wm21.https.presenter.interfaces.aplication.OnEShopRefComView;
import co.wm21.https.serviceapis.InvokeBDPStatusApi;

public class BDPStatusPresenter {
    OnBDPStatusView onView;

    public BDPStatusPresenter(OnBDPStatusView onView) {
        this.onView = onView;
    }

    public void getDataLoad(String memberID) {
        onView.onStartLoading();
        new InvokeBDPStatusApi(memberID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onView.onStopLoading();
                onView.onBDPStatusDataLoad((BDPStatusResponse) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onView.onStopLoading();
                onView.onError(errMsg);
            }
        });
    }
}
