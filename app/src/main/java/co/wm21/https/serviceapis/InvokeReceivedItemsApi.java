package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.ReceivedItemsModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnReceivedItemsRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeReceivedItemsApi {
    OnReceivedItemsRequestComplete requestComplete;

    public InvokeReceivedItemsApi(String userId, final OnReceivedItemsRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.receivedItems(userId).enqueue(new Callback<ReceivedItemsModelHead>() {
            @Override
            public void onResponse(Call<ReceivedItemsModelHead> call, Response<ReceivedItemsModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0) {
                        requestComplete.onReceivedItemsRequestComplete(response.body());
                    } else {
                        requestComplete.onReceivedItemsRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onReceivedItemsRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<ReceivedItemsModelHead> call, Throwable t) {
                requestComplete.onReceivedItemsRequestError("Something Went Wrong!");
            }
        });
    }
}
