package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.DeliveryItemsModel;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.interfaces.OnDeliveryItemsRequestComplete;
import co.wm21.https.interfaces.OnDeliveryItemsView;
import co.wm21.https.serviceapis.InvokeBlogListApi;
import co.wm21.https.serviceapis.InvokeDeliveryItemsApi;

public class DeliveryItemsPresenter {
    OnDeliveryItemsView deliveryItemsView;

    public DeliveryItemsPresenter(OnDeliveryItemsView deliveryItemsView) {
        this.deliveryItemsView = deliveryItemsView;
    }

    public void deliveryItemsDataLoad(String user_id) {
        deliveryItemsView.onDeliveryItemsStartLoading();
        new InvokeDeliveryItemsApi(user_id, new OnDeliveryItemsRequestComplete() {
            @Override
            public void onDeliveryItemsRequestComplete(Object obj) {
                deliveryItemsView.onDeliveryItemsStopLoading();
                deliveryItemsView.onDeliveryItemsDataLoad((List<DeliveryItemsModel>) obj);
            }

            @Override
            public void onDeliveryItemsRequestError(String errMsg) {
                deliveryItemsView.onDeliveryItemsStopLoading();
                deliveryItemsView.onDeliveryItemsShowMessage(errMsg);
            }
        });
    }
}
