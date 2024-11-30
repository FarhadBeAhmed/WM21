package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.EshopListModel;

public interface OnEshopListView {
    void onEshopListDataLoad(List<EshopListModel> eshopListModels);

    void onEshopListStartLoading();

    void onEshopListStopLoading();

    void onEshopListShowMessage(String errmsg);
}
