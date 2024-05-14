package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.VendorDetailsModel;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.interfaces.OnVendorDetailsRequestComplete;
import co.wm21.https.interfaces.OnVendorDetailsView;
import co.wm21.https.serviceapis.InvokeBlogListApi;
import co.wm21.https.serviceapis.InvokeVendorDetailsApi;

public class VendorDetailsPresenter {
    OnVendorDetailsView vendorDetailsView;

    public VendorDetailsPresenter(OnVendorDetailsView vendorDetailsView) {
        this.vendorDetailsView = vendorDetailsView;
    }

    public void getVendorDetailsDataLoad(String id) {
        vendorDetailsView.onVendorDetailsStartLoading();
        new InvokeVendorDetailsApi(id, new OnVendorDetailsRequestComplete() {
            @Override
            public void onVendorDetailsRequestComplete(Object obj) {
                vendorDetailsView.onVendorDetailsStopLoading();
                vendorDetailsView.onVendorDetailsDataLoad((VendorDetailsModel) obj);
            }

            @Override
            public void onVendorDetailsRequestError(String errMsg) {
                vendorDetailsView.onVendorDetailsStopLoading();
                vendorDetailsView.onVendorDetailsShowMessage(errMsg);
            }
        });
    }
}
