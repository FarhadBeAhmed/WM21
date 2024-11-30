package co.wm21.https.presenter.interfaces;

public interface OnOrderConfirmRequestComplete {
    void onOrderConfirmRequestComplete(Object obj);

    void onOrderConfirmRequestError(String errMsg);
}
