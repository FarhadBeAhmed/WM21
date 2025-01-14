package co.wm21.https.presenter.interfaces

import co.wm21.https.FHelper.networks.Models.AddToCartModel
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel
import co.wm21.https.FHelper.networks.Models.PremierShopViewResponse
import com.example.example.HomeDetailsResponse

interface OnHomeDetailsView {
    fun onHomeDetailsLoadSuccess(response: HomeDetailsResponse?)

    fun onStartLoading()

    fun onStopLoading()

    fun onError(errmsg: String?)
}
