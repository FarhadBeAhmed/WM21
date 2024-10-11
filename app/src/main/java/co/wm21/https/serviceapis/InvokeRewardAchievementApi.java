package co.wm21.https.serviceapis;



import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnRewardAchievementRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardAchievementApi {
    OnRewardAchievementRequestComplete requestComplete;

    public InvokeRewardAchievementApi(String userID, final OnRewardAchievementRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getRewardAchievementList(userID).enqueue(new Callback<RewardAchievementDataModel>() {
            @Override
            public void onResponse(Call<RewardAchievementDataModel> call, Response<RewardAchievementDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError() == 0){
                        requestComplete.onRewardAchievementRequestSuccess(response.body().getTeamInfo());
                    } else {
                        requestComplete.onRewardAchievementRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardAchievementRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardAchievementDataModel> call, Throwable t) {
                requestComplete.onRewardAchievementRequestError("Something went wrong!");
            }
        });
    }
}
