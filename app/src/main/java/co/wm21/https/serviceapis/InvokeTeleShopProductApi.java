package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.TeleShopProductModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTeleShopProductApi {
    private OnRequestComplete onTeleShopProductRequestComplete;

    public InvokeTeleShopProductApi(String value, final OnRequestComplete onTeleShopProductRequestComplete) {
        this.onTeleShopProductRequestComplete = onTeleShopProductRequestComplete;
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getTeleShopProduct(value).enqueue(new Callback<TeleShopProductModelHead>() {
            @Override
            public void onResponse(Call<TeleShopProductModelHead> call, Response<TeleShopProductModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onTeleShopProductRequestComplete.onRequestSuccess(response.body().getTeleShopProducts());
                    }else {
                        onTeleShopProductRequestComplete.onRequestError(response.body().getErrorReport());
                    }

                }else {
                    onTeleShopProductRequestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<TeleShopProductModelHead> call, Throwable t) {
                onTeleShopProductRequestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
