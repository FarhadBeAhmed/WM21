package co.wm21.https.interfaces;

import java.util.List;

import co.wm21.https.FHelper.Annotations.VendorProductModel;
import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;

public interface OnVendorProductListView {
    void onVendorProductListDataLoad(List<ProductModel> vendorProductModels);

    void onVendorProductListStartLoading();

    void onVendorProductListStopLoading();

    void onVendorProductListShowMessage(String errmsg);
}
