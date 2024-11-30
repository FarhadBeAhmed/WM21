package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.ShopTypeModel;
import co.wm21.https.presenter.interfaces.OnShopTypeRequestComplete;
import co.wm21.https.presenter.interfaces.OnShopTypeView;
import co.wm21.https.serviceapis.InvokeShopTypeApi;

public class ShopTypePresenter {
    OnShopTypeView shopTypeView;

    public ShopTypePresenter(OnShopTypeView shopTypeView) {
        this.shopTypeView = shopTypeView;
    }

    public void shopTypeDataLoad(String userId) {
        shopTypeView.onShopTypeStartLoading();
        new InvokeShopTypeApi(userId, new OnShopTypeRequestComplete() {
            @Override
            public void onShopTypeRequestComplete(Object obj) {
                shopTypeView.onShopTypeStopLoading();
                shopTypeView.onShopTypeDataLoad((ShopTypeModel) obj);
            }

            @Override
            public void onShopTypeRequestError(String errMsg) {
                shopTypeView.onShopTypeStopLoading();
                shopTypeView.onShopTypeShowMessage(errMsg);
            }
        });
    }
}
