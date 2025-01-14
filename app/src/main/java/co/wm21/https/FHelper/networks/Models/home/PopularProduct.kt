package com.example.example

import com.google.gson.annotations.SerializedName


data class PopularProduct (

  @SerializedName("serial"             ) var serial           : String? = null,
  @SerializedName("name"               ) var name             : String? = null,
  @SerializedName("upload_by"          ) var uploadBy         : String? = null,
  @SerializedName("offer_date"         ) var offerDate        : String? = null,
  @SerializedName("img"                ) var img              : String? = null,
  @SerializedName("discount"           ) var discount         : String? = null,
  @SerializedName("sprice"             ) var sprice           : String? = null,
  @SerializedName("point"              ) var point            : String? = null,
  @SerializedName("price"              ) var price            : String? = null,
  @SerializedName("cat_id"             ) var catId            : String? = null,
  @SerializedName("scat_id"            ) var scatId           : String? = null,
  @SerializedName("brand_id"           ) var brandId          : String? = null,
  @SerializedName("discount_in_percet" ) var discountInPercet : String? = null

)