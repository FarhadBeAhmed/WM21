package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnProfileDetailsRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeProfileDetailsApi {
    OnProfileDetailsRequestComplete requestComplete;

    public InvokeProfileDetailsApi(String userId, final OnProfileDetailsRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.profileDetails(userId).enqueue(new Callback<ProfileDetailsHead>() {
            @Override
            public void onResponse(Call<ProfileDetailsHead> call, Response<ProfileDetailsHead> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onProfileDetailsRequestComplete(response.body());
                    } else {
                        requestComplete.onProfileDetailsRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onProfileDetailsRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<ProfileDetailsHead> call, Throwable t) {
                requestComplete.onProfileDetailsRequestError("Something Went Wrong!");
            }
        });
    }
}
