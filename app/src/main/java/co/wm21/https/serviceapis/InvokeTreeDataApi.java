package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.TreeModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTreeDataApi {
    OnRequestComplete requestComplete;

    public InvokeTreeDataApi(String deviceID,String user_id, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();

        mApiService.getTreeData(deviceID,user_id).enqueue(new Callback<TreeModel>() {
            @Override
            public void onResponse(Call<TreeModel> call, Response<TreeModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onRequestSuccess(response.body());
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Tree Data Failed");
                }

            }
            @Override
            public void onFailure(Call<TreeModel> call, Throwable t) {
                requestComplete.onRequestError("Something Went Wrong in Tree Data!");
            }
        });
    }
}
