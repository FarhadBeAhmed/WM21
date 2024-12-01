package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.DeliveryItemsModel;
import co.wm21.https.presenter.interfaces.OnDeliveryItemsView;
import co.wm21.https.serviceapis.InvokeDeliveryItemsApi;

public class DeliveryItemsPresenter {
    OnDeliveryItemsView deliveryItemsView;

    public DeliveryItemsPresenter(OnDeliveryItemsView deliveryItemsView) {
        this.deliveryItemsView = deliveryItemsView;
    }

    public void deliveryItemsDataLoad(String user_id) {
        deliveryItemsView.onDeliveryItemsStartLoading();
        new InvokeDeliveryItemsApi(user_id, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                deliveryItemsView.onDeliveryItemsStopLoading();
                deliveryItemsView.onDeliveryItemsDataLoad((List<DeliveryItemsModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                deliveryItemsView.onDeliveryItemsStopLoading();
                deliveryItemsView.onDeliveryItemsShowMessage(errMsg);
            }
        });
    }
}
