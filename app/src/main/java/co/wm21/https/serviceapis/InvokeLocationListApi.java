package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.BlogsModelHead;
import co.wm21.https.FHelper.networks.Models.LocationModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnLocationListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeLocationListApi {
    OnLocationListRequestComplete requestComplete;

    public InvokeLocationListApi(String id,String value, final OnLocationListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.location(id,value).enqueue(new Callback<LocationModelHead>() {
            @Override
            public void onResponse(Call<LocationModelHead> call, Response<LocationModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onLocationListRequestComplete(response.body().getData());
                    } else {
                        requestComplete.onLocationListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onLocationListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<LocationModelHead> call, Throwable t) {
                requestComplete.onLocationListRequestError("Something Went Wrong!");
            }
        });
    }
}
