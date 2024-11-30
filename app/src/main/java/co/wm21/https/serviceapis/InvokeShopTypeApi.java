package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.ShopTypeModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnShopTypeRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeShopTypeApi {
    OnShopTypeRequestComplete requestComplete;

    public InvokeShopTypeApi(String userId, final OnShopTypeRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.shopType(userId).enqueue(new Callback<ShopTypeModelHead>() {
            @Override
            public void onResponse(Call<ShopTypeModelHead> call, Response<ShopTypeModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onShopTypeRequestComplete(response.body().getData());
                    } else {
                        requestComplete.onShopTypeRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onShopTypeRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<ShopTypeModelHead> call, Throwable t) {
                requestComplete.onShopTypeRequestError("Something Went Wrong!");
            }
        });
    }
}
