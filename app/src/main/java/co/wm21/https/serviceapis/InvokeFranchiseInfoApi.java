package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnFranchiseInfoRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.FranchiseInfoSearchDataModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeFranchiseInfoApi {
    OnFranchiseInfoRequestComplete requestComplete;

    public InvokeFranchiseInfoApi(String divisionID, String districtID, String thanaID, final OnFranchiseInfoRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getFranchiseBySearch(divisionID,districtID,thanaID).enqueue(new Callback<FranchiseInfoSearchDataModel>() {
            @Override
            public void onResponse(Call<FranchiseInfoSearchDataModel> call, Response<FranchiseInfoSearchDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError() == 0){
                        HashMap hashMap = new HashMap();
                        hashMap.put("title", response.body().getTitle());
                        hashMap.put("name", response.body().getName());
                        hashMap.put("mobile", response.body().getMobile());
                        hashMap.put("email", response.body().getEmail());
                        hashMap.put("addrss", response.body().getAddrss());
                        requestComplete.onFranchiseInfoRequestSuccess(hashMap);
                    } else {
                        requestComplete.onFranchiseInfoRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onFranchiseInfoRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<FranchiseInfoSearchDataModel> call, Throwable t) {
                requestComplete.onFranchiseInfoRequestError("Something went wrong!");
            }
        });
    }
}
