package co.wm21.https.presenter;

import com.example.example.SlideImage;

import java.util.List;

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete;
import co.wm21.https.presenter.interfaces.OnHomeTopSliderImageView;
import co.wm21.https.serviceapis.InvokeHomeTopSliderImageApi;

public class HomeTopSliderImagePresenter {
    private OnHomeTopSliderImageView onHomeTopSliderImageView;

    public HomeTopSliderImagePresenter(OnHomeTopSliderImageView onHomeTopSliderImageView) {
        this.onHomeTopSliderImageView = onHomeTopSliderImageView;
    }
    public void getSliderImageDataResponse(String limit){
        onHomeTopSliderImageView.onHomeSliderDataStartLoading();
        new InvokeHomeTopSliderImageApi(limit, new OnRequestComplete() {
            @Override
            public void onRequestSuccess(Object obj) {
                onHomeTopSliderImageView.onHomeSliderDataLoaded((List<SlideImage>)obj);
                onHomeTopSliderImageView.onHomeSliderDataStopLoading();
            }

            @Override
            public void onRequestError(String errMsg) {
                onHomeTopSliderImageView.onHomeSliderDataShowMessage(errMsg);
                onHomeTopSliderImageView.onHomeSliderDataStopLoading();
            }
        });

    }

}
