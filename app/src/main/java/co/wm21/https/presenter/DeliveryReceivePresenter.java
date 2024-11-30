package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.DeliveryReceiveModel;
import co.wm21.https.presenter.interfaces.OnDeliveryReceiveRequestComplete;
import co.wm21.https.presenter.interfaces.OnDeliveryReceiveView;
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
