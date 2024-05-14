package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.ReceivedItemsModelHead;

public interface OnReceivedItemsView {
    void onReceivedItemsDataLoad(ReceivedItemsModelHead receivedItemsModelHead);

    void onReceivedItemsStartLoading();

    void onReceivedItemsStopLoading();

    void onReceivedItemsShowMessage(String errmsg);
}
