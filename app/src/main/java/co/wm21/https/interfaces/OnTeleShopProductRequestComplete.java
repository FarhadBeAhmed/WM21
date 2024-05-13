package co.wm21.https.interfaces;

public interface OnHotProductRequestComplete {

    void onHotProductSuccess(Object obj);

    void onHotProductError(String errMsg);
}
