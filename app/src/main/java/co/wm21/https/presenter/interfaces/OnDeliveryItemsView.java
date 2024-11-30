package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.DeliveryItemsModel;

public interface OnDeliveryItemsView {
    void onDeliveryItemsDataLoad(List<DeliveryItemsModel> deliveryItemsModels);

    void onDeliveryItemsStartLoading();

    void onDeliveryItemsStopLoading();

    void onDeliveryItemsShowMessage(String errmsg);
}
