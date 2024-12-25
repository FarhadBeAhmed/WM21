package co.wm21.https.serviceapis;


import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardAchievementApi {
    OnRequestComplete requestComplete;

    public InvokeRewardAchievementApi(String userID, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getRewardAchievementList(userID).enqueue(new Callback<RewardAchievementDataModel>() {
            @Override
            public void onResponse(Call<RewardAchievementDataModel> call, Response<RewardAchievementDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError() == 0){
                        requestComplete.onRequestSuccess(response.body().getTeamInfo());
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardAchievementDataModel> call, Throwable t) {
                requestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
