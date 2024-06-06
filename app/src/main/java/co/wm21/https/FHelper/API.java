package co.wm21.https.FHelper;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import co.wm21.https.FHelper.Annotations.Field;
import co.wm21.https.FHelper.Annotations.RequestUrl;
import co.wm21.https.FHelper.Annotations.SendMethod;
import co.wm21.https.FHelper.networks.Models.AccountIncomeDataModel;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface API {
    @RequestUrl("apps/signUpFirstStep.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest signUpFirstStep(
            @Field(ConstantValues.SignUp.MOBILE) String mobile,
            @Field(ConstantValues.SignUp.COUNTRY) int country,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/login.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest login(
            @Field(ConstantValues.USERNAME) String username,
            @Field(ConstantValues.PASSWORD) String password,
            Response.Listener<JSONObject> listener);


    @RequestUrl("apps/accounts_income.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest getIncomeBalanceReportStatus(
            @Field(ConstantValues.USERNAME) String username,
            Response.Listener<JSONObject> listener);
    
    @RequestUrl("apps/accounts_expense.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest getExpenseBalanceReportStatus(
            @Field(ConstantValues.USERNAME) String username,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/accounts.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest getIncomeStatus(
            @Field(ConstantValues.USERNAME) String username,
            Response.Listener<JSONObject> listener);
    @RequestUrl("apps/accounts_income2.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest getIncomeBalanceReportStatus2(
            @Field(ConstantValues.USERNAME) String username,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/accounts_expense2.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest getExpenseBalanceReportStatus2(
            @Field(ConstantValues.USERNAME) String username,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/accounts2.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest getIncomeStatus2(
            @Field(ConstantValues.USERNAME) String username,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/tele_shop.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest teleShop(
            @Field(ConstantValues.SHOP) int shop,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/more_products.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest moreProducts(
            @Field(ConstantValues.VALUE) int value,
            @Field(ConstantValues.ID) int id,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/blog_get.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest blogs(
            @Field(ConstantValues.VALUE) int value,
            Response.Listener<JSONObject> listener);
    @RequestUrl("apps/blog_single.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest singleBlog(
            @Field(ConstantValues.ID) String id,
            Response.Listener<JSONObject> listener);



    @RequestUrl("apps/shops.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest premiumShop(
            @Field(ConstantValues.SHOP) int shop,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/category.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest category(Response.Listener<JSONObject> listener);



    @RequestUrl("apps/verification.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest verification(
            @Field(ConstantValues.Login.USERNAME) String username,
            @Field(ConstantValues.PASSWORD) String password,
            Response.Listener<JSONObject> listener);





    @RequestUrl("apps/shops_products.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest shopsProducts(@Field(ConstantValues.SHOP) int shop,
                                    @Field(ConstantValues.Product.S_ID) int s_id,
                                     Response.Listener<JSONObject> listener);



    @RequestUrl("apps/hotProducts.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest hotProduct(Response.Listener<JSONObject> listener);







    @RequestUrl("apps/balance.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest allBalances(
            @Field(ConstantValues.Login.USERNAME) String username,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/add_to_cart.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest addToCart(@Field(ConstantValues.Cart.PRODUCT_ID) String p_id,
                                @Field(ConstantValues.Cart.DEVICE_ID) String u_id,
                                @Field(ConstantValues.Cart.COLOR) String color,
                                @Field(ConstantValues.Cart.SIZE) String size,
                                @Field(ConstantValues.Cart.QUANTITY) int qty,
                                Response.Listener<JSONObject> listener);




    @RequestUrl("apps/categories.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest categories(@Field(ConstantValues.Categories.ID) int id,
                                 @Field(ConstantValues.Categories.CAT_ID) String cat_id,
                                 Response.Listener<JSONObject> listener);




    @RequestUrl("apps/customer_product_review.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest review(@Field(ConstantValues.Product.PRODUCT_ID) long serial,
                                     Response.Listener<JSONObject> listener);


    @RequestUrl("apps/user_profile.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest profile(@Field(ConstantValues.USERNAME) String username,
                              @Field(ConstantValues.PASSWORD) String password,
                              Response.Listener<JSONObject> listener);

    @RequestUrl("apps/verify_number.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest mobileVerify(@Field(ConstantValues.USERNAME) String username,
                                   @Field(ConstantValues.Verification.mobile.MOB) int mob,
                                   @Field(ConstantValues.Verification.mobile.CODE2) String code2,
                                   Response.Listener<JSONObject> listener
    );

    @RequestUrl("apps/update_password.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest updatePassword(@Field(ConstantValues.USERNAME) String username,
                                     @Field(ConstantValues.PASSWORD) String password,
                                     @Field(ConstantValues.NEW_PASSWORD) String new_password,
                                     Response.Listener<JSONObject> listener);

    @RequestUrl("apps/all_locations.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest addresses(@Field(ConstantValues.ID) String id,
                                @Field(ConstantValues.VALUE) String value,
                                Response.Listener<JSONObject> listener);

    @RequestUrl("apps/slide.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest slide(Response.Listener<JSONObject> listener);





    @RequestUrl("apps/received_eCom_sign.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest eCom_sign(@Field(ConstantValues.Login.USERNAME) String username,
                                @Field(ConstantValues.PASSWORD) String password,
                                @Field(ConstantValues.Login.PIN) String pin,
                                @Field(ConstantValues.applied.SERIAL) String serial,
                                @Field(ConstantValues.applied.POSITION) int position,
                                @Field(ConstantValues.applied.UPLINK) String uplink,
                                Response.Listener<JSONObject> listener);
    @RequestUrl("apps/receive_action.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest receive_action(@Field(ConstantValues.Login.USERNAME) String username,
                                @Field(ConstantValues.PASSWORD) String password,
                                @Field(ConstantValues.Login.PIN) String pin,
                                @Field(ConstantValues.applied.SERIAL) String serial,
                                @Field(ConstantValues.applied.ACTION) String action,
                                Response.Listener<JSONObject> listener);



    @RequestUrl("apps/update_profile.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest profileUpdateForVar(
            @Field(ConstantValues.USERNAME) String username,
            @Field(ConstantValues.PASSWORD) String password,
            @Field(ConstantValues.profile.FATHER) String father,
            @Field(ConstantValues.profile.MOTHER) String mother,
            @Field(ConstantValues.profile.BIRTH) String birth,
            @Field(ConstantValues.profile.GENDER) String gender,
            @Field(ConstantValues.profile.BLOOD) String blood,
            @Field(ConstantValues.profile.PROFESSION) String prof,
            @Field(ConstantValues.profile.EDUCATION) String education,
            @Field(ConstantValues.profile.RELIGION) String religion,
            @Field(ConstantValues.profile.NOMINEE) String nominee,
            @Field(ConstantValues.profile.NOMINEE_RELATION) String nomi_relation,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/update_contact.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest contactUpdateForVar(
            @Field(ConstantValues.USERNAME) String username,
            @Field(ConstantValues.PASSWORD) String password,
            @Field(ConstantValues.profile.COUNTRY) String country,
            @Field(ConstantValues.profile.DIVISION) String division,
            @Field(ConstantValues.profile.DISTRICT) String district,
            @Field(ConstantValues.profile.THANA) String thana,
            @Field(ConstantValues.profile.UNION) String union,
            @Field(ConstantValues.profile.WORD) String word,
            @Field(ConstantValues.profile.ADDRESS) String address,
            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/send_OTP_code.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest sendOTPForGatewayVar(@Field(ConstantValues.USERNAME) String username,
                                           @Field(ConstantValues.PASSWORD) String password,
                                           Response.Listener<JSONObject> listener);

    @RequestUrl("apps/update_gateway.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest gatewayUpdateForVar(
            @Field(ConstantValues.USERNAME) String username,
            @Field(ConstantValues.PASSWORD) String password,
            @Field(ConstantValues.profile.BKASH) String bkash,
            @Field(ConstantValues.profile.ROCKET) String rocket,
            @Field(ConstantValues.profile.NAGAD) String nagad,
            @Field(ConstantValues.profile.BANK_NAME) String bankName,
            @Field(ConstantValues.profile.ACCOUNT_NAME) String accountName,
            @Field(ConstantValues.profile.ACCOUNT_NUMBER) String accountNo,
            @Field(ConstantValues.profile.BRANCH_NAME) String branchName,
            @Field(ConstantValues.profile.ROUTING) String routing,
            @Field(ConstantValues.profile.CREDIT_CARD) String creditcard,
            @Field(ConstantValues.profile.PAYPAL) String paypal,
            @Field(ConstantValues.profile.SKRILL) String skrill,
            @Field(ConstantValues.profile.BITCOIN) String bitcoin,
            @Field(ConstantValues.profile.NETELLER) String neteller,
            @Field(ConstantValues.profile.PIN) String pin,
            Response.Listener<JSONObject> listener);

}