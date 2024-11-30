package co.wm21.https.serviceapis;

import com.google.gson.JsonObject;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.application.EShopRefComResponse;
import co.wm21.https.FHelper.networks.Models.application.EShopStatusResponse;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeEShopRefComApi {
    OnRequestComplete requestComplete;
    public InvokeEShopRefComApi(String memberId, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", memberId);
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getEShopRefCom(jsonObject).enqueue(new Callback<EShopRefComResponse>() {
            @Override
            public void onResponse(Call<EShopRefComResponse> call, Response<EShopRefComResponse> response) {
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
            public void onFailure(Call<EShopRefComResponse> call, Throwable t) {
                requestComplete.onRequestError("Something Went Wrong!");
            }
        });
    }
}
