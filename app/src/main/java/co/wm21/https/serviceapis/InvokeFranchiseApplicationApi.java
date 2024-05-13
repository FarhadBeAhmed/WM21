package com.wm21ltd.wm21.serviceapis;

import com.google.gson.JsonObject;
import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnFranchiseApplicationRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Remote.APIService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeFranchiseApplicationApi {
    OnFranchiseApplicationRequestComplete requestComplete;

    public InvokeFranchiseApplicationApi(String serviceID, String userID, String formType,
                                         String divisionID, String districtID, String thanaID,
                                         String appName, String appAddress, String appLicense, String category,
                                         final OnFranchiseApplicationRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.submitFranchiseApplication(serviceID, userID, formType, divisionID, districtID, thanaID, appName,
                appAddress, appLicense, category).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("error").getAsInt() == 0) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("error", response.body().get("error").getAsInt() + "");
                        hashMap.put("error_report", response.body().get("error_report").getAsString());
                        requestComplete.onFranchiseApplicationRequestSuccess(hashMap);
                    } else {
                        requestComplete.onFranchiseApplicationRequestError(response.body().get("error_report").toString());
                    }
                } else {
                    requestComplete.onFranchiseApplicationRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestComplete.onFranchiseApplicationRequestError("Something went wrong!");
            }
        });
    }
}
