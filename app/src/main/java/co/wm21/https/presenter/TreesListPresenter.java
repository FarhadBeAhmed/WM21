package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.TreesModel;
import co.wm21.https.presenter.interfaces.OnTreesListRequestComplete;
import co.wm21.https.presenter.interfaces.OnTreesListView;
import co.wm21.https.serviceapis.InvokeTreesListApi;

public class TreesListPresenter {
    OnTreesListView treesListView;

    public TreesListPresenter(OnTreesListView treesListView) {
        this.treesListView = treesListView;
    }

    public void TreesDataLoad(String deviceID) {
        treesListView.onTreesListStartLoading();
        new InvokeTreesListApi(deviceID, new OnTreesListRequestComplete() {

            @Override
            public void onTreesListRequestComplete(Object obj) {
                treesListView.onTreesListStopLoading();
                treesListView.onTreesListDataLoad((TreesModel) obj);
            }

            @Override
            public void onTreesListRequestError(String errMsg) {
                treesListView.onTreesListStopLoading();
                treesListView.onTreesListShowMessage(errMsg);

            }
        });
    }
}
