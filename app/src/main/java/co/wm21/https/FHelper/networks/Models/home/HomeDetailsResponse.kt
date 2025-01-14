package com.example.example

import com.google.gson.annotations.SerializedName


data class HomeDetailsResponse (

  @SerializedName("error"        ) var error       : Int?    = null,
  @SerializedName("error_report" ) var errorReport : String? = null,
  @SerializedName("data"         ) var data        : Data?   = Data()

)