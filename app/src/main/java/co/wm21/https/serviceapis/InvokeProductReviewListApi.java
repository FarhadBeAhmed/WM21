package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.ProductReviewModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnProductReviewListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeProductReviewListApi {
    OnProductReviewListRequestComplete requestComplete;

    public InvokeProductReviewListApi(String id, final OnProductReviewListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getAllProductReview(id).enqueue(new Callback<ProductReviewModelHead>() {
            @Override
            public void onResponse(Call<ProductReviewModelHead> call, Response<ProductReviewModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onProductReviewListRequestComplete(response.body().getProductReviewModels());
                    } else {
                        requestComplete.onProductReviewListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onProductReviewListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<ProductReviewModelHead> call, Throwable t) {
                requestComplete.onProductReviewListRequestError("Something Went Wrong!");
            }
        });
    }
}
