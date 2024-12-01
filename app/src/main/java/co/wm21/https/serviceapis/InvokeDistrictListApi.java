package co.wm21.https.serviceapis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeDistrictListApi {
    OnRequestComplete requestComplete;

    public InvokeDistrictListApi(String countryID, String divisionID, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getDistrictList("", divisionID, countryID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("error").getAsInt() == 0) {
                        JsonArray jsonArray = new JsonArray();
                        jsonArray = response.body().get("report").getAsJsonArray();
                        requestComplete.onRequestSuccess(jsonArray);
                    } else {
                        requestComplete.onRequestError(response.body().get("error_report").toString());
                    }
                } else {
                    requestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
