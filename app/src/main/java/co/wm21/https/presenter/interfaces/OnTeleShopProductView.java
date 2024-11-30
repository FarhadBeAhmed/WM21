package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;

public interface OnTeleShopProductView {
    void onTeleShopProductDataLoaded(List<ProductModel> teleShopProducts);

    void onTeleShopProductStartLoading();

    void onTeleShopProductStopLoading();

    void onTeleShopProductShowMessage(String errMsg);
}
