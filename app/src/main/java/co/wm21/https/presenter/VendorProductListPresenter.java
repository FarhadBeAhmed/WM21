package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.presenter.interfaces.OnVendorProductListView;
import co.wm21.https.serviceapis.InvokeVendorProductListApi;

public class VendorProductListPresenter {
    OnVendorProductListView vendorProductListView;

    public VendorProductListPresenter(OnVendorProductListView vendorProductListView) {
        this.vendorProductListView = vendorProductListView;
    }

    public void VendorProductDataLoad(String id,String limit) {
        vendorProductListView.onVendorProductListStartLoading();
        new InvokeVendorProductListApi(id,limit, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                vendorProductListView.onVendorProductListStopLoading();
                vendorProductListView.onVendorProductListDataLoad((List<ProductModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                vendorProductListView.onVendorProductListStopLoading();
                vendorProductListView.onVendorProductListShowMessage(errMsg);
            }
        });
    }
}
