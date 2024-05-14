package co.wm21.https.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.adapters.category.CategoryView;
import co.wm21.https.adapters.product.ProductView;

public interface OnHomePopularProductView {
    void onHomePopularProductLoaded(List<ProductModel> productViews);

    void onHomePopularProductStartLoading();

    void onHomePopularProductStopLoading();

    void onHomePopularProductShowMessage(String errMsg);
}
