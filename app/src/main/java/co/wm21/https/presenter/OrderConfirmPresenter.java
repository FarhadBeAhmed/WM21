package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.OrderConfirmModel;
import co.wm21.https.presenter.interfaces.OnOrderConfirmView;
import co.wm21.https.serviceapis.InvokeOrderConfirmApi;

public class OrderConfirmPresenter {
    OnOrderConfirmView onOrderConfirmView;

    public OrderConfirmPresenter(OnOrderConfirmView onOrderConfirmView) {
        this.onOrderConfirmView = onOrderConfirmView;
    }

    public void orderConfirmDataLoad(String userId, String eshop,String shipping,String address,int adjust) {
        onOrderConfirmView.onOrderConfirmStartLoading();
        new InvokeOrderConfirmApi(userId,eshop,shipping,address,adjust, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onOrderConfirmView.onOrderConfirmStopLoading();
                onOrderConfirmView.onOrderConfirmDataLoad((OrderConfirmModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onOrderConfirmView.onOrderConfirmStopLoading();
                onOrderConfirmView.onOrderConfirmShowMessage(errMsg);
            }
        });
    }
}
