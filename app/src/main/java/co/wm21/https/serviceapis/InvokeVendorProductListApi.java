package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.VendorProductModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnVendorProductListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeVendorProductListApi {
    OnVendorProductListRequestComplete requestComplete;

    public InvokeVendorProductListApi(String id,String limit, final OnVendorProductListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getVendorProduct(id,limit).enqueue(new Callback<VendorProductModelHead>() {
            @Override
            public void onResponse(Call<VendorProductModelHead> call, Response<VendorProductModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onVendorProductListRequestComplete(response.body().getData());
                    } else {
                        requestComplete.onVendorProductListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onVendorProductListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<VendorProductModelHead> call, Throwable t) {
                requestComplete.onVendorProductListRequestError("Something Went Wrong!");
            }
        });
    }
}
