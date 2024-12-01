package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.DeliveryItemsModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeDeliveryItemsApi {
    OnRequestComplete requestComplete;

    public InvokeDeliveryItemsApi(String user_id, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.delivery_items(user_id).enqueue(new Callback<DeliveryItemsModelHead>() {
            @Override
            public void onResponse(Call<DeliveryItemsModelHead> call, Response<DeliveryItemsModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onRequestSuccess(response.body().getData());
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<DeliveryItemsModelHead> call, Throwable t) {
                requestComplete.onRequestError("Something Went Wrong!");
            }
        });
    }
}
