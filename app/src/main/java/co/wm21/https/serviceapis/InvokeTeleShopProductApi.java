package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.HotProductModelHead;
import co.wm21.https.FHelper.networks.Models.TeleShopProductModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnHotProductRequestComplete;
import co.wm21.https.interfaces.OnTeleShopProductRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTeleShopProductApi {
    private OnTeleShopProductRequestComplete onTeleShopProductRequestComplete;

    public InvokeTeleShopProductApi(String value, final OnTeleShopProductRequestComplete onTeleShopProductRequestComplete) {
        this.onTeleShopProductRequestComplete = onTeleShopProductRequestComplete;
        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getTeleShopProduct(value).enqueue(new Callback<TeleShopProductModelHead>() {
            @Override
            public void onResponse(Call<TeleShopProductModelHead> call, Response<TeleShopProductModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onTeleShopProductRequestComplete.onTeleShopProductSuccess(response.body().getTeleShopProducts());
                    }else {
                        onTeleShopProductRequestComplete.onTeleShopProductError(response.body().getErrorReport());
                    }

                }else {
                    onTeleShopProductRequestComplete.onTeleShopProductError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<TeleShopProductModelHead> call, Throwable t) {

            }
        });
    }
}
