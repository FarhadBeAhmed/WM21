package co.wm21.https.serviceapis;



import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.RewardFundDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnRewardFundRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardFundApi {
    OnRewardFundRequestComplete requestComplete;

    public InvokeRewardFundApi(String userID, final OnRewardFundRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.web_url);
        mApiService.getRewardFund(userID).enqueue(new Callback<RewardFundDataModel>() {
            @Override
            public void onResponse(Call<RewardFundDataModel> call, Response<RewardFundDataModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onRewardFundRequestSuccess(response.body().getTeamInfo());
                    } else {
                        requestComplete.onRewardFundRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardFundRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardFundDataModel> call, Throwable t) {
                requestComplete.onRewardFundRequestError("Something went wrong!");
            }
        });
    }


}
