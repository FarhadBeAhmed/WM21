package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.EshopListModel;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.interfaces.OnEshopListRequestComplete;
import co.wm21.https.interfaces.OnEshopListView;
import co.wm21.https.serviceapis.InvokeBlogListApi;
import co.wm21.https.serviceapis.InvokeEshopListApi;

public class EshopListPresenter {
    OnEshopListView eshopListView;

    public EshopListPresenter(OnEshopListView eshopListView) {
        this.eshopListView = eshopListView;
    }

    public void eshopDataLoad(String user_id,String location,String locationType,String searchTxt) {
        eshopListView.onEshopListStartLoading();
        new InvokeEshopListApi(user_id,location,locationType,searchTxt, new OnEshopListRequestComplete() {
            @Override
            public void onEshopListRequestComplete(Object obj) {
                eshopListView.onEshopListStopLoading();
                eshopListView.onEshopListDataLoad((List<EshopListModel>) obj);
            }

            @Override
            public void onEshopListRequestError(String errMsg) {
                eshopListView.onEshopListStopLoading();
                eshopListView.onEshopListShowMessage(errMsg);
            }
        });
    }
}
