package co.wm21.https.presenter.interfaces.gelealogy;

public interface OnGenealogyListRequestComplete {
    void onApiRequestComplete(Object obj);

    void onApiRequestError(String errMsg);
}
