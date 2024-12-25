package co.wm21.https.presenter;


import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.ProductDetails;
import co.wm21.https.presenter.interfaces.OnProductDetailsView;
import co.wm21.https.serviceapis.InvokeProductDetailsApi;

public class ProductDetailsPresenter {
    OnProductDetailsView productDetailsView;

    public ProductDetailsPresenter(OnProductDetailsView productDetailsView) {
        this.productDetailsView = productDetailsView;
    }

    public void onProductDetailsRequestData(String id){
        productDetailsView.onProductDetailsStartLoading();
        new InvokeProductDetailsApi(id,new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                productDetailsView.onProductDetailsStopLoading();
                productDetailsView.onProductDetailsDataLoad((ProductDetails) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                productDetailsView.onProductDetailsStopLoading();
                productDetailsView.onProductDetailsShowMessage(errMsg);
            }
        });
    }
}
