package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.PopularCatProductModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHomePopularCatProductApi {
    private OnRequestComplete onHomePopularCatProductComplete;

    public InvokeHomePopularCatProductApi(String value1,String value2,String value3,String value4, final OnRequestComplete onHomePopularCatProductComplete) {
        this.onHomePopularCatProductComplete = onHomePopularCatProductComplete;
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getPopularCatProductList(value1, value2, value3, value4).enqueue(new Callback<PopularCatProductModelHead>() {
            @Override
            public void onResponse(Call<PopularCatProductModelHead> call, Response<PopularCatProductModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHomePopularCatProductComplete.onRequestSuccess(response.body().getPopCatProduct());
                    }else {
                        onHomePopularCatProductComplete.onRequestError(response.body().getErrorReport());
                    }

                }else {
                    onHomePopularCatProductComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<PopularCatProductModelHead> call, Throwable t) {

            }
        });
    }
}
