package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.SignupNumberVerifyModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnSignupNumberVerifyRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeSignupNumberVerifyApi {
    OnSignupNumberVerifyRequestComplete requestComplete;

    public InvokeSignupNumberVerifyApi(String mobile,String country,String code, final OnSignupNumberVerifyRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.signUpNumberVerify(mobile,country,code).enqueue(new Callback<SignupNumberVerifyModel>() {
            @Override
            public void onResponse(Call<SignupNumberVerifyModel> call, Response<SignupNumberVerifyModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onSignupNumberVerifyRequestComplete(response.body());
                    } else {
                        requestComplete.onSignupNumberVerifyRequestError(response.body().getError_report());
                    }
                } else {
                    requestComplete.onSignupNumberVerifyRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<SignupNumberVerifyModel> call, Throwable t) {
                requestComplete.onSignupNumberVerifyRequestError("Something Went Wrong!");
            }
        });
    }
}
