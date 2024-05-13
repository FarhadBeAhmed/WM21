package com.wm21ltd.wm21.serviceapis;

import com.google.gson.JsonObject;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnTrainingRequestFormRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Remote.APIService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTrainerRequestApi {
    OnTrainingRequestFormRequestComplete requestComplete;

    public InvokeTrainerRequestApi(String userID, String trainingCategory, String title,
                                   String details, String trainer, String charge, String duration, String venue,
                                   String seats, String targetDate, String division, String district, String thana, final OnTrainingRequestFormRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.submitTrainerRequest(userID, trainingCategory, title, details, trainer, charge, duration, venue, seats, targetDate,division,district,thana).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("error").getAsInt() == 0) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("error", response.body().get("error").getAsInt() + "");
                        hashMap.put("error_report", response.body().get("error_report").getAsString());
                        requestComplete.onTrainingRequestFormRequestSuccess(hashMap);
                    } else {
                        requestComplete.onTrainingRequestFormRequestError(response.body().get("error_report").toString());
                    }
                } else {
                    requestComplete.onTrainingRequestFormRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestComplete.onTrainingRequestFormRequestError("Something went wrong!");
            }
        });
    }
}
