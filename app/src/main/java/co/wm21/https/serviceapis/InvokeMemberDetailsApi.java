package co.wm21.https.serviceapis;


import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.MemberDetailsModel;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnMemberDetailsRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeMemberDetailsApi {
    private OnMemberDetailsRequestComplete onMemberDetailsRequestComplete;

    public InvokeMemberDetailsApi(String userId, String rank, String limit, OnMemberDetailsRequestComplete onMemberDetailsRequestComplete) {
        this.onMemberDetailsRequestComplete = onMemberDetailsRequestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getMemberDetails(userId, rank, limit).enqueue(new Callback<MemberDetailsModel>() {
            @Override
            public void onResponse(Call<MemberDetailsModel> call, Response<MemberDetailsModel> response) {
                if (response.isSuccessful()){
                    if (response.body().getError()==0){
                        onMemberDetailsRequestComplete.onMemberDetailsRequestSuccess(response.body().getSmsList());
                    } else {
                        onMemberDetailsRequestComplete.onMemberDetailsRequestError(response.body().getErrorReport());
                    }
                } else {
                    onMemberDetailsRequestComplete.onMemberDetailsRequestError("Something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<MemberDetailsModel> call, Throwable t) {
                onMemberDetailsRequestComplete.onMemberDetailsRequestError("Something went wrong!");
            }
        });
    }
}
