package com.example.example

import co.wm21.https.FHelper.networks.Models.BlogsModel
import co.wm21.https.FHelper.networks.Models.ProductModel
import co.wm21.https.FHelper.networks.Models.home.CategoryView
import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("slide_image"            ) var slideImage             : ArrayList<SlideImage>             = arrayListOf(),
  @SerializedName("category"               ) var category               : ArrayList<CategoryView>               = arrayListOf(),
  @SerializedName("hotProducts"            ) var hotProducts            : ArrayList<ProductModel>            = arrayListOf(),
  @SerializedName("popularProduct"         ) var popularProduct         : ArrayList<ProductModel>         = arrayListOf(),
  @SerializedName("popularCategoryProduct" ) var popularCategoryProduct : ArrayList<ProductModel> = arrayListOf(),
  @SerializedName("allBlogs"               ) var allBlogs               : ArrayList<BlogsModel>               = arrayListOf()

)