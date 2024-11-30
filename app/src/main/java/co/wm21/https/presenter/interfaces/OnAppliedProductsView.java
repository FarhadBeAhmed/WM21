package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.AppliedProductModelHead;
import co.wm21.https.FHelper.networks.Models.BlogsModel;

public interface OnAppliedProductsView {
    void onAppliedProductsDataLoad(AppliedProductModelHead appliedProductModelHeads);

    void onAppliedProductsStartLoading();

    void onAppliedProductsStopLoading();

    void onAppliedProductsShowMessage(String errmsg);
}
