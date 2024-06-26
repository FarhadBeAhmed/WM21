package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.CheckoutModel;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnAddToCartView;
import co.wm21.https.interfaces.OnCheckoutRequestComplete;
import co.wm21.https.interfaces.OnCheckoutView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;
import co.wm21.https.serviceapis.InvokeCheckoutApi;

public class CheckoutPresenter {
    OnCheckoutView checkoutView;

    public CheckoutPresenter(OnCheckoutView checkoutView) {
        this.checkoutView = checkoutView;
    }

    public void checkoutDataLoad(String deviceID,String userId) {
        checkoutView.onCheckoutStartLoading();
        new InvokeCheckoutApi(deviceID,userId, new OnCheckoutRequestComplete() {
            @Override
            public void onCheckoutRequestComplete(Object obj) {
                checkoutView.onCheckoutStopLoading();
                checkoutView.onCheckoutDataLoad((CheckoutModel) obj);
            }

            @Override
            public void onCheckoutRequestError(String errMsg) {
                checkoutView.onCheckoutStopLoading();
                checkoutView.onCheckoutShowMessage(errMsg);
            }
        });
    }
}
