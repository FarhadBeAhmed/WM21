package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.UpdateQty;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnUpdateQtyRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeUpdateQtyApi {
    OnUpdateQtyRequestComplete requestComplete;

    public InvokeUpdateQtyApi( String userId,String pId, String color, String size, int qty,int type, final OnUpdateQtyRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.update_quantity(userId,pId,color,size,qty,type).enqueue(new Callback<UpdateQty>() {
            @Override
            public void onResponse(Call<UpdateQty> call, Response<UpdateQty> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getError()==0 || response.body().getError()==2 ) {
                        requestComplete.onUpdateQtyRequestComplete(response.body());
                    } else {
                        requestComplete.onUpdateQtyRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onUpdateQtyRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<UpdateQty> call, Throwable t) {
                requestComplete.onUpdateQtyRequestError("Something Went Wrong!");
            }
        });
    }
}
