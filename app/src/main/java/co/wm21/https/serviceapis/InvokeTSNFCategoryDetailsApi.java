package co.wm21.https.serviceapis;


import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.TSNFCategoryDetailsModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeTSNFCategoryDetailsApi {
    private OnRequestComplete onTSNFCategoryDetailsRequestComplete;

    public InvokeTSNFCategoryDetailsApi(String type, String category, String limit, final OnRequestComplete onTSNFCategoryDetailsRequestComplete) {
        this.onTSNFCategoryDetailsRequestComplete = onTSNFCategoryDetailsRequestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getTSNFCategoryDetails(type,category,limit).enqueue(new Callback<TSNFCategoryDetailsModel>() {
            @Override
            public void onResponse(Call<TSNFCategoryDetailsModel> call, Response<TSNFCategoryDetailsModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        onTSNFCategoryDetailsRequestComplete.onRequestSuccess(response.body().getServiceTraining());
                    }
                    else {
                        onTSNFCategoryDetailsRequestComplete.onRequestError(response.body().getErrorReport());
                    }
                }
                else {
                    onTSNFCategoryDetailsRequestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<TSNFCategoryDetailsModel> call, Throwable t) {
                onTSNFCategoryDetailsRequestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
