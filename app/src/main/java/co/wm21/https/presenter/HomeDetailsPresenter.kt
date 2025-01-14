package co.wm21.https.presenter

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.PremierShopData
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataListModel
import co.wm21.https.presenter.interfaces.OnHomeDetailsView
import co.wm21.https.presenter.interfaces.OnPremierShopView
import co.wm21.https.presenter.interfaces.OnRewardAchievementView
import co.wm21.https.serviceapis.InvokeHomeDetailsApi
import co.wm21.https.serviceapis.InvokePremierShopApi
import co.wm21.https.serviceapis.InvokeRewardAchievementApi
import com.example.example.HomeDetailsResponse


class HomeDetailsPresenter(var mView: OnHomeDetailsView) {
    fun onHomeDetailsResponseData() {
        mView.onStartLoading()
        InvokeHomeDetailsApi( object : OnRequestComplete {
            override fun onRequestSuccess(obj: Any) {
                mView.onStopLoading()
                mView.onHomeDetailsLoadSuccess(obj as HomeDetailsResponse)
            }

            override fun onRequestError(errMsg: String) {
                mView.onStopLoading()
                mView.onError(errMsg)
            }
        })
    }
}
