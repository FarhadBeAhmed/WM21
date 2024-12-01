package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.DrawerCatModel;
import co.wm21.https.presenter.interfaces.OnDrawerCatListView;
import co.wm21.https.serviceapis.InvokeDrawerCatListApi;

public class DrawerCatListPresenter {
    OnDrawerCatListView mView;

    public DrawerCatListPresenter(OnDrawerCatListView mView) {
        this.mView = mView;
    }

    public void onDrawerCatDataLoad(int id,String cat_id) {
        mView.onDrawerCatListStartLoading();
        new InvokeDrawerCatListApi(id,cat_id, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                mView.onDrawerCatListStopLoading();
                mView.onDrawerCatListDataLoad((List<DrawerCatModel>) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                mView.onDrawerCatListStopLoading();
                mView.onDrawerCatListShowMessage(errMsg);
            }
        });
    }
}
