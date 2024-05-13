package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.AppliedProductModelHead;
import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.interfaces.OnAppliedProductsRequestComplete;
import co.wm21.https.interfaces.OnAppliedProductsView;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.serviceapis.InvokeAppliedProductsApi;
import co.wm21.https.serviceapis.InvokeBlogListApi;

public class AppliedProductsPresenter {
    OnAppliedProductsView productsView;

    public AppliedProductsPresenter(OnAppliedProductsView productsView) {
        this.productsView = productsView;
    }

    public void appliedProductsDataLoad(String user_id) {
        productsView.onAppliedProductsStartLoading();
        new InvokeAppliedProductsApi(user_id, new OnAppliedProductsRequestComplete() {
            @Override
            public void onAppliedProductsRequestComplete(Object obj) {
                productsView.onAppliedProductsStopLoading();
                productsView.onAppliedProductsDataLoad((AppliedProductModelHead) obj);
            }

            @Override
            public void onAppliedProductsRequestError(String errMsg) {
                productsView.onAppliedProductsStopLoading();
                productsView.onAppliedProductsShowMessage(errMsg);
            }
        });
    }
}
