package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnBrandAmbassadorRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.BrandAmbassadorDataModel;
import com.wm21ltd.wm21.networks.Models.BrandAmbassadorListModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeBrandAmbassadorApi {
    private OnBrandAmbassadorRequestComplete onBrandAmbassadorRequestComplete;

    public InvokeBrandAmbassadorApi(String limit, final OnBrandAmbassadorRequestComplete onBrandAmbassadorRequestComplete) {
        this.onBrandAmbassadorRequestComplete = onBrandAmbassadorRequestComplete;

        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getBrandAmbassador(limit).enqueue(new Callback<BrandAmbassadorDataModel>() {
            @Override
            public void onResponse(Call<BrandAmbassadorDataModel> call, Response<BrandAmbassadorDataModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        onBrandAmbassadorRequestComplete.onBrandAmbassadorRequestComplete(response.body().getAmbassador());
                    } else {
                        onBrandAmbassadorRequestComplete.onBrandAmbassadorRequestError(response.body().getErrorReport());
                    }
                } else {
                    onBrandAmbassadorRequestComplete.onBrandAmbassadorRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<BrandAmbassadorDataModel> call, Throwable t) {
                onBrandAmbassadorRequestComplete.onBrandAmbassadorRequestError("Something went wrong!");
            }
        });
    }
}
