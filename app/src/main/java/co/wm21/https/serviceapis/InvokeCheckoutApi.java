package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.CheckoutModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnCheckoutRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeCheckoutApi {
    OnCheckoutRequestComplete requestComplete;

    public InvokeCheckoutApi(String deviceID, String userId,  final OnCheckoutRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.checkout(deviceID,userId).enqueue(new Callback<CheckoutModel>() {
            @Override
            public void onResponse(Call<CheckoutModel> call, Response<CheckoutModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onCheckoutRequestComplete(response.body());
                    } else {
                        requestComplete.onCheckoutRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onCheckoutRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<CheckoutModel> call, Throwable t) {
                requestComplete.onCheckoutRequestError("Something Went Wrong!");
            }
        });
    }
}
