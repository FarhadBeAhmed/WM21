package co.wm21.https.serviceapis

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel
import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse
import com.example.example.HomeDetailsResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvokeHomeDetailsApi( var requestComplete: OnRequestComplete) {
    init {

        val mApiService = ApiUtils.getApiService()
        mApiService.getHomeDetailsResponse().enqueue(object : Callback<HomeDetailsResponse> {
            override fun onResponse(
                call: Call<HomeDetailsResponse>,
                response: Response<HomeDetailsResponse>
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

            override fun onFailure(call: Call<HomeDetailsResponse>, t: Throwable) {
                requestComplete.onRequestError("Something Went Wrong!")
            }
        })
    }
}
