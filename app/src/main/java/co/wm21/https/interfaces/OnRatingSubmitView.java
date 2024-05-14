package co.wm21.https.interfaces;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.BlogsModel;

public interface OnRatingSubmitView {
    void onRatingSubmitDataLoad(String responseMsg);

    void onRatingSubmitStartLoading();

    void onRatingSubmitStopLoading();

    void onRatingSubmitShowMessage(String errmsg);
}
