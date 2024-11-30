package co.wm21.https.serviceapis;


import android.util.Log;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.view.fragments.member.model.RewardPolicyResponse;
import co.wm21.https.presenter.interfaces.OnRewardPolicyRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardPolicyApi {
    OnRewardPolicyRequestComplete requestComplete;

    API api;

    public InvokeRewardPolicyApi(String userID,final OnRewardPolicyRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        api=ConstantValues.getAPI();

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getRewardPolicy(userID).enqueue(new Callback<RewardPolicyResponse>() {
            @Override
            public void onResponse(Call<RewardPolicyResponse> call, Response<RewardPolicyResponse> response) {
                Log.d("getRewardPolicy", "onResponse: "+response.body());
                if (response.isSuccessful()){
                    if (response.body().getError() == false){
                        requestComplete.onRewardPolicyRequestSuccess(response.body());
                    } else {
                        requestComplete.onRewardPolicyRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardPolicyRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardPolicyResponse> call, Throwable t) {
                requestComplete.onRewardPolicyRequestError("Something went wrong!");
            }
        });
    }
}
