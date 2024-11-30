package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.LoginModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnLoginRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeLoginApi {
    OnLoginRequestComplete requestComplete;

    public InvokeLoginApi(String user, String pass,  final OnLoginRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.login(user,pass).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onLoginRequestComplete(response.body());
                    } else {
                        requestComplete.onLoginRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onLoginRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                requestComplete.onLoginRequestError("Something Went Wrong!");
            }
        });
    }
}
