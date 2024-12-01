package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.AppliedProductModelHead;
import co.wm21.https.presenter.interfaces.OnAppliedProductsView;
import co.wm21.https.serviceapis.InvokeAppliedProductsApi;

public class AppliedProductsPresenter {
    OnAppliedProductsView productsView;

    public AppliedProductsPresenter(OnAppliedProductsView productsView) {
        this.productsView = productsView;
    }

    public void appliedProductsDataLoad(String user_id) {
        productsView.onAppliedProductsStartLoading();
        new InvokeAppliedProductsApi(user_id, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                productsView.onAppliedProductsStopLoading();
                productsView.onAppliedProductsDataLoad((AppliedProductModelHead) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                productsView.onAppliedProductsStopLoading();
                productsView.onAppliedProductsShowMessage(errMsg);
            }
        });
    }
}
