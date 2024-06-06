package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.TreeModel;
import co.wm21.https.FHelper.networks.Models.TreesModel;

public interface OnTreeDataView {
    void onTreeDataLoad(TreeModel treeModel);

    void onTreeDataStartLoading();

    void onTreeDataStopLoading();

    void onTreeDataShowMessage(String errMsg);
}
