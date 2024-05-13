package co.wm21.https.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.adapters.category.CategoryView;

public interface OnHotProductView {
    void onHotProductDataLoaded(List<ProductModel> sliderItem);

    void onHotProductStartLoading();

    void onHotProductStopLoading();

    void onHotProductShowMessage(String errMsg);
}
