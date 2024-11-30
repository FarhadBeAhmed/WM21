package co.wm21.https.presenter.interfaces;

public interface OnMemberDetailsView {
    void onResponseData(Object object);

    void onStartLoading();

    void onStopLoading();

    void onShowMessage(String errMsg);
}
