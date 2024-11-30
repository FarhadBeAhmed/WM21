package co.wm21.https.presenter.genealogy;

import co.wm21.https.FHelper.networks.Models.genealogy.GenealogyListResponse;
import co.wm21.https.presenter.interfaces.gelealogy.OnGenealogyListRequestComplete;
import co.wm21.https.presenter.interfaces.gelealogy.OnGenealogyListView;
import co.wm21.https.serviceapis.InvokeGenealogyListApi;

public class GenealogyListPresenter {
    OnGenealogyListView onView;

    public GenealogyListPresenter(OnGenealogyListView onView) {
        this.onView = onView;
    }

    public void getDataLoad(String memberID) {
        onView.onStartLoading();
        new InvokeGenealogyListApi(memberID, new OnGenealogyListRequestComplete() {
            @Override
            public void onApiRequestComplete(Object obj) {
                onView.onStopLoading();
                onView.onGenealogyListDataLoad((GenealogyListResponse) obj);
            }

            @Override
            public void onApiRequestError(String errMsg) {
                onView.onStopLoading();
                onView.onError(errMsg);
            }
        });
    }
}
