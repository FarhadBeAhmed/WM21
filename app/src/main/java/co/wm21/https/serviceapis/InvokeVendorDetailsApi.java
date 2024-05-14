package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.BlogsModelHead;
import co.wm21.https.FHelper.networks.Models.VendorDetailsModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnVendorDetailsRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeVendorDetailsApi {
    OnVendorDetailsRequestComplete requestComplete;

    public InvokeVendorDetailsApi(String id, final OnVendorDetailsRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getVendorDetails(id).enqueue(new Callback<VendorDetailsModelHead>() {
            @Override
            public void onResponse(Call<VendorDetailsModelHead> call, Response<VendorDetailsModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onVendorDetailsRequestComplete(response.body().getData());
                    } else {
                        requestComplete.onVendorDetailsRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onVendorDetailsRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<VendorDetailsModelHead> call, Throwable t) {
                requestComplete.onVendorDetailsRequestError("Something Went Wrong!");
            }
        });
    }
}
