package co.wm21.https.FHelper.networks.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import android.os.Parcel
import android.os.Parcelable

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

class PremierShopData() : Parcelable {

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

    constructor(parcel: Parcel) : this() {
        shopId = parcel.readString()
        shopImage = parcel.readString()
        shopTypeName = parcel.readString()
        shopTypeId = parcel.readString()
        shopName = parcel.readString()
        phone = parcel.readString()
        address = parcel.readString()
        smallImg = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(shopId)
        parcel.writeString(shopImage)
        parcel.writeString(shopTypeName)
        parcel.writeString(shopTypeId)
        parcel.writeString(shopName)
        parcel.writeString(phone)
        parcel.writeString(address)
        parcel.writeString(smallImg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PremierShopData> {
        override fun createFromParcel(parcel: Parcel): PremierShopData {
            return PremierShopData(parcel)
        }

        override fun newArray(size: Int): Array<PremierShopData?> {
            return arrayOfNulls(size)
        }
    }
}