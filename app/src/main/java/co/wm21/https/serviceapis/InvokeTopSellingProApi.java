package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AppliedProductModelHead;
import co.wm21.https.FHelper.networks.Models.TopSellingProModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAppliedProductsRequestComplete;
import co.wm21.https.interfaces.OnTopSellingProRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTopSellingProApi {
    OnTopSellingProRequestComplete requestComplete;

    public InvokeTopSellingProApi(int limit,final OnTopSellingProRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.topSelling(limit).enqueue(new Callback<TopSellingProModelHead>() {
            @Override
            public void onResponse(Call<TopSellingProModelHead> call, Response<TopSellingProModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onTopSellingProRequestComplete(response.body().getData());
                    } else {
                        requestComplete.onTopSellingProRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onTopSellingProRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<TopSellingProModelHead> call, Throwable t) {
                requestComplete.onTopSellingProRequestError("Something Went Wrong!");
            }
        });
    }
}
