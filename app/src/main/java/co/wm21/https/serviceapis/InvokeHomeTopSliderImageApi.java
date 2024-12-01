package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.HomeTopSliderImageModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHomeTopSliderImageApi {
    private OnRequestComplete onHomeTopSliderImageRequestComplete;

    public InvokeHomeTopSliderImageApi(String limit,final OnRequestComplete onHomeTopSliderImageRequestComplete) {
        this.onHomeTopSliderImageRequestComplete = onHomeTopSliderImageRequestComplete;
        APIService mApiService = ApiUtils.getApiService();
        mApiService.getHomeTopSlideImageList(limit).enqueue(new Callback<HomeTopSliderImageModelHead>() {
            @Override
            public void onResponse(Call<HomeTopSliderImageModelHead> call, Response<HomeTopSliderImageModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHomeTopSliderImageRequestComplete.onRequestSuccess(response.body().getSliderItems());
                    }else {
                        onHomeTopSliderImageRequestComplete.onRequestError(response.body().getErrorReport());
                    }

                }else {
                    onHomeTopSliderImageRequestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<HomeTopSliderImageModelHead> call, Throwable t) {

            }
        });
    }
}
