package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductReviewModel;
import co.wm21.https.presenter.interfaces.OnProductReviewListRequestComplete;
import co.wm21.https.presenter.interfaces.OnProductReviewListView;
import co.wm21.https.serviceapis.InvokeProductReviewListApi;

public class ProductReviewListPresenter {
    OnProductReviewListView productReviewListView;

    public ProductReviewListPresenter(OnProductReviewListView productReviewListView) {
        this.productReviewListView = productReviewListView;
    }

    public void ProductReviewDataLoad(String id) {
        productReviewListView.onProductReviewListStartLoading();
        new InvokeProductReviewListApi(id, new OnProductReviewListRequestComplete() {
            @Override
            public void onProductReviewListRequestComplete(Object obj) {
                productReviewListView.onProductReviewListStopLoading();
                productReviewListView.onProductReviewListDataLoad((List<ProductReviewModel>) obj);
            }

            @Override
            public void onProductReviewListRequestError(String errMsg) {
                productReviewListView.onProductReviewListStopLoading();
                productReviewListView.onProductReviewListShowMessage(errMsg);
            }
        });
    }
}
