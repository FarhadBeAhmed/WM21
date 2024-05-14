package co.wm21.https.presenter;


import java.util.List;

import co.wm21.https.FHelper.networks.Models.BrandAmbassadorListModel;
import co.wm21.https.interfaces.OnBrandAmbassadorListView;
import co.wm21.https.interfaces.OnBrandAmbassadorRequestComplete;
import co.wm21.https.serviceapis.InvokeBrandAmbassadorApi;

public class BrandAmbassadorPresenter {
    private OnBrandAmbassadorListView onBrandAmbassadorListView;

    public BrandAmbassadorPresenter(OnBrandAmbassadorListView onBrandAmbassadorListView) {
        this.onBrandAmbassadorListView = onBrandAmbassadorListView;
    }

    public void brandAmbassadorDataResponse (String limit) {

        onBrandAmbassadorListView.onBrandAmbassadorStartLoading();
        new InvokeBrandAmbassadorApi(limit, new OnBrandAmbassadorRequestComplete() {
            @Override
            public void onBrandAmbassadorRequestComplete(Object obj) {
                onBrandAmbassadorListView.onBrandAmbassadorStopLoading();
                onBrandAmbassadorListView.onBrandAmbassadorReqData((List<BrandAmbassadorListModel>) obj);
            }

            @Override
            public void onBrandAmbassadorRequestError(String errMsg) {
                onBrandAmbassadorListView.onBrandAmbassadorStopLoading();
                onBrandAmbassadorListView.onBrandAmbassadorShowMessage(errMsg);
            }
        });
    }
}
