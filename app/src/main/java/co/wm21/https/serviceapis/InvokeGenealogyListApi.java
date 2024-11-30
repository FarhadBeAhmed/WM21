package co.wm21.https.serviceapis;

import com.google.gson.JsonObject;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.genealogy.GenealogyListResponse;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.gelealogy.OnGenealogyListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeGenealogyListApi {
    OnGenealogyListRequestComplete requestComplete;

    public InvokeGenealogyListApi(String memberId, final OnGenealogyListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", memberId);
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getGenealogyList(jsonObject).enqueue(new Callback<GenealogyListResponse>() {
            @Override
            public void onResponse(Call<GenealogyListResponse> call, Response<GenealogyListResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError() == 0 || response.body().getError() == 2) {
                        requestComplete.onApiRequestComplete(response.body());
                    } else {
                        requestComplete.onApiRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onApiRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<GenealogyListResponse> call, Throwable t) {
                requestComplete.onApiRequestError("Something Went Wrong!");
            }
        });
    }
}
