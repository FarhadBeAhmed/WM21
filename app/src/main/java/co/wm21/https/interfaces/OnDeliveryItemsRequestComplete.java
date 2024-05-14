package co.wm21.https.interfaces;

public interface OnDeliveryItemsRequestComplete {
    void onDeliveryItemsRequestComplete(Object obj);

    void onDeliveryItemsRequestError(String errMsg);
}
