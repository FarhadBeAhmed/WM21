package co.wm21.https.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.AppliedProductModelHead;
import co.wm21.https.FHelper.networks.Models.TopSellingProModel;
import co.wm21.https.FHelper.networks.Models.TopSellingProModelHead;

public interface OnTopSellingProView {
    void onTopSellingProDataLoad(List<TopSellingProModel> topSellingProModel);

    void onTopSellingProStartLoading();

    void onTopSellingProStopLoading();

    void onTopSellingProShowMessage(String errmsg);
}
