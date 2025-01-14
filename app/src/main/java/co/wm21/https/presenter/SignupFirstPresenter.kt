package co.wm21.https.presenter

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.SignupModel
import co.wm21.https.presenter.interfaces.OnSignupFirstView
import co.wm21.https.serviceapis.InvokeSignupFirstApi

class SignupFirstPresenter(var signupFirstView: OnSignupFirstView) {
    fun signupFirstDataLoad(mobile: String?, country: Int) {
        signupFirstView.onSignupFirstStartLoading()
        InvokeSignupFirstApi(mobile, country, object : OnRequestComplete {
            override fun onRequestSuccess(obj: Any) {
                signupFirstView.onSignupFirstStopLoading()
                signupFirstView.onSignupFirstDataLoad(obj as SignupModel)
            }

            override fun onRequestError(errMsg: String) {
                signupFirstView.onSignupFirstStopLoading()
                signupFirstView.onSignupFirstShowMessage(errMsg)
            }
        })
    }
}
