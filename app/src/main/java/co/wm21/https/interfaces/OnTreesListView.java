package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.FHelper.networks.Models.TreesModel;

public interface OnTreesListView {
    void onTreesListDataLoad(TreesModel treesModel);

    void onTreesListStartLoading();

    void onTreesListStopLoading();

    void onTreesListShowMessage(String errmsg);
}
