package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.presenter.interfaces.OnCartItemListView;
import co.wm21.https.serviceapis.InvokeCartItemListApi;

public class CartItemListPresenter {
    OnCartItemListView cartItemListView;

    public CartItemListPresenter(OnCartItemListView cartItemListView) {
        this.cartItemListView = cartItemListView;
    }

    public void CartItemDataLoad(String deviceID) {
        cartItemListView.onCartItemListStartLoading();
        new InvokeCartItemListApi(deviceID, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                cartItemListView.onCartItemListStopLoading();
                cartItemListView.onCartItemListDataLoad((CartItemsHead) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                cartItemListView.onCartItemListStopLoading();
                cartItemListView.onCartItemListShowMessage(errMsg);
            }
        });
    }
}
