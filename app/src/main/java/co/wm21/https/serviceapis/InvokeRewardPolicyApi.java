package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnRewardPolicyRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.RewardPolicyDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardPolicyApi {
    OnRewardPolicyRequestComplete requestComplete;

    public InvokeRewardPolicyApi(final OnRewardPolicyRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getRewardPolicy().enqueue(new Callback<RewardPolicyDataModel>() {
            @Override
            public void onResponse(Call<RewardPolicyDataModel> call, Response<RewardPolicyDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError() == 0){
                        requestComplete.onRewardPolicyRequestSuccess(response.body().getTeamInfo());
                    } else {
                        requestComplete.onRewardPolicyRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardPolicyRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardPolicyDataModel> call, Throwable t) {
                requestComplete.onRewardPolicyRequestError("Something went wrong!");
            }
        });
    }
}
