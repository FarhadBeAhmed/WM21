package com.wm21ltd.wm21.interfaces;

import java.util.HashMap;

public interface OnTrainingRequestFormView {
    void onTrainingRequestFormData(HashMap hashMap);

    void onTrainingRequestFormStartLoading();

    void onTrainingRequestFormStopLoading();

    void onTrainingRequestFormShowMessage(String errMsg);
}
