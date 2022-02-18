package co.wm21.https.helpers;

import androidx.annotation.NonNull;

import com.android.volley.Request;

import org.json.JSONObject;

import co.wm21.https.annotations.*;
import co.wm21.https.api_request.JsonArrayResponse;
import co.wm21.https.api_request.JsonObjectResponse;

/**
 * <p>To use API interface add a return method and type to JsonResponse and add
 * {@link RequestUrl}, {@link SendMethod} and {@link NonNull} to method and add parameters and add
 * {@link Field} to parameters and make the method as non-static.<br/>
 * For Example</p><br/>
 *
 * <pre>
 * &#64;{@link RequestUrl}(&#123;"apps", "link"&#125;)
 * &#64;{@link SendMethod}({@link Request.Method}.POST)
 * &#64;{@link NonNull}
 * {@link JsonObjectResponse} methodName(&#64;{@link Field}("Field") {@link Object} field,
 *                         &#64;{@link Field}("Field2") {@link Object} field2);
 * </pre>
 *
 * @see Constant#getAPI()
 * @see JsonObjectResponse
 * @see com.android.volley.toolbox.Volley
 * @see JSONObject
 */
public interface API {

    @RequestUrl({"apps", "login"})
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
    JsonArrayResponse product();

    @RequestUrl({"apps", "product"})
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonArrayResponse product(@Field(Constant.Category.BRAND_ID) long brand_id);

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
                                         @Field(Constant.PASSWORD) String password);
}