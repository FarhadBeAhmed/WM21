package co.wm21.https.serviceapis;



import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.RewardGalleryDataModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.interfaces.OnRewardGalleryRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeRewardGalleryApi {
    OnRewardGalleryRequestComplete requestComplete;

    public InvokeRewardGalleryApi(String userID, String limitData, final OnRewardGalleryRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService(ConstantValues.URL);
        mApiService.getRewardGallery(userID, limitData).enqueue(new Callback<RewardGalleryDataModel>() {
            @Override
            public void onResponse(Call<RewardGalleryDataModel> call, Response<RewardGalleryDataModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        requestComplete.onRewardGalleryRequestSuccess(response.body().getServiceTraining());
                    } else {
                        requestComplete.onRewardGalleryRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onRewardGalleryRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<RewardGalleryDataModel> call, Throwable t) {
                requestComplete.onRewardGalleryRequestError("Something went wrong!");
            }
        });
    }
}
