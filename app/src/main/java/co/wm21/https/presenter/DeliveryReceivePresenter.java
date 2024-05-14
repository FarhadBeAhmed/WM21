package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.DeliveryReceiveModel;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnAddToCartView;
import co.wm21.https.interfaces.OnDeliveryReceiveRequestComplete;
import co.wm21.https.interfaces.OnDeliveryReceiveView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;
import co.wm21.https.serviceapis.InvokeDeliveryReceiveApi;

public class DeliveryReceivePresenter {
    OnDeliveryReceiveView deliveryReceiveView;

    public DeliveryReceivePresenter(OnDeliveryReceiveView deliveryReceiveView) {
        this.deliveryReceiveView = deliveryReceiveView;
    }

    public void deliveryReceiveDataLoad(String userId,String pin,String action,String serial) {
        deliveryReceiveView.onDeliveryReceiveStartLoading();
        new InvokeDeliveryReceiveApi(userId, pin, action, serial, new OnDeliveryReceiveRequestComplete() {
            @Override
            public void onDeliveryReceiveRequestComplete(Object obj) {
                deliveryReceiveView.onDeliveryReceiveStopLoading();
                deliveryReceiveView.onDeliveryReceiveDataLoad((DeliveryReceiveModel) obj);
            }

            @Override
            public void onDeliveryReceiveRequestError(String errMsg) {
                deliveryReceiveView.onDeliveryReceiveStopLoading();
                deliveryReceiveView.onDeliveryReceiveShowMessage(errMsg);
            }
        });
    }
}
