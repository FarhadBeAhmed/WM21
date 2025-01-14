package co.wm21.https.FHelper.networks.Models
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName


data class PremierShopViewResponse(
    @SerializedName("error")
    val error: Long,
    @SerializedName("error_report")
    val errorReport: String,
    @SerializedName("data")
    val data: PremierShopViewData
)

data class PremierShopViewData(
    @SerializedName("shop_info")
    val shopInfo: ShopInfo,
    @SerializedName("products")
    val products: List<ProductModel>?
)
data class ShopInfo(
    @SerializedName("shop_id")
    val shopId: Long,
    @SerializedName("shop_image")
    val shopImage: String,
    @SerializedName("shop_type_name")
    val shopTypeName: String,
    @SerializedName("shop_type_id")
    val shopTypeId: String,
    @SerializedName("shop_name")
    val shopName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("small_img")
    val smallImg: Any?,
    @SerializedName("map")
    val map: String,
    @SerializedName("percent")
    val percent: String,
    @SerializedName("rp")
    val rp: Long
)


