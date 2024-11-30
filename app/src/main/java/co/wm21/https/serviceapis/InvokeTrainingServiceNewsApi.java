package co.wm21.https.serviceapis;


import android.util.Log;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.TrainingServiceNewsDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnTrainingServiceNewsRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTrainingServiceNewsApi {
    private OnTrainingServiceNewsRequestComplete onTrainingServiceNewsRequestComplete;

    public InvokeTrainingServiceNewsApi(String type, String limit, final OnTrainingServiceNewsRequestComplete onTrainingServiceNewsRequestComplete) {
        this.onTrainingServiceNewsRequestComplete = onTrainingServiceNewsRequestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getTrainingServiceNews(type,limit).enqueue(new Callback<TrainingServiceNewsDataModel>() {
            @Override
            public void onResponse(Call<TrainingServiceNewsDataModel> call, Response<TrainingServiceNewsDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        onTrainingServiceNewsRequestComplete.onTrainingServiceNewsRequestSuccess(response.body().getCategory());
                    }
                    else {
                        onTrainingServiceNewsRequestComplete.onTrainingServiceNewsRequestError(response.body().getErrorReport());
                    }
                }
                else {
                    onTrainingServiceNewsRequestComplete.onTrainingServiceNewsRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<TrainingServiceNewsDataModel> call, Throwable t) {
                Log.d("tag", "onFailure: "+t.getMessage());
                onTrainingServiceNewsRequestComplete.onTrainingServiceNewsRequestError("Something went wrong!");

            }
        });
    }
}
