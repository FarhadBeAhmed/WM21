package co.wm21.https.serviceapis;


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

    public InvokeRewardPolicyApi(final OnRewardPolicyRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        api=ConstantValues.getAPI();


        APIService mApiService = ApiUtils.getApiService(ConstantValues.web_url);
        mApiService.getRewardPolicy().enqueue(new Callback<RewardPolicyDataModel>() {
            @Override
            public void onResponse(Call<RewardPolicyDataModel> call, Response<RewardPolicyDataModel> response) {
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
