package co.wm21.https.FHelper.networks.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckoutModel {
    @JvmField
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @JvmField
    @SerializedName("message")
    @Expose
    var errorReport: String? = null

    @JvmField
    @SerializedName("data")
    @Expose
    var data: String? = null
}
