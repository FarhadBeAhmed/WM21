package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.HomeTopSliderImageModelHead;
import co.wm21.https.SliderItem;
import co.wm21.https.interfaces.OnHomeTopSliderImageRequestComplete;
import co.wm21.https.interfaces.OnHomeTopSliderImageView;
import co.wm21.https.serviceapis.InvokeHomeTopSliderImageApi;

public class HomeTopSliderImagePresenter {
    private OnHomeTopSliderImageView onHomeTopSliderImageView;

    public HomeTopSliderImagePresenter(OnHomeTopSliderImageView onHomeTopSliderImageView) {
        this.onHomeTopSliderImageView = onHomeTopSliderImageView;
    }
    public void getSliderImageDataResponse(String limit){
        onHomeTopSliderImageView.onHomeSliderDataStartLoading();
        new InvokeHomeTopSliderImageApi(limit, new OnHomeTopSliderImageRequestComplete() {
            @Override
            public void onHomeTopSliderImageRequestSuccess(Object obj) {
                onHomeTopSliderImageView.onHomeSliderDataStopLoading();
                onHomeTopSliderImageView.onHomeSliderDataLoaded((List<SliderItem>)obj);
            }

            @Override
            public void onHomeTopSliderImageRequestError(String errMsg) {
                onHomeTopSliderImageView.onHomeSliderDataStopLoading();
                onHomeTopSliderImageView.onHomeSliderDataShowMessage(errMsg);
            }
        });

    }

}
