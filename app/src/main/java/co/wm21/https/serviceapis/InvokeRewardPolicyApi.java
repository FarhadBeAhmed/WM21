package co.wm21.https.serviceapis;


import android.util.Log;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.fragments.member.model.RewardPolicyDataModel;
import co.wm21.https.interfaces.OnRewardPolicyRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardPolicyApi {
    OnRewardPolicyRequestComplete requestComplete;

    API api;

    public InvokeRewardPolicyApi(String userID,final OnRewardPolicyRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        api=ConstantValues.getAPI();


        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getRewardPolicy(userID).enqueue(new Callback<RewardPolicyDataModel>() {
            @Override
            public void onResponse(Call<RewardPolicyDataModel> call, Response<RewardPolicyDataModel> response) {
                Log.d("getRewardPolicy", "onResponse: "+response.body());
                if (response.isSuccessful()){
                    if (response.body().getError() == 0){
                        requestComplete.onRewardPolicyRequestSuccess(response.body().getTeamInfo());
                    } else {
                        requestComplete.onRewardPolicyRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardPolicyRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardPolicyDataModel> call, Throwable t) {
                requestComplete.onRewardPolicyRequestError("Something went wrong!");
            }
        });
    }
}
