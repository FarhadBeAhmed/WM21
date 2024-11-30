package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.EshopListModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnEshopListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeEshopListApi {
    OnEshopListRequestComplete requestComplete;

    public InvokeEshopListApi( String user_id,String location,String locationType,String searchTxt, final OnEshopListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.order_eshop(user_id,location,locationType,searchTxt).enqueue(new Callback<EshopListModelHead>() {
            @Override
            public void onResponse(Call<EshopListModelHead> call, Response<EshopListModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onEshopListRequestComplete(response.body().getData());
                    } else {
                        requestComplete.onEshopListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onEshopListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<EshopListModelHead> call, Throwable t) {
                requestComplete.onEshopListRequestError("Something Went Wrong!");
            }
        });
    }
}
