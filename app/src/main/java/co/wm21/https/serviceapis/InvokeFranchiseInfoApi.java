package co.wm21.https.serviceapis;


import java.util.HashMap;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.FranchiseInfoSearchDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeFranchiseInfoApi {
    OnRequestComplete requestComplete;

    public InvokeFranchiseInfoApi(String divisionID, String districtID, String thanaID, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
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
                        requestComplete.onRequestSuccess(hashMap);
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<FranchiseInfoSearchDataModel> call, Throwable t) {
                requestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
