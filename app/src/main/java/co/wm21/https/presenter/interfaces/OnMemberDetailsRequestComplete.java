package co.wm21.https.presenter.interfaces;

public interface OnMemberDetailsRequestComplete {

    void onMemberDetailsRequestSuccess(Object obj);

    void onMemberDetailsRequestError(String errMsg);
}
