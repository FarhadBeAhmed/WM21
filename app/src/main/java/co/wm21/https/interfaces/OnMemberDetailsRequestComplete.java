package com.wm21ltd.wm21.interfaces;

public interface OnMemberDetailsRequestComplete {

    void onMemberDetailsRequestSuccess(Object obj);

    void onMemberDetailsRequestError(String errMsg);
}
