package co.wm21.https.serviceapis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnThanaListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeThanaListApi {
    OnThanaListRequestComplete requestComplete;

    public InvokeThanaListApi(String countryID, String divisionID, String districtID, final OnThanaListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getThanaList("",districtID,divisionID,countryID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if (response.body().get("error").getAsInt() == 0){
                        JsonArray jsonArray = new JsonArray();
                        jsonArray = response.body().get("report").getAsJsonArray();
                        requestComplete.onThanaListRequestSuccess(jsonArray);
                    } else {
                        requestComplete.onThanaListRequestError(response.body().get("error_report").toString());
                    }
                } else {
                    requestComplete.onThanaListRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestComplete.onThanaListRequestError("Something went wrong!");
            }
        });
    }
}
