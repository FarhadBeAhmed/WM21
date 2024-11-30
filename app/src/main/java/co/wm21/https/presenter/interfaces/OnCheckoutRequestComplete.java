package co.wm21.https.presenter.interfaces;

public interface OnCheckoutRequestComplete {
    void onCheckoutRequestComplete(Object obj);

    void onCheckoutRequestError(String errMsg);
}
