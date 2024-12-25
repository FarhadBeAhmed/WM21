package co.wm21.https.serviceapis;


import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.RewardGalleryDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardGalleryApi {
    OnRequestComplete requestComplete;

    public InvokeRewardGalleryApi(String userID, String limitData, final OnRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getRewardGallery(userID, limitData).enqueue(new Callback<RewardGalleryDataModel>() {
            @Override
            public void onResponse(Call<RewardGalleryDataModel> call, Response<RewardGalleryDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        requestComplete.onRequestSuccess(response.body().getServiceTraining());
                    } else {
                        requestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardGalleryDataModel> call, Throwable t) {
                requestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
