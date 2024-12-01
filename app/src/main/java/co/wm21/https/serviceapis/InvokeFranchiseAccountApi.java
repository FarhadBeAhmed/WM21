package co.wm21.https.serviceapis;


import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.FranchiseAccountDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeFranchiseAccountApi {
    OnRequestComplete requestComplete;

    public InvokeFranchiseAccountApi(String userID, String dataLimit, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getFranchiseAccountCommission(userID, dataLimit).enqueue(new Callback<FranchiseAccountDataModel>() {
            @Override
            public void onResponse(Call<FranchiseAccountDataModel> call, Response<FranchiseAccountDataModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onRequestSuccess(response.body().getServiceTraining());
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<FranchiseAccountDataModel> call, Throwable t) {
                requestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
