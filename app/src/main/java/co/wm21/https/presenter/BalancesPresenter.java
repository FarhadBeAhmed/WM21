package co.wm21.https.presenter;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.FHelper.networks.Models.balanceResponse.BalanceModel;
import co.wm21.https.presenter.interfaces.OnBalancesView;
import co.wm21.https.serviceapis.InvokeBalancesApi;

public class BalancesPresenter {
    OnBalancesView balancesView;

    public BalancesPresenter(OnBalancesView balancesView) {
        this.balancesView = balancesView;
    }

    public void BalancesDataLoad(String user_id) {
        balancesView.onBalancesStartLoading();
        new InvokeBalancesApi(user_id, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                balancesView.onBalancesStopLoading();
                balancesView.onBalancesDataLoad((BalanceModel) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                balancesView.onBalancesStopLoading();
                balancesView.onBalancesShowMessage(errMsg);
            }

        });
    }
}
