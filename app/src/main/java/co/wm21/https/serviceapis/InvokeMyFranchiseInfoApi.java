package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnMyFranchiseRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.MyFranchiseDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeMyFranchiseInfoApi {
    OnMyFranchiseRequestComplete requestComplete;

    public InvokeMyFranchiseInfoApi(String userID, final OnMyFranchiseRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getMyFranchiseData(userID).enqueue(new Callback<MyFranchiseDataModel>() {
            @Override
            public void onResponse(Call<MyFranchiseDataModel> call, Response<MyFranchiseDataModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        HashMap hashMap = new HashMap();
                        if (response.body().getTitle() != null){
                            hashMap.put("title", response.body().getTitle());
                        } else {
                            hashMap.put("title", "null");
                        }

                        if (response.body().getName() != null){
                            hashMap.put("name", response.body().getName());
                        } else {
                            hashMap.put("name", "null");
                        }

                        if (response.body().getMobile() != null){
                            hashMap.put("mobile", response.body().getMobile());
                        } else {
                            hashMap.put("mobile", "null");
                        }

                        if (response.body().getEmail() != null){
                            hashMap.put("email", response.body().getEmail());
                        } else {
                            hashMap.put("email", "null");
                        }

                        if (response.body().getAddrss() != null){
                            hashMap.put("addrss", response.body().getAddrss());
                        } else {
                            hashMap.put("addrss", "null");
                        }

                        requestComplete.onMyFranchiseRequestComplete(hashMap);
                    } else {
                        requestComplete.onMyFranchiseRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onMyFranchiseRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<MyFranchiseDataModel> call, Throwable t) {
                requestComplete.onMyFranchiseRequestError("Something went wrong!");
            }
        });
    }
}
