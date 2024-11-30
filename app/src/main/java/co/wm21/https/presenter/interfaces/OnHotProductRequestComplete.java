package co.wm21.https.presenter.interfaces;

public interface OnHotProductRequestComplete {

    void onHotProductSuccess(Object obj);

    void onHotProductError(String errMsg);
}
