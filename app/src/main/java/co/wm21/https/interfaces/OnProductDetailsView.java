package co.wm21.https.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.ProductDetails;

public interface OnProductDetailsView {
    void onProductDetailsDataLoad(ProductDetails productDetails);

    void onProductDetailsStartLoading();

    void onProductDetailsStopLoading();

    void onProductDetailsShowMessage(String errmsg);
}
