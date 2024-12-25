package co.wm21.https.presenter;


import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.presenter.interfaces.OnRatingSubmitView;
import co.wm21.https.serviceapis.InvokeRatingSubmitApi;

public class RatingSubmitPresenter {
    OnRatingSubmitView onRatingSubmitView;

    public RatingSubmitPresenter(OnRatingSubmitView onRatingSubmitView) {
        this.onRatingSubmitView = onRatingSubmitView;
    }

    public void onRatingSubmitResponse(String username, String serial, String rating, String review){
        onRatingSubmitView.onRatingSubmitStartLoading();
        new InvokeRatingSubmitApi(username, serial, rating,review, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onRatingSubmitView.onRatingSubmitStopLoading();
                onRatingSubmitView.onRatingSubmitDataLoad((String) obj);
            }

            @Override
            public void onRequestError(String errMsg) {
                onRatingSubmitView.onRatingSubmitStopLoading();
                onRatingSubmitView.onRatingSubmitShowMessage(errMsg);
            }
        });
    }
}
