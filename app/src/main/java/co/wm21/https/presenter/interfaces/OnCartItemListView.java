package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.CartItems;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;

public interface OnCartItemListView {
    void onCartItemListDataLoad(CartItemsHead cartItems);

    void onCartItemListStartLoading();

    void onCartItemListStopLoading();

    void onCartItemListShowMessage(String errmsg);
}
