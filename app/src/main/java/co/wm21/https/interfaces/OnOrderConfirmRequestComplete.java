package co.wm21.https.interfaces;

public interface OnOrderConfirmRequestComplete {
    void onOrderConfirmRequestComplete(Object obj);

    void onOrderConfirmRequestError(String errMsg);
}
