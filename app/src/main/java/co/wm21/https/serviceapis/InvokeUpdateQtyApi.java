package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.BlogsModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeAddToCartApi {
    OnAddToCartRequestComplete requestComplete;

    public InvokeAddToCartApi(String pId,String userId,String color,String size,int qty, final OnAddToCartRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.addToCart(pId,userId,color,size,qty).enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onAddToCartRequestComplete(response.body());
                    } else {
                        requestComplete.onAddToCartRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onAddToCartRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<AddToCartModel> call, Throwable t) {
                requestComplete.onAddToCartRequestError("Something Went Wrong!");
            }
        });
    }
}
