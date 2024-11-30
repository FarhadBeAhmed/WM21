package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.RelatedProductModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnRelatedProductListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRelatedProductListApi {
    OnRelatedProductListRequestComplete requestComplete;

    public InvokeRelatedProductListApi(String limit,String cat_id,String scat_id,String brand_id, final OnRelatedProductListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getRelatedProduct(limit,cat_id,scat_id,brand_id).enqueue(new Callback<RelatedProductModelHead>() {
            @Override
            public void onResponse(Call<RelatedProductModelHead> call, Response<RelatedProductModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onRelatedProductListRequestComplete(response.body().getData());
                    } else {
                        requestComplete.onRelatedProductListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRelatedProductListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<RelatedProductModelHead> call, Throwable t) {
                requestComplete.onRelatedProductListRequestError("Something Went Wrong!");
            }
        });
    }
}
