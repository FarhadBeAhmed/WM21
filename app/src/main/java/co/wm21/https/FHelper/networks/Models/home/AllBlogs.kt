package com.example.example

import com.google.gson.annotations.SerializedName


data class AllBlogs (

  @SerializedName("serial"  ) var serial  : String? = null,
  @SerializedName("name"    ) var name    : String? = null,
  @SerializedName("img"     ) var img     : String? = null,
  @SerializedName("image"   ) var image   : String? = null,
  @SerializedName("subject" ) var subject : String? = null,
  @SerializedName("text"    ) var text    : String? = null,
  @SerializedName("date"    ) var date    : String? = null

)