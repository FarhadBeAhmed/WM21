package co.wm21.https.serviceapis

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel
import co.wm21.https.FHelper.networks.Models.PremierShopViewResponse
import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvokePremierShopViewApi(memberId: String?,s_id: String?,shop: String?, var requestComplete: OnRequestComplete) {
    init {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", memberId)
        jsonObject.addProperty("s_id", s_id)
        jsonObject.addProperty("shop", shop)
        val mApiService = ApiUtils.getApiService()
        mApiService.getPremierShopViewResponse(jsonObject).enqueue(object : Callback<PremierShopViewResponse> {
            override fun onResponse(
                call: Call<PremierShopViewResponse>,
                response: Response<PremierShopViewResponse>
            ) {
                if (response.isSuccessful) {
                    checkNotNull(response.body())
                    if (response.body()?.error?.toInt() == 0 || response.body()?.error?.toInt() == 2) {
                        requestComplete.onRequestSuccess(response.body())
                    } else {
                        requestComplete.onRequestError(response.body()!!.errorReport)
                    }
                } else {
                    requestComplete.onRequestError("Something Went Wrong!")
                }
            }

            override fun onFailure(call: Call<PremierShopViewResponse>, t: Throwable) {
                requestComplete.onRequestError("Something Went Wrong!")
            }
        })
    }
}
