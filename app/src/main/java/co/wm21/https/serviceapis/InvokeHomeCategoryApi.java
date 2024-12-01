package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.HomeCategoryHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHomeCategoryApi {
    private OnRequestComplete onHomeCategoryRequestComplete;

    public InvokeHomeCategoryApi(String limit, final OnRequestComplete onHomeCategoryRequestComplete) {
        this.onHomeCategoryRequestComplete = onHomeCategoryRequestComplete;
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getCategoryList(limit).enqueue(new Callback<HomeCategoryHead>() {
            @Override
            public void onResponse(Call<HomeCategoryHead> call, Response<HomeCategoryHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHomeCategoryRequestComplete.onRequestSuccess(response.body().getCategoryList());
                    }else {
                        onHomeCategoryRequestComplete.onRequestError(response.body().getErrorReport());
                    }

                }else {
                    onHomeCategoryRequestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<HomeCategoryHead> call, Throwable t) {

            }
        });
    }
}
