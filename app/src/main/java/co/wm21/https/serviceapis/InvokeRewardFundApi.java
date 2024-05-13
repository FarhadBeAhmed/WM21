package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnRewardFundRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.RewardFundDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardFundApi {
    OnRewardFundRequestComplete requestComplete;

    public InvokeRewardFundApi(String userID, final OnRewardFundRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getRewardFund(userID).enqueue(new Callback<RewardFundDataModel>() {
            @Override
            public void onResponse(Call<RewardFundDataModel> call, Response<RewardFundDataModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onRewardFundRequestSuccess(response.body().getTeamInfo());
                    } else {
                        requestComplete.onRewardFundRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardFundRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardFundDataModel> call, Throwable t) {
                requestComplete.onRewardFundRequestError("Something went wrong!");
            }
        });
    }


}
