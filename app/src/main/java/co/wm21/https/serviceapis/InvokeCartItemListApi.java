package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnCartItemListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeCartItemListApi {
    OnCartItemListRequestComplete requestComplete;

    public InvokeCartItemListApi(String deviceID, final OnCartItemListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getCartItems(deviceID).enqueue(new Callback<CartItemsHead>() {
            @Override
            public void onResponse(Call<CartItemsHead> call, Response<CartItemsHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onCartItemListRequestComplete(response.body());
                    } else {
                        requestComplete.onCartItemListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onCartItemListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<CartItemsHead> call, Throwable t) {
                requestComplete.onCartItemListRequestError("Something Went Wrong!");
            }
        });
    }
}
