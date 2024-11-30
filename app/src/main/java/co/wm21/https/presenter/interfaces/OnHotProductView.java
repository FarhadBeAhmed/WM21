package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;

public interface OnHotProductView {
    void onHotProductDataLoaded(List<ProductModel> sliderItem);

    void onHotProductStartLoading();

    void onHotProductStopLoading();

    void onHotProductShowMessage(String errMsg);
}
