package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.HotProductModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnHotProductRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHotProductApi {
    private OnHotProductRequestComplete onHotProductRequestComplete;

    public InvokeHotProductApi(int value, final OnHotProductRequestComplete onHotProductRequestComplete) {
        this.onHotProductRequestComplete = onHotProductRequestComplete;
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getHotProduct(value).enqueue(new Callback<HotProductModelHead>() {
            @Override
            public void onResponse(Call<HotProductModelHead> call, Response<HotProductModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHotProductRequestComplete.onHotProductSuccess(response.body().getHotProduct());
                    }else {
                        onHotProductRequestComplete.onHotProductError(response.body().getErrorReport());
                    }

                }else {
                    onHotProductRequestComplete.onHotProductError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<HotProductModelHead> call, Throwable t) {

            }
        });
    }
}
