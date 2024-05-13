package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.PopularProductViewModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnHomePopularProductComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHomePopularProductApi {
    private OnHomePopularProductComplete onHomePopularProductComplete;

    public InvokeHomePopularProductApi(String value, final OnHomePopularProductComplete onHomePopularProductComplete) {
        this.onHomePopularProductComplete = onHomePopularProductComplete;
        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getPopularProductList(value).enqueue(new Callback<PopularProductViewModelHead>() {
            @Override
            public void onResponse(Call<PopularProductViewModelHead> call, Response<PopularProductViewModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHomePopularProductComplete.onHomePopularProductSuccess(response.body().getProductViews());
                    }else {
                        onHomePopularProductComplete.onHomePopularProductError(response.body().getErrorReport());
                    }

                }else {
                    onHomePopularProductComplete.onHomePopularProductError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<PopularProductViewModelHead> call, Throwable t) {

            }
        });
    }
}
