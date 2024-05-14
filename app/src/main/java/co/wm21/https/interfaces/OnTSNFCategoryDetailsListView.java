package co.wm21.https.interfaces;


import java.util.List;

import co.wm21.https.FHelper.networks.Models.TSNFCategoryDetailsListModel;

public interface OnTSNFCategoryDetailsListView {

    void onTSNFCategoryDetailsReqData(List<TSNFCategoryDetailsListModel> tsnfCategoryDetailsListModels);

    void onTSNFCategoryDetailsStartLoading();

    void onTSNFCategoryDetailsStopLoading();

    void oonTSNFCategoryDetailsShowMessage(String msg);
}
