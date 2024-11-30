package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.HomeCategoryHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnHomeCategoryRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHomeCategoryApi {
    private OnHomeCategoryRequestComplete onHomeCategoryRequestComplete;

    public InvokeHomeCategoryApi(String limit, final OnHomeCategoryRequestComplete onHomeCategoryRequestComplete) {
        this.onHomeCategoryRequestComplete = onHomeCategoryRequestComplete;
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getCategoryList(limit).enqueue(new Callback<HomeCategoryHead>() {
            @Override
            public void onResponse(Call<HomeCategoryHead> call, Response<HomeCategoryHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHomeCategoryRequestComplete.onHomeCategoryRequestSuccess(response.body().getCategoryList());
                    }else {
                        onHomeCategoryRequestComplete.onHomeCategoryRequestError(response.body().getErrorReport());
                    }

                }else {
                    onHomeCategoryRequestComplete.onHomeCategoryRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<HomeCategoryHead> call, Throwable t) {

            }
        });
    }
}
