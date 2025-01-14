package co.wm21.https.serviceapis

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.CheckoutModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvokeCheckoutApi(
    deviceID: String?,
    userId: String?,
    var requestComplete: OnRequestComplete
) {
    init {
        val mApiService = ApiUtils.getApiService()

        val jsonObject = JsonObject()
        jsonObject.addProperty("u_id", deviceID)
        jsonObject.addProperty("username", userId)
        mApiService.checkout(jsonObject).enqueue(object : Callback<CheckoutModel> {
            override fun onResponse(call: Call<CheckoutModel>, response: Response<CheckoutModel>) {
                if (response.isSuccessful) {
                    checkNotNull(response.body())
                    if (response.body()!!.status == 200) {
                        requestComplete.onRequestSuccess(response.body())
                    } else {
                        requestComplete.onRequestError(response.body()!!.errorReport)
                    }
                } else {
                    requestComplete.onRequestError("Something Went Wrong!")
                }
            }

            override fun onFailure(call: Call<CheckoutModel>, t: Throwable) {
                requestComplete.onRequestError("Something Went Wrong!")
            }
        })
    }
}
