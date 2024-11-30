package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.OrderItemModelHead;
import co.wm21.https.presenter.interfaces.OnOrderItemListRequestComplete;
import co.wm21.https.presenter.interfaces.OnOrderItemListView;
import co.wm21.https.serviceapis.InvokeOrderItemListApi;

public class OrderItemListPresenter {
    OnOrderItemListView orderItemListView;

    public OrderItemListPresenter(OnOrderItemListView orderItemListView) {
        this.orderItemListView = orderItemListView;
    }

    public void orderItemDataLoad(String user_id) {
        orderItemListView.onOrderItemListStartLoading();
        new InvokeOrderItemListApi(user_id, new OnOrderItemListRequestComplete() {
            @Override
            public void onOrderItemListRequestComplete(Object obj) {
                orderItemListView.onOrderItemListStopLoading();
                orderItemListView.onOrderItemListDataLoad((OrderItemModelHead) obj);
            }

            @Override
            public void onOrderItemListRequestError(String errMsg) {
                orderItemListView.onOrderItemListStopLoading();
                orderItemListView.onOrderItemListShowMessage(errMsg);
            }
        });
    }
}
