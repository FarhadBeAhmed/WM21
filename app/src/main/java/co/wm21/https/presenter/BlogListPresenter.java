package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.presenter.interfaces.OnBlogListRequestComplete;
import co.wm21.https.presenter.interfaces.OnBlogListView;
import co.wm21.https.serviceapis.InvokeBlogListApi;

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
