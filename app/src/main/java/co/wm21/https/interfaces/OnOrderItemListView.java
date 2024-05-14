package co.wm21.https.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.OrderItemModelHead;

public interface OnOrderItemListView {
    void onOrderItemListDataLoad(OrderItemModelHead orderItemModelHeads);

    void onOrderItemListStartLoading();

    void onOrderItemListStopLoading();

    void onOrderItemListShowMessage(String errmsg);
}
