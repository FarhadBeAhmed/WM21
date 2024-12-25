package co.wm21.https.presenter.interfaces

import co.wm21.https.FHelper.networks.Models.AddToCartModel
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel

interface OnPremierShopView {
    fun onPremierShopDataLoadSuccess(response: PremierShopResponseModel?)

    fun onStartLoading()

    fun onStopLoading()

    fun onError(errmsg: String?)
}
