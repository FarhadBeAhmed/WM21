package co.wm21.https.serviceapis;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnDivisionListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeDivisionListApi {
    OnDivisionListRequestComplete requestComplete;

    public InvokeDivisionListApi(String counrtyID, final OnDivisionListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getDivisionList("", counrtyID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("error").getAsInt() == 0) {
                        JsonArray jsonArray = new JsonArray();
                        jsonArray = response.body().get("report").getAsJsonArray();
                        requestComplete.onDivisionListRequestComplete(jsonArray);
                    } else {
                        requestComplete.onDevisionListRequestError(response.body().get("error_report").toString());
                    }
                } else {
                    requestComplete.onDevisionListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestComplete.onDevisionListRequestError("Something Went Wrong!");
            }
        });
    }
}
