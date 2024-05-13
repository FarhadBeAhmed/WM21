package com.wm21ltd.wm21.serviceapis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnDistrictListRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeDistrictListApi {
    OnDistrictListRequestComplete requestComplete;

    public InvokeDistrictListApi(String countryID, String divisionID, final OnDistrictListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getDistrictList("", divisionID, countryID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("error").getAsInt() == 0) {
                        JsonArray jsonArray = new JsonArray();
                        jsonArray = response.body().get("report").getAsJsonArray();
                        requestComplete.onDistrictListRequestSuccess(jsonArray);
                    } else {
                        requestComplete.onDistrictListRequestError(response.body().get("error_report").toString());
                    }
                } else {
                    requestComplete.onDistrictListRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestComplete.onDistrictListRequestError("Something went wrong!");
            }
        });
    }
}
