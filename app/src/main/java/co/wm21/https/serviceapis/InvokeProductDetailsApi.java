package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.BlogsModelHead;
import co.wm21.https.FHelper.networks.Models.ProductDetailsHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnProductDetailsRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeProductDetailsApi {
    OnProductDetailsRequestComplete requestComplete;

    public InvokeProductDetailsApi(String id, final OnProductDetailsRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getProductDetails(id).enqueue(new Callback<ProductDetailsHead>() {
            @Override
            public void onResponse(Call<ProductDetailsHead> call, Response<ProductDetailsHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onProductDetailsRequestComplete(response.body().getProductDetails());
                    } else {
                        requestComplete.onProductDetailsRequestError(response.body().getError_report());
                    }
                } else {
                    requestComplete.onProductDetailsRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<ProductDetailsHead> call, Throwable t) {
                requestComplete.onProductDetailsRequestError("Something Went Wrong!");
            }
        });
    }
}
