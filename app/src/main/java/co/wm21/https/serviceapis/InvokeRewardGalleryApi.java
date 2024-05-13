package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnRewardGalleryRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.RewardGalleryDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardGalleryApi {
    OnRewardGalleryRequestComplete requestComplete;

    public InvokeRewardGalleryApi(String userID, String limitData, final OnRewardGalleryRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getRewardGallery(userID, limitData).enqueue(new Callback<RewardGalleryDataModel>() {
            @Override
            public void onResponse(Call<RewardGalleryDataModel> call, Response<RewardGalleryDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        requestComplete.onRewardGalleryRequestSuccess(response.body().getServiceTraining());
                    } else {
                        requestComplete.onRewardGalleryRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardGalleryRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardGalleryDataModel> call, Throwable t) {
                requestComplete.onRewardGalleryRequestError("Something went wrong!");
            }
        });
    }
}
