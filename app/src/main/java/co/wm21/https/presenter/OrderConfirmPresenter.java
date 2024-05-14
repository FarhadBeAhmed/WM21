package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.OrderConfirmModel;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnAddToCartView;
import co.wm21.https.interfaces.OnOrderConfirmRequestComplete;
import co.wm21.https.interfaces.OnOrderConfirmView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;
import co.wm21.https.serviceapis.InvokeOrderConfirmApi;

public class OrderConfirmPresenter {
    OnOrderConfirmView onOrderConfirmView;

    public OrderConfirmPresenter(OnOrderConfirmView onOrderConfirmView) {
        this.onOrderConfirmView = onOrderConfirmView;
    }

    public void orderConfirmDataLoad(String userId, String eshop,String shipping,String address,int adjust) {
        onOrderConfirmView.onOrderConfirmStartLoading();
        new InvokeOrderConfirmApi(userId,eshop,shipping,address,adjust, new OnOrderConfirmRequestComplete() {
            @Override
            public void onOrderConfirmRequestComplete(Object obj) {
                onOrderConfirmView.onOrderConfirmStopLoading();
                onOrderConfirmView.onOrderConfirmDataLoad((OrderConfirmModel) obj);
            }

            @Override
            public void onOrderConfirmRequestError(String errMsg) {
                onOrderConfirmView.onOrderConfirmStopLoading();
                onOrderConfirmView.onOrderConfirmShowMessage(errMsg);
            }
        });
    }
}
