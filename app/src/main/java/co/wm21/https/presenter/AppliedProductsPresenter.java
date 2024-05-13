package co.wm21.https.presenter;

import com.google.gson.JsonArray;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.interfaces.OnDivisionListRequestComplete;
import co.wm21.https.interfaces.OnDivisionListView;
import co.wm21.https.serviceapis.InvokeBlogListApi;
import co.wm21.https.serviceapis.InvokeDivisionListApi;

public class BlogListPresenter {
    OnBlogListView mView;

    public BlogListPresenter(OnBlogListView mView) {
        this.mView = mView;
    }

    public void BlogDataLoad(int limit) {
        mView.onBlogListStartLoading();
        new InvokeBlogListApi(limit, new OnBlogListRequestComplete() {
            @Override
            public void onBlogListRequestComplete(Object obj) {
                mView.onBlogListStopLoading();
                mView.onBlogListDataLoad((List<BlogsModel>) obj);
            }

            @Override
            public void onBlogListRequestError(String errMsg) {
                mView.onBlogListStopLoading();
                mView.onBlogListShowMessage(errMsg);
            }
        });
    }
}
