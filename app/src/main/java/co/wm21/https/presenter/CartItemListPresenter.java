package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.CartItems;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.interfaces.OnCartItemListRequestComplete;
import co.wm21.https.interfaces.OnCartItemListView;
import co.wm21.https.serviceapis.InvokeBlogListApi;
import co.wm21.https.serviceapis.InvokeCartItemListApi;

public class CartItemListPresenter {
    OnCartItemListView cartItemListView;

    public CartItemListPresenter(OnCartItemListView cartItemListView) {
        this.cartItemListView = cartItemListView;
    }

    public void CartItemDataLoad(String deviceID) {
        cartItemListView.onCartItemListStartLoading();
        new InvokeCartItemListApi(deviceID, new OnCartItemListRequestComplete() {
            @Override
            public void onCartItemListRequestComplete(Object obj) {
                cartItemListView.onCartItemListStopLoading();
                cartItemListView.onCartItemListDataLoad((CartItemsHead) obj);
            }

            @Override
            public void onCartItemListRequestError(String errMsg) {
                cartItemListView.onCartItemListStopLoading();
                cartItemListView.onCartItemListShowMessage(errMsg);
            }
        });
    }
}
