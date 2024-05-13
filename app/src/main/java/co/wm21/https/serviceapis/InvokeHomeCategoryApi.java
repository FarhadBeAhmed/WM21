package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.HomeTopSliderImageModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.SliderItem;
import co.wm21.https.interfaces.OnHomeTopSliderImageRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeHomeTopSliderImageApi {
    private OnHomeTopSliderImageRequestComplete onHomeTopSliderImageRequestComplete;

    public InvokeHomeTopSliderImageApi(String limit,final OnHomeTopSliderImageRequestComplete onHomeTopSliderImageRequestComplete) {
        this.onHomeTopSliderImageRequestComplete = onHomeTopSliderImageRequestComplete;
        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getHomeTopSlideImageList(limit).enqueue(new Callback<HomeTopSliderImageModelHead>() {
            @Override
            public void onResponse(Call<HomeTopSliderImageModelHead> call, Response<HomeTopSliderImageModelHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0){
                        onHomeTopSliderImageRequestComplete.onHomeTopSliderImageRequestSuccess(response.body().getSliderItems());
                    }else {
                        onHomeTopSliderImageRequestComplete.onHomeTopSliderImageRequestError(response.body().getErrorReport());
                    }

                }else {
                    onHomeTopSliderImageRequestComplete.onHomeTopSliderImageRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<HomeTopSliderImageModelHead> call, Throwable t) {

            }
        });
    }
}
