package co.wm21.https.presenter;



import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.TSNFCategoryDetailsListModel;
import co.wm21.https.presenter.interfaces.OnTSNFCategoryDetailsListView;
import co.wm21.https.serviceapis.InvokeTSNFCategoryDetailsApi;

public class TSNFCategoryDetailsPresenter {
    private OnTSNFCategoryDetailsListView onTSNFCategoryDetailsListView;

    public TSNFCategoryDetailsPresenter(OnTSNFCategoryDetailsListView onTSNFCategoryDetailsListView) {
        this.onTSNFCategoryDetailsListView = onTSNFCategoryDetailsListView;
    }

    public void tsnfCategoryDetailsDataResponse (String type, String category, String limit){
        onTSNFCategoryDetailsListView.onTSNFCategoryDetailsStartLoading();
        new InvokeTSNFCategoryDetailsApi(type, category, limit, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onTSNFCategoryDetailsListView.onTSNFCategoryDetailsStopLoading();
                onTSNFCategoryDetailsListView.onTSNFCategoryDetailsReqData((List<TSNFCategoryDetailsListModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onTSNFCategoryDetailsListView.onTSNFCategoryDetailsStopLoading();
                onTSNFCategoryDetailsListView.oonTSNFCategoryDetailsShowMessage(errMsg);
            }
        });
    }
}
