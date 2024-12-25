package co.wm21.https.serviceapis;


import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.MemberDetailsModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeMemberDetailsApi {
    private OnRequestComplete onMemberDetailsRequestComplete;

    public InvokeMemberDetailsApi(String userId, String rank, String limit, OnRequestComplete onMemberDetailsRequestComplete) {
        this.onMemberDetailsRequestComplete = onMemberDetailsRequestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getMemberDetails(userId, rank, limit).enqueue(new Callback<MemberDetailsModel>() {
            @Override
            public void onResponse(Call<MemberDetailsModel> call, Response<MemberDetailsModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        onMemberDetailsRequestComplete.onRequestSuccess(response.body().getSmsList());
                    } else {
                        onMemberDetailsRequestComplete.onRequestError(response.body().getErrorReport());
                    }
                } else {
                    onMemberDetailsRequestComplete.onRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<MemberDetailsModel> call, Throwable t) {
                onMemberDetailsRequestComplete.onRequestError("Something went wrong!");
            }
        });
    }
}
