package co.wm21.https.presenter.interfaces;



import java.util.List;

import co.wm21.https.FHelper.networks.Models.BrandAmbassadorListModel;

public interface OnBrandAmbassadorListView {

    void onBrandAmbassadorReqData (List<BrandAmbassadorListModel> brandAmbassadorList);

    void onBrandAmbassadorStartLoading();

    void onBrandAmbassadorStopLoading();

    void onBrandAmbassadorShowMessage(String msg);
}
