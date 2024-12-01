package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.BrandAmbassadorDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeBrandAmbassadorApi {
    private OnRequestComplete onBrandAmbassadorRequestComplete;

    public InvokeBrandAmbassadorApi(String limit, final OnRequestComplete onBrandAmbassadorRequestComplete) {
        this.onBrandAmbassadorRequestComplete = onBrandAmbassadorRequestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getBrandAmbassador(limit).enqueue(new Callback<BrandAmbassadorDataModel>() {
            @Override
            public void onResponse(Call<BrandAmbassadorDataModel> call, Response<BrandAmbassadorDataModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        onBrandAmbassadorRequestComplete.onRequestSuccess(response.body().getAmbassador());
                    } else {
                        onBrandAmbassadorRequestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    onBrandAmbassadorRequestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<BrandAmbassadorDataModel> call, Throwable t) {
                onBrandAmbassadorRequestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
