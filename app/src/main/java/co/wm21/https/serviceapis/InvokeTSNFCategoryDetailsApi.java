package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnTSNFCategoryDetailsRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.TSNFCategoryDetailsModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTSNFCategoryDetailsApi {
    private OnTSNFCategoryDetailsRequestComplete onTSNFCategoryDetailsRequestComplete;

    public InvokeTSNFCategoryDetailsApi(String type, String category, String limit, final OnTSNFCategoryDetailsRequestComplete onTSNFCategoryDetailsRequestComplete) {
        this.onTSNFCategoryDetailsRequestComplete = onTSNFCategoryDetailsRequestComplete;

        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getTSNFCategoryDetails(type,category,limit).enqueue(new Callback<TSNFCategoryDetailsModel>() {
            @Override
            public void onResponse(Call<TSNFCategoryDetailsModel> call, Response<TSNFCategoryDetailsModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        onTSNFCategoryDetailsRequestComplete.onTSNFCategoryDetailsRequestSuccess(response.body().getServiceTraining());
                    }
                    else {
                        onTSNFCategoryDetailsRequestComplete.onTSNFCategoryDetailsRequestError(response.body().getErrorReport());
                    }
                }
                else {
                    onTSNFCategoryDetailsRequestComplete.onTSNFCategoryDetailsRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<TSNFCategoryDetailsModel> call, Throwable t) {
                onTSNFCategoryDetailsRequestComplete.onTSNFCategoryDetailsRequestError("Something went wrong!");
            }
        });
    }
}
