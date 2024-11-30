package co.wm21.https.serviceapis;

import com.google.gson.JsonObject;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse;
import co.wm21.https.FHelper.networks.Models.application.EShopRefComResponse;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeBDPStatusApi {
    OnRequestComplete requestComplete;
    public InvokeBDPStatusApi(String memberId, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", memberId);
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getBDPStatus(jsonObject).enqueue(new Callback<BDPStatusResponse>() {
            @Override
            public void onResponse(Call<BDPStatusResponse> call, Response<BDPStatusResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError() == 0 || response.body().getError() == 2) {
                        requestComplete.onRequestSuccess(response.body());
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Something Went Wrong!");
                }
            }
            @Override
            public void onFailure(Call<BDPStatusResponse> call, Throwable t) {
                requestComplete.onRequestError("Something Went Wrong!");
            }
        });
    }
}
