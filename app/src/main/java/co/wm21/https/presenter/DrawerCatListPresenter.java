package co.wm21.https.presenter;

import com.google.gson.JsonArray;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.DrawerCatModel;
import co.wm21.https.interfaces.OnDivisionListRequestComplete;
import co.wm21.https.interfaces.OnDivisionListView;
import co.wm21.https.interfaces.OnDrawerCatListRequestComplete;
import co.wm21.https.interfaces.OnDrawerCatListView;
import co.wm21.https.serviceapis.InvokeDivisionListApi;
import co.wm21.https.serviceapis.InvokeDrawerCatListApi;

public class DrawerCatListPresenter {
    OnDrawerCatListView mView;

    public DrawerCatListPresenter(OnDrawerCatListView mView) {
        this.mView = mView;
    }

    public void onDrawerCatDataLoad(int id,String cat_id) {
        mView.onDrawerCatListStartLoading();
        new InvokeDrawerCatListApi(id,cat_id, new OnDrawerCatListRequestComplete() {
            @Override
            public void onDrawerCatListRequestComplete(Object obj) {
                mView.onDrawerCatListStopLoading();
                mView.onDrawerCatListDataLoad((List<DrawerCatModel>) obj);
            }

            @Override
            public void onDrawerCatListRequestError(String errMsg) {
                mView.onDrawerCatListStopLoading();
                mView.onDrawerCatListShowMessage(errMsg);
            }
        });
    }
}
