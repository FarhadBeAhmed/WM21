package co.wm21.https.interfaces;

public interface OnBalancesRequestComplete {
    void onBalancesRequestComplete(Object obj);

    void onBalancesRequestError(String errMsg);
}
