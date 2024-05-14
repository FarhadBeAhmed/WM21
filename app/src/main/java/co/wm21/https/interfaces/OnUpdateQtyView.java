package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.UpdateQty;

public interface OnUpdateQtyView {
    void onUpdateQtyDataLoad(UpdateQty updateQty);

    void onUpdateQtyStartLoading();

    void onUpdateQtyStopLoading();

    void onUpdateQtyShowMessage(String errmsg);
}
