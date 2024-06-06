package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.balanceResponse.BalanceModel;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnAddToCartView;
import co.wm21.https.interfaces.OnBalancesRequestComplete;
import co.wm21.https.interfaces.OnBalancesView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;
import co.wm21.https.serviceapis.InvokeBalancesApi;

public class BalancesPresenter {
    OnBalancesView balancesView;

    public BalancesPresenter(OnBalancesView balancesView) {
        this.balancesView = balancesView;
    }

    public void BalancesDataLoad(String user_id) {
        balancesView.onBalancesStartLoading();
        new InvokeBalancesApi(user_id, new OnBalancesRequestComplete() {
            @Override
            public void onBalancesRequestComplete(Object obj) {
                balancesView.onBalancesStopLoading();
                balancesView.onBalancesDataLoad((BalanceModel) obj);
            }

            @Override
            public void onBalancesRequestError(String errMsg) {
                balancesView.onBalancesStopLoading();
                balancesView.onBalancesShowMessage(errMsg);
            }

        });
    }
}
