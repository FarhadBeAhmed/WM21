package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.VerificationModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnVerificationRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeVerificationApi {
    OnVerificationRequestComplete requestComplete;

    public InvokeVerificationApi( String user_id, final OnVerificationRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.verification(user_id).enqueue(new Callback<VerificationModel>() {
            @Override
            public void onResponse(Call<VerificationModel> call, Response<VerificationModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onVerificationRequestComplete(response.body());
                    } else {
                        requestComplete.onVerificationRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onVerificationRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<VerificationModel> call, Throwable t) {
                requestComplete.onVerificationRequestError("Something Went Wrong!");
            }
        });
    }
}
