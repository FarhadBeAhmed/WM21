package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.ShopTypeModel;
import co.wm21.https.presenter.interfaces.OnShopTypeView;
import co.wm21.https.serviceapis.InvokeShopTypeApi;

public class ShopTypePresenter {
    OnShopTypeView shopTypeView;

    public ShopTypePresenter(OnShopTypeView shopTypeView) {
        this.shopTypeView = shopTypeView;
    }

    public void shopTypeDataLoad(String userId) {
        shopTypeView.onShopTypeStartLoading();
        new InvokeShopTypeApi(userId, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                shopTypeView.onShopTypeStopLoading();
                shopTypeView.onShopTypeDataLoad((ShopTypeModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                shopTypeView.onShopTypeStopLoading();
                shopTypeView.onShopTypeShowMessage(errMsg);
            }
        });
    }
}
