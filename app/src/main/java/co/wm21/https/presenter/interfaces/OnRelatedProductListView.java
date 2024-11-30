package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.FHelper.networks.Models.RelatedProductModelHead;

public interface OnRelatedProductListView {
    void onRelatedProductListDataLoad(List<ProductModel> relatedProductModelHeads);

    void onRelatedProductListStartLoading();

    void onRelatedProductListStopLoading();

    void onRelatedProductListShowMessage(String errmsg);
}
