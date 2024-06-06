package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.TreeModel;
import co.wm21.https.FHelper.networks.Models.TreesModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnTreeDataRequestComplete;
import co.wm21.https.interfaces.OnTreesListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTreeDataApi {
    OnTreeDataRequestComplete requestComplete;

    public InvokeTreeDataApi(String deviceID,String user_id, final OnTreeDataRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);

        mApiService.getTreeData(deviceID,user_id).enqueue(new Callback<TreeModel>() {
            @Override
            public void onResponse(Call<TreeModel> call, Response<TreeModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onTreeDataRequestComplete(response.body());
                    } else {
                        requestComplete.onTreeDataRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onTreeDataRequestError("Tree Data Failed");
                }

            }
            @Override
            public void onFailure(Call<TreeModel> call, Throwable t) {
                requestComplete.onTreeDataRequestError("Something Went Wrong in Tree Data!");
            }
        });
    }
}
