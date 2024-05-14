package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.DeliveryReceiveModel;

public interface OnDeliveryReceiveView {
    void onDeliveryReceiveDataLoad(DeliveryReceiveModel deliveryReceiveModel);

    void onDeliveryReceiveStartLoading();

    void onDeliveryReceiveStopLoading();

    void onDeliveryReceiveShowMessage(String errmsg);
}
