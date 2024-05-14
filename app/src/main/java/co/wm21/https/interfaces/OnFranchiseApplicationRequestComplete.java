package co.wm21.https.interfaces;

public interface OnFranchiseApplicationRequestComplete {
    void onFranchiseApplicationRequestSuccess(Object obj);

    void onFranchiseApplicationRequestError(String errMsg);
}
