package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.VendorDetailsModel;

public interface OnVendorDetailsView {
    void onVendorDetailsDataLoad(VendorDetailsModel vendorDetailsModels);

    void onVendorDetailsStartLoading();

    void onVendorDetailsStopLoading();

    void onVendorDetailsShowMessage(String errmsg);
}
