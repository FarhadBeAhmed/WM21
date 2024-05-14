package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.PopularCatProductModelHead;
import co.wm21.https.FHelper.networks.Models.PopularProductViewModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnHomePopularCatProductComplete;
import co.wm21.https.interfaces.OnHomePopularProductComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHomePopularCatProductApi {
    private OnHomePopularCatProductComplete onHomePopularCatProductComplete;

    public InvokeHomePopularCatProductApi(String value1,String value2,String value3,String value4, final OnHomePopularCatProductComplete onHomePopularCatProductComplete) {
        this.onHomePopularCatProductComplete = onHomePopularCatProductComplete;
        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getPopularCatProductList(value1, value2, value3, value4).enqueue(new Callback<PopularCatProductModelHead>() {
            @Override
            public void onResponse(Call<PopularCatProductModelHead> call, Response<PopularCatProductModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHomePopularCatProductComplete.onHomePopularCatProductSuccess(response.body().getPopCatProduct());
                    }else {
                        onHomePopularCatProductComplete.onHomePopularCatProductError(response.body().getErrorReport());
                    }

                }else {
                    onHomePopularCatProductComplete.onHomePopularCatProductError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<PopularCatProductModelHead> call, Throwable t) {

            }
        });
    }
}
