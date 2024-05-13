package co.wm21.https.interfaces;

public interface OnMemberDetailsRequestComplete {

    void onMemberDetailsRequestSuccess(Object obj);

    void onMemberDetailsRequestError(String errMsg);
}
