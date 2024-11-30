package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeAddToCartApi {
    OnRequestComplete requestComplete;

    public InvokeAddToCartApi(String pId,String userId,String color,String size,int qty, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.addToCart(pId,userId,color,size,qty).enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onRequestSuccess(response.body());
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<AddToCartModel> call, Throwable t) {
                requestComplete.onRequestError("Something Went Wrong!");
            }
        });
    }
}
