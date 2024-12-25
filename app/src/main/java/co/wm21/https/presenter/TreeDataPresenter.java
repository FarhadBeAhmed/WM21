package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.TreeModel;
import co.wm21.https.presenter.interfaces.OnTreeDataView;
import co.wm21.https.serviceapis.InvokeTreeDataApi;

public class TreeDataPresenter {
    OnTreeDataView treeDataView;

    public TreeDataPresenter(OnTreeDataView treeDataView) {
        this.treeDataView = treeDataView;
    }

    public void TreeDataLoad(String deviceID,String user_id) {
        treeDataView.onTreeDataStartLoading();
        new InvokeTreeDataApi(deviceID,user_id, new OnRequestComplete() {

            @Override
            public void onRequestSuccess(Object obj) {
                treeDataView.onTreeDataStopLoading();
                treeDataView.onTreeDataLoad((TreeModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                treeDataView.onTreeDataStopLoading();
                treeDataView.onTreeDataShowMessage(errMsg);

            }
        });
    }
}
