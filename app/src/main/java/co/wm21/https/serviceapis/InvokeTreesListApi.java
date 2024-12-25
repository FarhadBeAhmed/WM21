package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.TreesModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTreesListApi {
    OnRequestComplete requestComplete;

    public InvokeTreesListApi(String deviceID, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();

        mApiService.getTreesData(deviceID).enqueue(new Callback<TreesModel>() {
            @Override
            public void onResponse(Call<TreesModel> call, Response<TreesModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onRequestSuccess(response.body());
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Trees Data Failed!");
                }

            }
            @Override
            public void onFailure(Call<TreesModel> call, Throwable t) {
                requestComplete.onRequestError("Something Went Wrong in Trees Data!");
            }
        });
    }
}
