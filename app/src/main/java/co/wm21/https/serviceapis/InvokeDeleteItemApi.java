package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.DeleteItem;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnDeleteItemRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeDeleteItemApi {
    OnDeleteItemRequestComplete requestComplete;

    public InvokeDeleteItemApi(String pId, int type, final OnDeleteItemRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.deleteFromCart(pId,type).enqueue(new Callback<DeleteItem>() {
            @Override
            public void onResponse(Call<DeleteItem> call, Response<DeleteItem> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onDeleteItemRequestComplete(response.body());
                    } else {
                        requestComplete.onDeleteItemRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onDeleteItemRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<DeleteItem> call, Throwable t) {
                requestComplete.onDeleteItemRequestError("Something Went Wrong!");
            }
        });
    }
}
