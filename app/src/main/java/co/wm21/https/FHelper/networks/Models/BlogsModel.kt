package co.wm21.https.FHelper.networks.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BlogsModel {
    @JvmField
    @SerializedName("serial")
    @Expose
    var serial: String? = null

    @JvmField
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("img")
    @Expose
    var img: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @JvmField
    @SerializedName("subject")
    @Expose
    var subject: String? = null

    @SerializedName("text")
    @Expose
    var text: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null
}
