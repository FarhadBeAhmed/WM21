package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnSignupFinalRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeSignupFinalApi {
    OnSignupFinalRequestComplete requestComplete;

    public InvokeSignupFinalApi(String mobile,String name,String refer,String email,String division,String country, final OnSignupFinalRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.signUpFinal(mobile,name,refer,email,division,country).enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onSignupFinalRequestComplete(response.body());
                    } else {
                        requestComplete.onSignupFinalRequestError(response.body().getError_report());
                    }
                } else {
                    requestComplete.onSignupFinalRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                requestComplete.onSignupFinalRequestError("Something Went Wrong!");
            }
        });
    }
}
