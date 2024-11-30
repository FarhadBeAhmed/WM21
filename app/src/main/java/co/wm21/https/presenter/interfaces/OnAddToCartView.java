package co.wm21.https.presenter.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.BlogsModel;

public interface OnAddToCartView {
    void onAddToCartDataLoad(AddToCartModel addToCartModel);

    void onAddToCartStartLoading();

    void onAddToCartStopLoading();

    void onAddToCartShowMessage(String errmsg);
}
