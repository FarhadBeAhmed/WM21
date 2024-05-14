package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.DeliveryReceiveModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnDeliveryReceiveRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeDeliveryReceiveApi {
    OnDeliveryReceiveRequestComplete requestComplete;

    public InvokeDeliveryReceiveApi(String userId, String pin, String action, String serial, final OnDeliveryReceiveRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.deliveryReceive(userId, pin, action, serial).enqueue(new Callback<DeliveryReceiveModel>() {
            @Override
            public void onResponse(Call<DeliveryReceiveModel> call, Response<DeliveryReceiveModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError() == 0 || response.body().getError() == 2) {
                        requestComplete.onDeliveryReceiveRequestComplete(response.body());
                    } else {
                        requestComplete.onDeliveryReceiveRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onDeliveryReceiveRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<DeliveryReceiveModel> call, Throwable t) {
                requestComplete.onDeliveryReceiveRequestError("Something Went Wrong!");
            }
        });
    }
}
