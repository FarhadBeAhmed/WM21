package co.wm21.https.presenter.interfaces.gelealogy;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.genealogy.GenealogyListResponse;

public interface OnGenealogyListView {
    void onGenealogyListDataLoad(GenealogyListResponse response);

    void onStartLoading();

    void onStopLoading();

    void onError(String errmsg);
}
