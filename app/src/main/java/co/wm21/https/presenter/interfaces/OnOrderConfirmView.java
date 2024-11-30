package co.wm21.https.presenter.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.OrderConfirmModel;

public interface OnOrderConfirmView {
    void onOrderConfirmDataLoad(OrderConfirmModel orderConfirmModel);

    void onOrderConfirmStartLoading();

    void onOrderConfirmStopLoading();

    void onOrderConfirmShowMessage(String errmsg);
}
