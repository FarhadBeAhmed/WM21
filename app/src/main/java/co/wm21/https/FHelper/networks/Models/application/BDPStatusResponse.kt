package co.wm21.https.FHelper.networks.Models.application

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BDPStatusResponse {
    @kotlin.jvm.JvmField
    @SerializedName("error")
    @Expose
    var error: Int? = null

    @SerializedName("error_report")
    @Expose
    var errorReport: String? = null

    @SerializedName("data")
    @Expose
    var data: List<BDPStatusData>? = null
}
