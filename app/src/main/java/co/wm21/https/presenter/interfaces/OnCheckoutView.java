package co.wm21.https.presenter.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.CheckoutModel;

public interface OnCheckoutView {
    void onCheckoutDataLoad(CheckoutModel checkoutModel);

    void onCheckoutStartLoading();

    void onCheckoutStopLoading();

    void onCheckoutShowMessage(String errmsg);
}
