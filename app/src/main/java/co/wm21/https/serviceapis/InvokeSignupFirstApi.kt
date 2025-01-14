package co.wm21.https.serviceapis

import co.wm21.https.FHelper.networks.ApiUtil.ApiUtils
import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.SignupModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvokeSignupFirstApi(pId: String?, country: Int?, var requestComplete: OnRequestComplete) {
    init {
        val mApiService = ApiUtils.getApiService()

        val jsonObject = JsonObject()
        jsonObject.addProperty("country", country)
        jsonObject.addProperty("mobile", pId)
        
        mApiService.signUpFirstStep(jsonObject).enqueue(object : Callback<SignupModel> {
            override fun onResponse(call: Call<SignupModel>, response: Response<SignupModel>) {
                if (response.isSuccessful) {
                    checkNotNull(response.body())
                    if (response.body()!!.error == 0 || response.body()!!.error == 2) {
                        requestComplete.onRequestSuccess(response.body())
                    } else {
                        requestComplete.onRequestError(response.body()!!.error_report)
                    }
                } else {
                    requestComplete.onRequestError("Something Went Wrong!")
                }
            }

            override fun onFailure(call: Call<SignupModel>, t: Throwable) {
                requestComplete.onRequestError("Something Went Wrong!")
            }
        })
    }
}
