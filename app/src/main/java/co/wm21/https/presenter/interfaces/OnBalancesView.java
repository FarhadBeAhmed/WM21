package co.wm21.https.presenter.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.balanceResponse.BalanceModel;

public interface OnBalancesView {
    void onBalancesDataLoad(BalanceModel balanceModel);

    void onBalancesStartLoading();

    void onBalancesStopLoading();

    void onBalancesShowMessage(String errMsg);
}
