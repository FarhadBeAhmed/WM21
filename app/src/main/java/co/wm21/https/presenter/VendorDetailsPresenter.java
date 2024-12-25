package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.VendorDetailsModel;
import co.wm21.https.presenter.interfaces.OnVendorDetailsView;
import co.wm21.https.serviceapis.InvokeVendorDetailsApi;

public class VendorDetailsPresenter {
    OnVendorDetailsView vendorDetailsView;

    public VendorDetailsPresenter(OnVendorDetailsView vendorDetailsView) {
        this.vendorDetailsView = vendorDetailsView;
    }

    public void getVendorDetailsDataLoad(String id) {
        vendorDetailsView.onVendorDetailsStartLoading();
        new InvokeVendorDetailsApi(id, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                vendorDetailsView.onVendorDetailsStopLoading();
                vendorDetailsView.onVendorDetailsDataLoad((VendorDetailsModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                vendorDetailsView.onVendorDetailsStopLoading();
                vendorDetailsView.onVendorDetailsShowMessage(errMsg);
            }
        });
    }
}
