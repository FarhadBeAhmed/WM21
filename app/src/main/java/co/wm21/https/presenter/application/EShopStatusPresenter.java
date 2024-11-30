package co.wm21.https.presenter.application;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.application.EShopStatusResponse;
import co.wm21.https.presenter.interfaces.aplication.OnEShopStatusView;
import co.wm21.https.serviceapis.InvokeEShopStatusApi;

public class EShopStatusPresenter {
    OnEShopStatusView onView;

    public EShopStatusPresenter(OnEShopStatusView onView) {
        this.onView = onView;
    }

    public void getDataLoad(String memberID) {
        onView.onStartLoading();
        new InvokeEShopStatusApi(memberID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onView.onStopLoading();
                onView.onEShopStatusDataLoad((EShopStatusResponse) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onView.onStopLoading();
                onView.onError(errMsg);
            }
        });
    }
}
