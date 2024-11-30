package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.presenter.interfaces.OnTeleShopProductRequestComplete;
import co.wm21.https.presenter.interfaces.OnTeleShopProductView;
import co.wm21.https.serviceapis.InvokeTeleShopProductApi;

public class TeleShopProductPresenter {
    private OnTeleShopProductView onTeleShopProductView;

    public TeleShopProductPresenter(OnTeleShopProductView onTeleShopProductView) {
        this.onTeleShopProductView = onTeleShopProductView;
    }
    public void getTeleShopProduct(String value){
        onTeleShopProductView.onTeleShopProductStartLoading();
        new InvokeTeleShopProductApi(value, new OnTeleShopProductRequestComplete() {
            @Override
            public void onTeleShopProductSuccess(Object obj) {
                onTeleShopProductView.onTeleShopProductDataLoaded((List<ProductModel>)obj);
                onTeleShopProductView.onTeleShopProductStopLoading();
            }

            @Override
            public void onTeleShopProductError(String errMsg) {
                onTeleShopProductView.onTeleShopProductShowMessage(errMsg);
                onTeleShopProductView.onTeleShopProductStopLoading();
            }
        });

    }

}