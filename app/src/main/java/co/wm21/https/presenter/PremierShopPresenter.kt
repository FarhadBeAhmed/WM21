package co.wm21.https.presenter

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.PremierShopData
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataListModel
import co.wm21.https.presenter.interfaces.OnPremierShopView
import co.wm21.https.presenter.interfaces.OnRewardAchievementView
import co.wm21.https.serviceapis.InvokePremierShopApi
import co.wm21.https.serviceapis.InvokeRewardAchievementApi


class PremierShopPresenter(var mView: OnPremierShopView) {
    fun onPremierShopResponseData(userID: String?,shopId: String?) {
        mView.onStartLoading()
        InvokePremierShopApi(userID,shopId, object : OnRequestComplete {
            override fun onRequestSuccess(obj: Any) {
                mView.onStopLoading()
                mView.onPremierShopDataLoadSuccess(obj as PremierShopResponseModel)
            }

            override fun onRequestError(errMsg: String) {
                mView.onStopLoading()
                mView.onError(errMsg)
            }
        })
    }
}
