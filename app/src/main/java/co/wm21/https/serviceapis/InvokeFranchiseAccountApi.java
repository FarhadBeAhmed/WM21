package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnFranchiseAccountRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.FranchiseAccountDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeFranchiseAccountApi {
    OnFranchiseAccountRequestComplete requestComplete;

    public InvokeFranchiseAccountApi(String userID, String dataLimit, final OnFranchiseAccountRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getFranchiseAccountCommission(userID, dataLimit).enqueue(new Callback<FranchiseAccountDataModel>() {
            @Override
            public void onResponse(Call<FranchiseAccountDataModel> call, Response<FranchiseAccountDataModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onFranchiseAccountRequestSuccess(response.body().getServiceTraining());
                    } else {
                        requestComplete.onFranchiseAccountRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onFranchiseAccountRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<FranchiseAccountDataModel> call, Throwable t) {
                requestComplete.onFranchiseAccountRequestError("Something went wrong!");
            }
        });
    }
}
