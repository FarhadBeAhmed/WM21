package com.wm21ltd.wm21.presenters;

import com.wm21ltd.wm21.interfaces.OnTSNFCategoryDetailsListView;
import com.wm21ltd.wm21.interfaces.OnTSNFCategoryDetailsRequestComplete;
import com.wm21ltd.wm21.networks.Models.TSNFCategoryDetailsListModel;
import com.wm21ltd.wm21.serviceapis.InvokeTSNFCategoryDetailsApi;

import java.util.List;

public class TSNFCategoryDetailsPresenter {
    private OnTSNFCategoryDetailsListView onTSNFCategoryDetailsListView;

    public TSNFCategoryDetailsPresenter(OnTSNFCategoryDetailsListView onTSNFCategoryDetailsListView) {
        this.onTSNFCategoryDetailsListView = onTSNFCategoryDetailsListView;
    }

    public void tsnfCategoryDetailsDataResponse (String type, String category, String limit){
        onTSNFCategoryDetailsListView.onTSNFCategoryDetailsStartLoading();
        new InvokeTSNFCategoryDetailsApi(type, category, limit, new OnTSNFCategoryDetailsRequestComplete() {
            @Override
            public void onTSNFCategoryDetailsRequestSuccess(Object obj) {
                onTSNFCategoryDetailsListView.onTSNFCategoryDetailsStopLoading();
                onTSNFCategoryDetailsListView.onTSNFCategoryDetailsReqData((List<TSNFCategoryDetailsListModel>) obj);
            }

            @Override
            public void onTSNFCategoryDetailsRequestError(String errMsg) {
                onTSNFCategoryDetailsListView.onTSNFCategoryDetailsStopLoading();
                onTSNFCategoryDetailsListView.oonTSNFCategoryDetailsShowMessage(errMsg);
            }
        });
    }
}
