package com.wm21ltd.wm21.serviceapis;

import com.wm21ltd.wm21.helpers.ConstantValues;
import com.wm21ltd.wm21.interfaces.OnMemberDetailsRequestComplete;
import com.wm21ltd.wm21.networks.ApiUtil.ApiUtils;
import com.wm21ltd.wm21.networks.Models.MemberDetailsModel;
import com.wm21ltd.wm21.networks.Remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeMemberDetailsApi {
    private OnMemberDetailsRequestComplete onMemberDetailsRequestComplete;

    public InvokeMemberDetailsApi(String userId, String rank, String limit,OnMemberDetailsRequestComplete onMemberDetailsRequestComplete) {
        this.onMemberDetailsRequestComplete = onMemberDetailsRequestComplete;

        APIService apiService = ApiUtils.getApiService(ConstantValues.URL);
        apiService.getMemberDetails(userId, rank, limit).enqueue(new Callback<MemberDetailsModel>() {
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
