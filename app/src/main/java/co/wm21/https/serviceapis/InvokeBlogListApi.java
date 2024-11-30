package co.wm21.https.serviceapis;

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils;
import co.wm21.https.FHelper.networks.Models.BlogsModelHead;
import co.wm21.https.FHelper.networks.Remote.APIService;
import co.wm21.https.presenter.interfaces.OnBlogListRequestComplete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvokeBlogListApi {
    OnBlogListRequestComplete requestComplete;

    public InvokeBlogListApi(int limit, final OnBlogListRequestComplete requestComplete) {
        this.requestComplete = requestComplete;

        APIService mApiService = ApiUtils.getApiService();
        mApiService.getAllBlogs(limit).enqueue(new Callback<BlogsModelHead>() {
            @Override
            public void onResponse(Call<BlogsModelHead> call, Response<BlogsModelHead> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError() == 0) {
                        requestComplete.onBlogListRequestComplete(response.body().getBlogsModelList());
                    } else {
                        requestComplete.onBlogListRequestError(response.body().getErrorReport());
                    }
                } else {
                    requestComplete.onBlogListRequestError("Something Went Wrong!");
                }

            }

            @Override
            public void onFailure(Call<BlogsModelHead> call, Throwable t) {
                requestComplete.onBlogListRequestError("Something Went Wrong!");
            }
        });
    }
}
