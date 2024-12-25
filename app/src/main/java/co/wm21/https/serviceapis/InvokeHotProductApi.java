package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.HotProductModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHotProductApi {
    private OnRequestComplete onHotProductRequestComplete;

    public InvokeHotProductApi(int value, final OnRequestComplete onHotProductRequestComplete) {
        this.onHotProductRequestComplete = onHotProductRequestComplete;
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getHotProduct(value).enqueue(new Callback<HotProductModelHead>() {
            @Override
            public void onResponse(Call<HotProductModelHead> call, Response<HotProductModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHotProductRequestComplete.onRequestSuccess(response.body().getHotProduct());
                    }else {
                        onHotProductRequestComplete.onRequestError(response.body().getErrorReport());
                    }

                }else {
                    onHotProductRequestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<HotProductModelHead> call, Throwable t) {

            }
        });
    }
}
