package co.wm21.https.presenter.interfaces;

public interface OnBalancesRequestComplete {
    void onBalancesRequestComplete(Object obj);

    void onBalancesRequestError(String errMsg);
}
