package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.BrandAmbassadorDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnBrandAmbassadorRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeBrandAmbassadorApi {
    private OnBrandAmbassadorRequestComplete onBrandAmbassadorRequestComplete;

    public InvokeBrandAmbassadorApi(String limit, final OnBrandAmbassadorRequestComplete onBrandAmbassadorRequestComplete) {
        this.onBrandAmbassadorRequestComplete = onBrandAmbassadorRequestComplete;

        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getBrandAmbassador(limit).enqueue(new Callback<BrandAmbassadorDataModel>() {
            @Override
            public void onResponse(Call<BrandAmbassadorDataModel> call, Response<BrandAmbassadorDataModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        onBrandAmbassadorRequestComplete.onBrandAmbassadorRequestComplete(response.body().getAmbassador());
                    } else {
                        onBrandAmbassadorRequestComplete.onBrandAmbassadorRequestError(response.body().getErrorReport());
                    }
                } else {
                    onBrandAmbassadorRequestComplete.onBrandAmbassadorRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<BrandAmbassadorDataModel> call, Throwable t) {
                onBrandAmbassadorRequestComplete.onBrandAmbassadorRequestError("Something went wrong!");
            }
        });
    }
}
