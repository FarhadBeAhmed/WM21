package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnSignupFirstRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeSignupFirstApi {
    OnSignupFirstRequestComplete requestComplete;

    public InvokeSignupFirstApi(String pId, String userId, final OnSignupFirstRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.signUpFirstStep(pId,userId).enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onSignupFirstRequestComplete(response.body());
                    } else {
                        requestComplete.onSignupFirstRequestError(response.body().getError_report());
                    }
                } else {
                    requestComplete.onSignupFirstRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                requestComplete.onSignupFirstRequestError("Something Went Wrong!");
            }
        });
    }
}
