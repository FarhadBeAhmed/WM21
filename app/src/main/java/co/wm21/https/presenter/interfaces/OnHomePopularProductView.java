package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;

public interface OnHomePopularProductView {
    void onHomePopularProductLoaded(List<ProductModel> productViews);

    void onHomePopularProductStartLoading();

    void onHomePopularProductStopLoading();

    void onHomePopularProductShowMessage(String errMsg);
}
