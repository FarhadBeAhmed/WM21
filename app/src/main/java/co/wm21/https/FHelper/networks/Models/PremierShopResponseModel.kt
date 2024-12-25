package co.wm21.https.FHelper.networks.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PremierShopResponseModel {
    @SerializedName("error")
    @Expose
    var error: Int? = null

    @SerializedName("error_report")
    @Expose
    var errorReport: String? = null

    @SerializedName("data")
    @Expose
    var data: List<PremierShopData>? = null
}

class PremierShopData {
    @SerializedName("shop_id")
    @Expose
    var shopId: String? = null

    @SerializedName("shop_image")
    @Expose
    var shopImage: String? = null

    @SerializedName("shop_type_name")
    @Expose
    var shopTypeName: String? = null

    @SerializedName("shop_type_id")
    @Expose
    var shopTypeId: String? = null

    @SerializedName("shop_name")
    @Expose
    var shopName: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("small_img")
    @Expose
    var smallImg: String? = null
}