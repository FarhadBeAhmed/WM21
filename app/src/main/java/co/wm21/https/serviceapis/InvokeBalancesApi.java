package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.balanceResponse.BalanceModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnBalancesRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeBalancesApi {
    OnBalancesRequestComplete requestComplete;

    public InvokeBalancesApi(String user_id, final OnBalancesRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.allBalances(user_id).enqueue(new Callback<BalanceModel>() {
            @Override
            public void onResponse(Call<BalanceModel> call, Response<BalanceModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onBalancesRequestComplete(response.body());
                    } else {
                        requestComplete.onBalancesRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onBalancesRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<BalanceModel> call, Throwable t) {
                requestComplete.onBalancesRequestComplete("Something Went Wrong!");
            }
        });
    }
}
