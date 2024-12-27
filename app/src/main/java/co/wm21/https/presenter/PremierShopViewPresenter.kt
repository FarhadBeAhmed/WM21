package co.wm21.https.presenter

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.PremierShopData
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel
import co.wm21.https.FHelper.networks.Models.PremierShopViewResponse
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataListModel
import co.wm21.https.presenter.interfaces.OnPremierShopDetailView
import co.wm21.https.presenter.interfaces.OnPremierShopView
import co.wm21.https.presenter.interfaces.OnRewardAchievementView
import co.wm21.https.serviceapis.InvokePremierShopApi
import co.wm21.https.serviceapis.InvokePremierShopViewApi
import co.wm21.https.serviceapis.InvokeRewardAchievementApi


class PremierShopViewPresenter(var mView: OnPremierShopDetailView) {
    fun onPremierShopViewResponseData(userID: String?, s_id: String?, shop: String?, ) {
        mView.onStartLoading()
        InvokePremierShopViewApi(userID,s_id,shop, object : OnRequestComplete {
            override fun onRequestSuccess(obj: Any) {
                mView.onStopLoading()
                mView.onPremierShopDetailLoadSuccess(obj as PremierShopViewResponse)
            }

            override fun onRequestError(errMsg: String) {
                mView.onStopLoading()
                mView.onError(errMsg)
            }
        })
    }
}
