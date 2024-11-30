package co.wm21.https.presenter.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead;

public interface OnProfileDetailsView {
    void onProfileDetailsDataLoad(ProfileDetailsHead profileDetailsHead);

    void onProfileDetailsStartLoading();

    void onProfileDetailsStopLoading();

    void onProfileDetailsShowMessage(String errmsg);
}
