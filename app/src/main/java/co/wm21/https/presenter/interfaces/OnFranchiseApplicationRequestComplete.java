package co.wm21.https.presenter.interfaces;

public interface OnFranchiseApplicationRequestComplete {
    void onFranchiseApplicationRequestSuccess(Object obj);

    void onFranchiseApplicationRequestError(String errMsg);
}
