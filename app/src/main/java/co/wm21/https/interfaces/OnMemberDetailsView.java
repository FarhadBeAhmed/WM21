package com.wm21ltd.wm21.interfaces;

import java.util.List;

public interface OnMemberDetailsView {
    void onResponseData(Object object);

    void onStartLoading();

    void onStopLoading();

    void onShowMessage(String errMsg);
}
