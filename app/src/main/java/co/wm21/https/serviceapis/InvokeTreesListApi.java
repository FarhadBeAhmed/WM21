package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.TreesModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnTreesListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTreesListApi {
    OnTreesListRequestComplete requestComplete;

    public InvokeTreesListApi(String deviceID, final OnTreesListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();

        mApiService.getTreesData(deviceID).enqueue(new Callback<TreesModel>() {
            @Override
            public void onResponse(Call<TreesModel> call, Response<TreesModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onTreesListRequestComplete(response.body());
                    } else {
                        requestComplete.onTreesListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onTreesListRequestError("Trees Data Failed!");
                }

            }
            @Override
            public void onFailure(Call<TreesModel> call, Throwable t) {
                requestComplete.onTreesListRequestError("Something Went Wrong in Trees Data!");
            }
        });
    }
}
