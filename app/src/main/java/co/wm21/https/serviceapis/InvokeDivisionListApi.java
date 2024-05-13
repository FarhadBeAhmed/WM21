package com.wm21ltd.wm21.serviceapis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnDivisionListRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeDivisionListApi {
    OnDivisionListRequestComplete requestComplete;

    public InvokeDivisionListApi(String counrtyID, final OnDivisionListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getDivisionList("", counrtyID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("error").getAsInt() == 0) {
                        JsonArray jsonArray = new JsonArray();
                        jsonArray = response.body().get("report").getAsJsonArray();
                        requestComplete.onDivisionListRequestComplete(jsonArray);
                    } else {
                        requestComplete.onDevisionListRequestError(response.body().get("error_report").toString());
                    }
                } else {
                    requestComplete.onDevisionListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestComplete.onDevisionListRequestError("Something Went Wrong!");
            }
        });
    }
}
