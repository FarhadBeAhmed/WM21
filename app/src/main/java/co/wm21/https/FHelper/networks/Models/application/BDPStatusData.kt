package co.wm21.https.FHelper.networks.Models.application

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BDPStatusData {
    @JvmField
    @SerializedName("id")
    @Expose
    var id: String? = null

    @JvmField
    @SerializedName("user")
    @Expose
    var user: String? = null

    @JvmField
    @SerializedName("amount")
    @Expose
    var amount: String? = null

    @JvmField
    @SerializedName("unite")
    @Expose
    var unite: String? = null

    @JvmField
    @SerializedName("date")
    @Expose
    var date: String? = null

    @JvmField
    @SerializedName("approve")
    @Expose
    var approve: String? = null

    @JvmField
    @SerializedName("paid")
    @Expose
    var paid: String? = null
}
