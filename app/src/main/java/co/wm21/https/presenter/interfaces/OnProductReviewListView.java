package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.ProductReviewModel;

public interface OnProductReviewListView {
    void onProductReviewListDataLoad(List<ProductReviewModel> productReviewModels);

    void onProductReviewListStartLoading();

    void onProductReviewListStopLoading();

    void onProductReviewListShowMessage(String errmsg);
}
