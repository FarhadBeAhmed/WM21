package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductModel;

public interface OnHomePopularCatProductView {
    void onHomePopularCatProductLoaded(List<ProductModel> productViews);

    void onHomePopularCatProductStartLoading();

    void onHomePopularCatProductStopLoading();

    void onHomePopularCatProductShowMessage(String errMsg);
}
