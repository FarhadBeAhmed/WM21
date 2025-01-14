package co.wm21.https.FHelper.networks.Models.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoryView {
    @JvmField
    @SerializedName("categoryName")
    @Expose
    var categoryName: String

    @SerializedName("orderingID")
    @Expose
    var orderingID: String? = null

    @JvmField
    @SerializedName("catID")
    @Expose
    var catID: String? = null

    @JvmField
    @SerializedName("categoryImageUrl")
    @Expose
    var categoryImageUrl: String

    constructor(categoryName: String, categoryImageUrl: String) {
        this.categoryName = categoryName
        this.categoryImageUrl = categoryImageUrl
    }

    constructor(categoryName: String, categoryImageUrl: String, catID: String?) {
        this.categoryName = categoryName
        this.categoryImageUrl = categoryImageUrl
        this.catID = catID
    }
}
