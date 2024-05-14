package co.wm21.https.interfaces;

public interface OnDeliveryReceiveRequestComplete {
    void onDeliveryReceiveRequestComplete(Object obj);

    void onDeliveryReceiveRequestError(String errMsg);
}
