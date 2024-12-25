package co.wm21.https.presenter.application

import co.wm21.https.FHelper.networks.ApiUtil.OnRequestComplete
import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse
import co.wm21.https.presenter.interfaces.aplication.OnBDPStatusView
import co.wm21.https.serviceapis.InvokeBDPStatusApi

class BDPStatusPresenter(var onView: OnBDPStatusView) {
    fun getDataLoad(memberID: String?) {
        onView.onStartLoading()
        InvokeBDPStatusApi(memberID, object : OnRequestComplete {
            override fun onRequestSuccess(obj: Any) {
                onView.onStopLoading()
                onView.onBDPStatusDataLoad(obj as BDPStatusResponse)
            }

            override fun onRequestError(errMsg: String) {
                onView.onStopLoading()
                onView.onError(errMsg)
            }
        })
    }
}
