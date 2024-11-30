package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.DrawerCatModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnDrawerCatListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeDrawerCatListApi {
    OnDrawerCatListRequestComplete requestComplete;

    public InvokeDrawerCatListApi(int id,String counrtyID, final OnDrawerCatListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getDrawerCatList(id, counrtyID).enqueue(new Callback<DrawerCatModelHead>() {
            @Override
            public void onResponse(Call<DrawerCatModelHead> call, Response<DrawerCatModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onDrawerCatListRequestComplete(response.body().getData());
                    } else {
                        requestComplete.onDrawerCatListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onDrawerCatListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<DrawerCatModelHead> call, Throwable t) {
                requestComplete.onDrawerCatListRequestError("Something Went Wrong!");
            }
        });
    }
}
