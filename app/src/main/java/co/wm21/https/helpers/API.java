package co.wm21.https.helpers;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONObject;

import co.wm21.https.annotations.*;


public interface API {

  /*  @RequestUrl({"apps", "login"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectResponse login(@Field(Constant.USERNAME) String username,
                             @Field(Constant.PASSWORD) String password);

    @RequestUrl({"apps", "slide"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse slide();

    @RequestUrl({"apps", "category"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse category();

    @RequestUrl({"apps", "sub_category"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse sub_category();

    @RequestUrl({"apps", "sub_category"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse sub_category(@Field(Constant.Category.CATEGORY_ID) long cat_id);

    @RequestUrl({"apps", "sub_sub_category"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse sub_sub_category();

    @RequestUrl({"apps", "sub_sub_category"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse sub_sub_category(@Field(Constant.Category.SUB_CATEGORY_ID) long scat_id);

    @RequestUrl({"apps", "sub_sub_category"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse sub_sub_category(@Field(Constant.Category.CATEGORY_ID) long cat_id,
                                        @Field(Constant.Category.SUB_CATEGORY_ID) long scat_id);

    @RequestUrl({"apps", "product"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse product(@Field(ConstantValues.Product.LIMIT) int limit);


    @RequestUrl({"apps", "popularProduct"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse popularProduct(@Field(ConstantValues.Product.VALUE) int value);

    @RequestUrl({"apps", "product"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse product(@Field(Constant.Category.CATEGORY_ID) long cat_id);

    @RequestUrl({"apps", "product"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse product(@Field(Constant.Category.SUB_CATEGORY_ID) long scat_id,
                              @Field(Constant.Category.BRAND_ID) long brand_id);

    @RequestUrl({"apps", "product"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse product(@Field(Constant.Category.CATEGORY_ID) long cat_id,
                              @Field(Constant.Category.SUB_CATEGORY_ID) long scat_id,
                              @Field(Constant.Category.BRAND_ID) long brand_id);

    @RequestUrl({"apps", "affiliate", "balance"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectResponse affiliate_balance(@Field(Constant.USERNAME) String username,
                                         @Field(Constant.PASSWORD) String password);

    @RequestUrl({"apps", "affiliate", "profile"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectResponse affiliate_profile(@Field(Constant.USERNAME) String username,
                                         @Field(Constant.PASSWORD) String password);*/


}