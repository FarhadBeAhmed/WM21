package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.BlogsModelHead;
import co.wm21.https.FHelper.networks.Models.RatingSubmitModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnRatingSubmitRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRatingSubmitApi {
    OnRatingSubmitRequestComplete requestComplete;

    public InvokeRatingSubmitApi(String username,String serial,String rating,String review, final OnRatingSubmitRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getRatingSubmit(username,serial,review,rating).enqueue(new Callback<RatingSubmitModelHead>() {
            @Override
            public void onResponse(Call<RatingSubmitModelHead> call, Response<RatingSubmitModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onRatingSubmitRequestComplete(response.body());
                    } else {
                        requestComplete.onRatingSubmitRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRatingSubmitRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<RatingSubmitModelHead> call, Throwable t) {
                requestComplete.onRatingSubmitRequestError("Something Went Wrong!");
            }
        });
    }
}
