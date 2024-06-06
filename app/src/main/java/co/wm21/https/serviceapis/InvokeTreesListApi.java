package co.wm21.https.serviceapis;

import com.google.gson.JsonObject;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.FHelper.networks.Models.TreesModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnCartItemListRequestComplete;
import co.wm21.https.interfaces.OnTreesListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTreesListApi {
    OnTreesListRequestComplete requestComplete;

    public InvokeTreesListApi(String deviceID, final OnTreesListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);

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
