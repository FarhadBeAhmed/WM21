package co.wm21.https.FHelper.networks.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class ProductModel(
    @SerializedName("serial")
    val serial: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("upload_by")
    val uploadBy: String? = null,
    @SerializedName("offer_date")
    val offerDate: String? = null,
    @SerializedName("img")
    val img: String? = null,
    @SerializedName("discount")
    val discount: String? = null,
    @SerializedName("sprice")
    val sprice: String? = null,
    @SerializedName("point")
    val point: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("cat_id")
    val catId: String? = null,
    @SerializedName("scat_id")
    val scatId: String? = null,
    @SerializedName("brand_id")
    val brandId: String? = null,
    @SerializedName("discount_in_percet")
    val discountInPercet: String? = null
) : Parcelable
