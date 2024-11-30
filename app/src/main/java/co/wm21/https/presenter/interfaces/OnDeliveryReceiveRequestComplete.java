package co.wm21.https.presenter.interfaces;

public interface OnDeliveryReceiveRequestComplete {
    void onDeliveryReceiveRequestComplete(Object obj);

    void onDeliveryReceiveRequestError(String errMsg);
}
