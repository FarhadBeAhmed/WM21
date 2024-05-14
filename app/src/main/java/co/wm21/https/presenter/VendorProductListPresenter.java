package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.Annotations.VendorProductModel;
import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.interfaces.OnVendorProductListRequestComplete;
import co.wm21.https.interfaces.OnVendorProductListView;
import co.wm21.https.serviceapis.InvokeBlogListApi;
import co.wm21.https.serviceapis.InvokeVendorProductListApi;

public class VendorProductListPresenter {
    OnVendorProductListView vendorProductListView;

    public VendorProductListPresenter(OnVendorProductListView vendorProductListView) {
        this.vendorProductListView = vendorProductListView;
    }

    public void VendorProductDataLoad(String id,String limit) {
        vendorProductListView.onVendorProductListStartLoading();
        new InvokeVendorProductListApi(id,limit, new OnVendorProductListRequestComplete() {
            @Override
            public void onVendorProductListRequestComplete(Object obj) {
                vendorProductListView.onVendorProductListStopLoading();
                vendorProductListView.onVendorProductListDataLoad((List<ProductModel>) obj);
            }

            @Override
            public void onVendorProductListRequestError(String errMsg) {
                vendorProductListView.onVendorProductListStopLoading();
                vendorProductListView.onVendorProductListShowMessage(errMsg);
            }
        });
    }
}
