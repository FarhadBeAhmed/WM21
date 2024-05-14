package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.DeleteItem;

public interface OnDeleteItemView {
    void onDeleteItemDataLoad(DeleteItem deleteItem);

    void onDeleteItemStartLoading();

    void onDeleteItemStopLoading();

    void onDeleteItemShowMessage(String errmsg);
}
