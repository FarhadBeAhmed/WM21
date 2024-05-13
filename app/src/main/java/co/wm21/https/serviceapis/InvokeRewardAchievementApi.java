package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnRewardAchievementRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.RewardAchievementDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardAchievementApi {
    OnRewardAchievementRequestComplete requestComplete;

    public InvokeRewardAchievementApi(String userID, final OnRewardAchievementRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getRewardAchievementList(userID).enqueue(new Callback<RewardAchievementDataModel>() {
            @Override
            public void onResponse(Call<RewardAchievementDataModel> call, Response<RewardAchievementDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError() == 0){
                        requestComplete.onRewardAchievementRequestSuccess(response.body().getServiceTraining());
                    } else {
                        requestComplete.onRewardAchievementRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardAchievementRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardAchievementDataModel> call, Throwable t) {
                requestComplete.onRewardAchievementRequestError("Something went wrong!");
            }
        });
    }
}
