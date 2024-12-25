package co.wm21.https.serviceapis;


import android.util.Log;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.TrainingServiceNewsDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTrainingServiceNewsApi {
    private OnRequestComplete onTrainingServiceNewsRequestComplete;

    public InvokeTrainingServiceNewsApi(String type, String limit, final OnRequestComplete onTrainingServiceNewsRequestComplete) {
        this.onTrainingServiceNewsRequestComplete = onTrainingServiceNewsRequestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getTrainingServiceNews(type,limit).enqueue(new Callback<TrainingServiceNewsDataModel>() {
            @Override
            public void onResponse(Call<TrainingServiceNewsDataModel> call, Response<TrainingServiceNewsDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        onTrainingServiceNewsRequestComplete.onRequestSuccess(response.body().getCategory());
                    }
                    else {
                        onTrainingServiceNewsRequestComplete.onRequestError(response.body().getErrorReport());
                    }
                }
                else {
                    onTrainingServiceNewsRequestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<TrainingServiceNewsDataModel> call, Throwable t) {
                Log.d("tag", "onFailure: "+t.getMessage());
                onTrainingServiceNewsRequestComplete.onRequestError("Something went wrong!");

            }
        });
    }
}
