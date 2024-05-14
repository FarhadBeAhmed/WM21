package co.wm21.https.presenter;



import java.util.List;

import co.wm21.https.FHelper.networks.Models.ProductDetails;
import co.wm21.https.fragments.member.model.RewardPolicyDataListModel;
import co.wm21.https.interfaces.OnProductDetailsRequestComplete;
import co.wm21.https.interfaces.OnProductDetailsView;
import co.wm21.https.interfaces.OnRewardPolicyRequestComplete;
import co.wm21.https.interfaces.OnRewardPolicyView;
import co.wm21.https.serviceapis.InvokeProductDetailsApi;
import co.wm21.https.serviceapis.InvokeRewardPolicyApi;

public class ProductDetailsPresenter {
    OnProductDetailsView productDetailsView;

    public ProductDetailsPresenter(OnProductDetailsView productDetailsView) {
        this.productDetailsView = productDetailsView;
    }

    public void onProductDetailsRequestData(String id){
        productDetailsView.onProductDetailsStartLoading();
        new InvokeProductDetailsApi(id,new OnProductDetailsRequestComplete() {
            @Override
            public void onProductDetailsRequestComplete(Object obj) {
                productDetailsView.onProductDetailsStopLoading();
                productDetailsView.onProductDetailsDataLoad((ProductDetails) obj);
            }

            @Override
            public void onProductDetailsRequestError(String errMsg) {
                productDetailsView.onProductDetailsStopLoading();
                productDetailsView.onProductDetailsShowMessage(errMsg);
            }
        });
    }
}
