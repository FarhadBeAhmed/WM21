package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.TreeModel;
import co.wm21.https.FHelper.networks.Models.TreesModel;
import co.wm21.https.interfaces.OnTreeDataRequestComplete;
import co.wm21.https.interfaces.OnTreeDataView;
import co.wm21.https.interfaces.OnTreesListRequestComplete;
import co.wm21.https.interfaces.OnTreesListView;
import co.wm21.https.serviceapis.InvokeTreeDataApi;
import co.wm21.https.serviceapis.InvokeTreesListApi;

public class TreeDataPresenter {
    OnTreeDataView treeDataView;

    public TreeDataPresenter(OnTreeDataView treeDataView) {
        this.treeDataView = treeDataView;
    }

    public void TreeDataLoad(String deviceID,String user_id) {
        treeDataView.onTreeDataStartLoading();
        new InvokeTreeDataApi(deviceID,user_id, new OnTreeDataRequestComplete() {

            @Override
            public void onTreeDataRequestComplete(Object obj) {
                treeDataView.onTreeDataStopLoading();
                treeDataView.onTreeDataLoad((TreeModel) obj);
            }

            @Override
            public void onTreeDataRequestError(String errMsg) {
                treeDataView.onTreeDataStopLoading();
                treeDataView.onTreeDataShowMessage(errMsg);

            }
        });
    }
}
