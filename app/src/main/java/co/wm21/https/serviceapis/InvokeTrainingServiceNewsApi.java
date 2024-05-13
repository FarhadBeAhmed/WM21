package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnTrainingServiceNewsRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.TrainingServiceNewsDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTrainingServiceNewsApi {
    private OnTrainingServiceNewsRequestComplete onTrainingServiceNewsRequestComplete;

    public InvokeTrainingServiceNewsApi(String type, String limit, final OnTrainingServiceNewsRequestComplete onTrainingServiceNewsRequestComplete) {
        this.onTrainingServiceNewsRequestComplete = onTrainingServiceNewsRequestComplete;

        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getTrainingServiceNews(type,limit).enqueue(new Callback<TrainingServiceNewsDataModel>() {
            @Override
            public void onResponse(Call<TrainingServiceNewsDataModel> call, Response<TrainingServiceNewsDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        onTrainingServiceNewsRequestComplete.onTrainingServiceNewsRequestSuccess(response.body().getCategory());
                    }
                    else {
                        onTrainingServiceNewsRequestComplete.onTrainingServiceNewsRequestError(response.body().getErrorReport());
                    }
                }
                else {
                    onTrainingServiceNewsRequestComplete.onTrainingServiceNewsRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<TrainingServiceNewsDataModel> call, Throwable t) {

                onTrainingServiceNewsRequestComplete.onTrainingServiceNewsRequestError("Something went wrong!");
            }
        });
    }
}
