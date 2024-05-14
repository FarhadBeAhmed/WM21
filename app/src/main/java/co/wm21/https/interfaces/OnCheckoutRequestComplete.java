package co.wm21.https.interfaces;

public interface OnCheckoutRequestComplete {
    void onCheckoutRequestComplete(Object obj);

    void onCheckoutRequestError(String errMsg);
}
