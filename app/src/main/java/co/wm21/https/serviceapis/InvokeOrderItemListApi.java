package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.OrderItemModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnOrderItemListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeOrderItemListApi {
    OnOrderItemListRequestComplete requestComplete;

    public InvokeOrderItemListApi(String user_id, final OnOrderItemListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.orderItems(user_id).enqueue(new Callback<OrderItemModelHead>() {
            @Override
            public void onResponse(Call<OrderItemModelHead> call, Response<OrderItemModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onOrderItemListRequestComplete(response.body());
                    } else {
                        requestComplete.onOrderItemListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onOrderItemListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<OrderItemModelHead> call, Throwable t) {
                requestComplete.onOrderItemListRequestError("Something Went Wrong!");
            }
        });
    }
}
