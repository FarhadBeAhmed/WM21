package com.example.example

import com.google.gson.annotations.SerializedName


data class SlideImage (

  @SerializedName("img1" ) var img1 : String? = null,
  @SerializedName("info" ) var info : String? = null

)