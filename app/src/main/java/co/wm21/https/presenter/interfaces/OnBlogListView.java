package co.wm21.https.presenter.interfaces;

import com.google.gson.JsonArray;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;

public interface OnBlogListView {
    void onBlogListDataLoad(List<BlogsModel> blogs);

    void onBlogListStartLoading();

    void onBlogListStopLoading();

    void onBlogListShowMessage(String errmsg);
}
