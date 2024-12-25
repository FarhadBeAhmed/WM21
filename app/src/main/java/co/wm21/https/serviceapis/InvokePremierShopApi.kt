package co.wm21.https.serviceapis

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel
import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvokePremierShopApi(memberId: String?, var requestComplete: OnRequestComplete) {
    init {
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", memberId)
        val mApiService = ApiUtils.getApiService()
        mApiService.getPremierShopResponse(jsonObject).enqueue(object : Callback<PremierShopResponseModel> {
            override fun onResponse(
                call: Call<PremierShopResponseModel>,
                response: Response<PremierShopResponseModel>
            ) {
                if (response.isSuccessful) {
                    checkNotNull(response.body())
                    if (response.body()?.error == 0 || response.body()?.error == 2) {
                        requestComplete.onRequestSuccess(response.body())
                    } else {
                        requestComplete.onRequestError(response.body()!!.errorReport)
                    }
                } else {
                    requestComplete.onRequestError("Something Went Wrong!")
                }
            }

            override fun onFailure(call: Call<PremierShopResponseModel>, t: Throwable) {
                requestComplete.onRequestError("Something Went Wrong!")
            }
        })
    }
}
