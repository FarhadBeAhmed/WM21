package co.wm21.https.serviceapis;


import java.util.HashMap;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.MyFranchiseDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeMyFranchiseInfoApi {
    OnRequestComplete requestComplete;

    public InvokeMyFranchiseInfoApi(String userID, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        APIService mApiService = ApiUtils.getApiService();
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

                        requestComplete.onRequestSuccess(hashMap);
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<MyFranchiseDataModel> call, Throwable t) {
                requestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
