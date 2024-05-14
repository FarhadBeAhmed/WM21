package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.interfaces.OnRelatedProductListRequestComplete;
import co.wm21.https.interfaces.OnRelatedProductListView;
import co.wm21.https.serviceapis.InvokeBlogListApi;
import co.wm21.https.serviceapis.InvokeRelatedProductListApi;

public class RelatedProductListPresenter {
    OnRelatedProductListView relatedProductListView;

    public RelatedProductListPresenter(OnRelatedProductListView relatedProductListView) {
        this.relatedProductListView = relatedProductListView;
    }

    public void RelatedProductDataLoad(String limit,String cat_id,String scat_id,String brand_id) {
        relatedProductListView.onRelatedProductListStartLoading();
        new InvokeRelatedProductListApi(limit,cat_id,scat_id,brand_id, new OnRelatedProductListRequestComplete() {
            @Override
            public void onRelatedProductListRequestComplete(Object obj) {
                relatedProductListView.onRelatedProductListStopLoading();
                relatedProductListView.onRelatedProductListDataLoad((List<ProductModel>) obj);
            }

            @Override
            public void onRelatedProductListRequestError(String errMsg) {
                relatedProductListView.onRelatedProductListStopLoading();
                relatedProductListView.onRelatedProductListShowMessage(errMsg);
            }
        });
    }
}
