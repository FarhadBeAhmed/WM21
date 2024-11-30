package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AppliedProductModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnAppliedProductsRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeAppliedProductsApi {
    OnAppliedProductsRequestComplete requestComplete;

    public InvokeAppliedProductsApi(String user_id, final OnAppliedProductsRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.appliedProducts(user_id).enqueue(new Callback<AppliedProductModelHead>() {
            @Override
            public void onResponse(Call<AppliedProductModelHead> call, Response<AppliedProductModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onAppliedProductsRequestComplete(response.body());
                    } else {
                        requestComplete.onAppliedProductsRequestError(response.body().getError_report());
                    }
                } else {
                    requestComplete.onAppliedProductsRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<AppliedProductModelHead> call, Throwable t) {
                requestComplete.onAppliedProductsRequestError("Something Went Wrong!");
            }
        });
    }
}
