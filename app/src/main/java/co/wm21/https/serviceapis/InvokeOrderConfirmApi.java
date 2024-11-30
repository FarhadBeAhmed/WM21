package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.OrderConfirmModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnOrderConfirmRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeOrderConfirmApi {
    OnOrderConfirmRequestComplete requestComplete;

    public InvokeOrderConfirmApi( String userId, String eshop,String shipping,String address,int adjust, final OnOrderConfirmRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.orderConfirm(userId,eshop,shipping,address,adjust).enqueue(new Callback<OrderConfirmModel>() {
            @Override
            public void onResponse(Call<OrderConfirmModel> call, Response<OrderConfirmModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onOrderConfirmRequestComplete(response.body());
                    } else {
                        requestComplete.onOrderConfirmRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onOrderConfirmRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<OrderConfirmModel> call, Throwable t) {
                requestComplete.onOrderConfirmRequestError("Something Went Wrong!");
            }
        });
    }
}
