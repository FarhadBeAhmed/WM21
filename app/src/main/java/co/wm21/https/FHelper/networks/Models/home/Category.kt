package com.example.example

import com.google.gson.annotations.SerializedName


data class Category (

  @SerializedName("categoryName"     ) var categoryName     : String? = null,
  @SerializedName("orderingID"       ) var orderingID       : String? = null,
  @SerializedName("catID"            ) var catID            : String? = null,
  @SerializedName("categoryImageUrl" ) var categoryImageUrl : String? = null

)