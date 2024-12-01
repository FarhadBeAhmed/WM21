package co.wm21.https.serviceapis;

import com.google.gson.JsonObject;

import java.util.HashMap;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeFranchiseApplicationApi {
    OnRequestComplete requestComplete;

    public InvokeFranchiseApplicationApi(String serviceID, String userID, String formType,
                                         String divisionID, String districtID, String thanaID,
                                         String appName, String appAddress, String appLicense, String category,
                                         final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;
        APIService mApiService = ApiUtils.getApiService();
        mApiService.submitFranchiseApplication(serviceID, userID, formType, divisionID, districtID, thanaID, appName,
                appAddress, appLicense, category).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().get("error").getAsInt() == 0) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("error", response.body().get("error").getAsInt() + "");
                        hashMap.put("error_report", response.body().get("error_report").getAsString());
                        requestComplete.onRequestSuccess(hashMap);
                    } else {
                        requestComplete.onRequestError(response.body().get("error_report").toString());
                    }
                } else {
                    requestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                requestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
