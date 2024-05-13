package https.outsourcingvilla.com;

import androidx.annotation.NonNull;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import https.outsourcingvilla.com.Annotations.*;

public interface API {

    @RequestUrl("apps/login.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest login(@Field(ConstantValues.Login.USERNAME) String username,
                            @Field(ConstantValues.Login.PASSWORD) String password,
                            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/signup.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest signup(@Field(ConstantValues.ID) int id,
                             @Field(ConstantValues.PASSWORD) String pass,
                             @Field(ConstantValues.SignUp.COUNTRY) String country,
                             @Field(ConstantValues.SignUp.PIN_CODE) String pinCode,
                             @Field(ConstantValues.SignUp.REFERRAL) String referral,
                             @Field(ConstantValues.SignUp.PLACEMENT) String placement,
                             @Field(ConstantValues.SignUp.POSITION) String position,
                             @Field(ConstantValues.SignUp.USER_ID) String userID,
                             @Field(ConstantValues.SignUp.NAME) String name,
                             @Field(ConstantValues.SignUp.PASSWORD) String password,
                             @Field(ConstantValues.SignUp.PIN) String pin,
                             @Field(ConstantValues.SignUp.MOBILE) String mobile,
                             @Field(ConstantValues.SignUp.EMAIL) String email,
                             Response.Listener<JSONObject> listener);

    @RequestUrl("apps/product.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest product(Response.Listener<JSONObject> listener);

    @RequestUrl("apps/buy_product.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest buyProduct(@Field(ConstantValues.ID) int id,
                                 @Field(ConstantValues.PASSWORD) String pass,
                                 @Field(ConstantValues.Product.QUANTITY) String quantity,
                                 @Field(ConstantValues.Product.PIN) String pin,
                                 @Field(ConstantValues.Product.SERIAL) String serial,
                                 Response.Listener<JSONObject> listener);

    @RequestUrl("apps/notice.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest notice(@Field(ConstantValues.ID) int id,
                             @Field(ConstantValues.PASSWORD) String pass,
                             Response.Listener<JSONObject> listener);

    @RequestUrl("apps/terms_and_condition.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest termsAndCondition(@Field(ConstantValues.ID) int id,
                                        @Field(ConstantValues.PASSWORD) String pass,
                                        Response.Listener<JSONObject> listener);

    @RequestUrl("apps/bal_info.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest balance(@Field(ConstantValues.ID) int id,
                              @Field(ConstantValues.PASSWORD) String pass,
                              Response.Listener<JSONObject> listener);

    @RequestUrl("apps/profile.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest profile(@Field(ConstantValues.ID) int id,
                              @Field(ConstantValues.PASSWORD) String pass,
                              Response.Listener<JSONObject> listener);

    @RequestUrl("apps/upload_photo.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest uploadPhoto(@Field(ConstantValues.ID) int id,
                                  @Field(ConstantValues.PASSWORD) String pass,
                                  @Field(ConstantValues.Profile.IMAGE) String image,
                                  Response.Listener<JSONObject> listener);

    @RequestUrl("apps/profile_update.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest updateProfile(@Field(ConstantValues.ID) int id,
                                    @Field(ConstantValues.PASSWORD) String pass,
                                    @Field(ConstantValues.Profile.FULL_NAME) String fullName,
                                    @Field(ConstantValues.Profile.FATHER_NAME) String fatherName,
                                    @Field(ConstantValues.Profile.MOTHER_NAME) String motherName,
                                    @Field(ConstantValues.Profile.MOBILE_NUMBER) String mobile,
                                    @Field(ConstantValues.Profile.EMAIL_ADDRESS) String email,
                                    @Field(ConstantValues.Profile.UNION) String union,
                                    @Field(ConstantValues.Profile.THANA) String thana,
                                    @Field(ConstantValues.Profile.DISTRICT) String district,
                                    @Field(ConstantValues.Profile.DIVISION) String division,
                                    @Field(ConstantValues.Profile.POST_CODE) String postCode,
                                    @Field(ConstantValues.Profile.BIRTH) String birthDate,
                                    @Field(ConstantValues.Profile.TYPE) String type,
                                    @Field(ConstantValues.Profile.ADDRESS) String address,
                                    @Field(ConstantValues.Profile.COUNTRY) String country,
                                    @Field(ConstantValues.Profile.BANK_NAME) String bankName,
                                    @Field(ConstantValues.Profile.BRANCH_NAME) String bankBranch,
                                    @Field(ConstantValues.Profile.BANK_ACCOUNT_NO) String bankAccountNo,
                                    @Field(ConstantValues.Profile.BANK_ACCOUNT_NAME) String bankaccountName,
                                    @Field(ConstantValues.Profile.BKASH) String bKash,
                                    @Field(ConstantValues.Profile.ROCKET) String rocket,
                                    @Field(ConstantValues.Profile.NAGAD) String nagad,
                                    Response.Listener<JSONObject> listener);

    @RequestUrl("apps/mobile_verification.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest mobileVerify(@Field(ConstantValues.ID) int id,
                                   @Field(ConstantValues.PASSWORD) String pass,
                                   @Field(ConstantValues.Profile.VEF) int vef,
                                   @Field(ConstantValues.Profile.MOB) int mob,
                                   @Field(ConstantValues.Profile.CODE2) String code2,
                                   Response.Listener<JSONObject> listener);

    @RequestUrl("apps/package_details.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest packageDetails(@Field(ConstantValues.ID) int id,
                                     @Field(ConstantValues.PASSWORD) String pass,
                                     Response.Listener<JSONObject> listener);


    @RequestUrl("apps/package_details.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest packageSubmit(@Field(ConstantValues.ID) int id,
                                    @Field(ConstantValues.PASSWORD) String pass,
                                    @Field(ConstantValues.SignUp.PIN) String pin,
                                    @Field(ConstantValues.PACK) String pack,
                                    Response.Listener<JSONObject> listener);

    @RequestUrl("apps/update_password.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest updateSecurity(@Field(ConstantValues.ID) int id,
                                     @Field(ConstantValues.PASSWORD) String pass,
                                     @Field(ConstantValues.Profile.PASSWORD_0) String currentPassword,
                                     @Field(ConstantValues.Profile.PASSWORD_1) String newPassword,
                                     @Field(ConstantValues.Profile.PASSWORD_2) String confirmPassword,
                                     Response.Listener<JSONObject> listener);

    @RequestUrl("apps/update_pin.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest updatePin(@Field(ConstantValues.ID) int id,
                                @Field(ConstantValues.PASSWORD) String pass,
                                @Field(ConstantValues.Profile.PASSWORD_0) String currentPassword,
                                @Field(ConstantValues.Profile.PASSWORD_1) String newPassword,
                                @Field(ConstantValues.Profile.PASSWORD_2) String confirmPassword,
                                Response.Listener<JSONObject> listener);

   /* @NonNull
    default JsonObjectRequest updateSecurity(int id,
                                             String pass,
                                             String currentPassword,
                                             String newPassword,
                                             String confirmPassword,
                                             String updateType,
                                             Response.Listener<JSONObject> listener) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantValues.ID, id)
                      .put(ConstantValues.PASSWORD, pass)
                      .put(ConstantValues.Profile.PASSWORD_0, currentPassword)
                      .put(ConstantValues.Profile.PASSWORD_1, newPassword)
                      .put(ConstantValues.Profile.PASSWORD_2, confirmPassword);
        } catch (Exception e) { e.printStackTrace(); }
        return new JsonObjectRequest(Request.Method.POST, ConstantValues.getFileNameAsHost("apps", "update_"+updateType + ".php"),
                jsonObject, listener, Throwable::printStackTrace);
    }*/

    @RequestUrl("apps/verification.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest verification(@Field(ConstantValues.ID) int id,
                                   @Field(ConstantValues.PASSWORD) String pass,
                                   Response.Listener<JSONObject> listener);

    @RequestUrl("apps/tree.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest teamTree(@Field(ConstantValues.ID) int id,
                               @Field(ConstantValues.PASSWORD) String pass,
                               @Field(ConstantValues.USER_ID) String userid,
                               Response.Listener<JSONObject> listener);

    @RequestUrl("apps/agreement.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest agreement(@Field(ConstantValues.ID) int id,
                                @Field(ConstantValues.PASSWORD) String pass,
                                Response.Listener<JSONObject> listener);

    @RequestUrl("apps/signUp_reports_apps.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest signUp_reports(@Field(ConstantValues.ID) int id,
                                     @Field(ConstantValues.PASSWORD) String pass,
                                     Response.Listener<JSONObject> listener);

    @RequestUrl("apps/agent_sign_report_apps.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest agent_sign_report(@Field(ConstantValues.ID) int id,
                                        @Field(ConstantValues.PASSWORD) String pass,
                                        Response.Listener<JSONObject> listener);


    @RequestUrl("apps/withdrawDetails.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest withdrawDetails(@Field(ConstantValues.ID) int id,
                                      @Field(ConstantValues.PASSWORD) String pass,
                                      Response.Listener<JSONObject> listener);

    @RequestUrl("apps/fund.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest addFund(@Field(ConstantValues.ID) int id,
                              @Field(ConstantValues.PASSWORD) String pass,
                              @Field(ConstantValues.AddFund.PIN) String pin,
                              @Field(ConstantValues.AddFund.METHOD) String method,
                              @Field(ConstantValues.AddFund.AMOUNT) String amount,
                              @Field(ConstantValues.AddFund.PDATE) String pdate,
                              @Field(ConstantValues.AddFund.BANK_NAME) String bankname,
                              @Field(ConstantValues.AddFund.BRANCH_NAME) String branchName,
                              @Field(ConstantValues.AddFund.ACCOUNT_NO) String accountNum,
                              @Field(ConstantValues.AddFund.ACCOUNT_NAME) String accountName,
                              @Field(ConstantValues.AddFund.BKASH) String bkash,
                              @Field(ConstantValues.AddFund.ROCKET) String rocket,
                              @Field(ConstantValues.AddFund.NAGAD) String nagad,
                              @Field(ConstantValues.AddFund.NETTLER) String nettler,
                              @Field(ConstantValues.AddFund.PAYZA) String payza,
                              @Field(ConstantValues.AddFund.SKRILL) String skrill,
                              @Field(ConstantValues.AddFund.PAYPAL) String paypal,
                              @Field(ConstantValues.AddFund.TRX) String trx,
                              @Field(ConstantValues.AddFund.RECEIPT) String receipt,
                              @Field(ConstantValues.AddFund.GATEWAY) String gateway,
                              Response.Listener<JSONObject> listener);

    @RequestUrl("apps/transfer_bal.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest transfer(@Field(ConstantValues.ID) int id,
                               @Field(ConstantValues.PASSWORD) String pass,
                               @Field(ConstantValues.Transfer.TYPE) int type,
                               @Field(ConstantValues.Transfer.AMOUNT) String amount,
                               @Field(ConstantValues.Transfer.PIN) String pin,
                               @Field(ConstantValues.Transfer.USER_ID) String userId,
                               Response.Listener<JSONObject> listener);

    @RequestUrl("apps/withdraw.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest withdraw(@Field(ConstantValues.ID) int id,
                               @Field(ConstantValues.PASSWORD) String pass,
                               @Field(ConstantValues.Withdraw.METHOD) String method,
                               @Field(ConstantValues.Withdraw.AGENT) String agent,
                               @Field(ConstantValues.Withdraw.BANK_NAME) String bankname,
                               @Field(ConstantValues.Withdraw.BRANCH_NAME) String branchName,
                               @Field(ConstantValues.Withdraw.ACCOUNT_NO) String accountNum,
                               @Field(ConstantValues.Withdraw.ACCOUNT_NAME) String accountName,
                               @Field(ConstantValues.Withdraw.BKASH) String bKash,
                               @Field(ConstantValues.Withdraw.ROCKET) String rocket,
                               @Field(ConstantValues.Withdraw.NAGAD) String nagad,
                               @Field(ConstantValues.Withdraw.AMOUNT) String amount,
                               @Field(ConstantValues.Withdraw.PIN) String pin,
                               Response.Listener<JSONObject> listener);

    @RequestUrl("apps/flexiload.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest flexiload(@Field(ConstantValues.ID) int id,
                                @Field(ConstantValues.PASSWORD) String pass,
                                @Field(ConstantValues.Flexiload.TYPE) String type,
                                @Field(ConstantValues.Flexiload.MOBILE) String mobile,
                                @Field(ConstantValues.Flexiload.AMOUNT) String amount,
                                @Field(ConstantValues.Flexiload.PIN) String pin,
                                Response.Listener<JSONObject> listener);

    @RequestUrl("apps/withdraw_agent.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest withdrawAgent(@Field(ConstantValues.ID) int id,
                                    @Field(ConstantValues.PASSWORD) String pass,
                                    Response.Listener<JSONObject> listener);

    /*-------------------------ECOMMERCE FRAGMENT---------------------------*/

    @RequestUrl("apps/ecommerce_invite.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest ecommerceInvite(@Field(ConstantValues.ID) int id,
                                      @Field(ConstantValues.PASSWORD) String pass,
                                      Response.Listener<JSONObject> listener);

    @RequestUrl("apps/ecommerce_guest_invite.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest ecommerceGuestInvite(@Field(ConstantValues.ID) int id,
                                           @Field(ConstantValues.PASSWORD) String password,
                                           @Field(ConstantValues.EcommerceInvite.GUEST_MOBILE) String mobile,
                                           @Field(ConstantValues.EcommerceInvite.GUEST_NAME) String name,
                                           @Field(ConstantValues.EcommerceInvite.PIN) String pincode,
                                           Response.Listener<JSONObject> listener);

    @RequestUrl("apps/invitation_sms_confirm.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest invitation_sms_confirm(@Field(ConstantValues.ID) int id,
                                             @Field(ConstantValues.PASSWORD) String password,
                                             @Field(ConstantValues.EcommerceInvite.GUEST_MOBILE) String mobile,
                                             @Field(ConstantValues.EcommerceInvite.GUEST_NAME) String name,
                                             @Field(ConstantValues.EcommerceInvite.PIN) String pincode,
                                             Response.Listener<JSONObject> listener);

    @RequestUrl("apps/ecommerce_commission.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest ecommerceCommission(@Field(ConstantValues.ID) int id,
                                          @Field(ConstantValues.PASSWORD) String pass,
                                          Response.Listener<JSONObject> listener);

    @RequestUrl("apps/invitation_deleteOrResend.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest invitation_delete(@Field(ConstantValues.ID) int id,
                                        @Field(ConstantValues.PASSWORD) String pass,
                                        @Field(ConstantValues.EcommerceInvite.GUEST_MOBILE) String mobile,
                                        @Field(ConstantValues.EcommerceInvite.MESSAGE) String message,
                                        Response.Listener<JSONObject> listener);


    /*---------------------------GALLERY FRAGMENT---------------------------*/

    @RequestUrl("apps/gallery_photo.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest loadPhotoGallery(@Field(ConstantValues.ID) int id,
                                       @Field(ConstantValues.PASSWORD) String pass,
                                       Response.Listener<JSONObject> listener);

    @RequestUrl("apps/gallery_video.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest loadVideoGallery(@Field(ConstantValues.ID) int id,
                                       @Field(ConstantValues.PASSWORD) String pass,
                                       Response.Listener<JSONObject> listener);

    /*-------------------------------REPORT-----------------------------------*/

    @RequestUrl("apps/report/report_receive.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest receiveReport(@Field(ConstantValues.ID) int id,
                                    @Field(ConstantValues.PASSWORD) String pass,
                                    Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/fund_report.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest addFundReport(@Field(ConstantValues.ID) int id,
                                    @Field(ConstantValues.PASSWORD) String pass,
                                    Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/transfer.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest transferReport(@Field(ConstantValues.ID) int id,
                                     @Field(ConstantValues.PASSWORD) String pass,
                                     Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/withdrawal.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest withdrawalReport(@Field(ConstantValues.ID) int id,
                                       @Field(ConstantValues.PASSWORD) String pass,
                                       Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/flexi.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest flexiloadReport(@Field(ConstantValues.ID) int id,
                                      @Field(ConstantValues.PASSWORD) String pass,
                                      @Field(ConstantValues.Report.Flexiload.OPERATOR) String operator,
                                      @Field(ConstantValues.Report.Flexiload.STATUS) String status,
                                      @Field(ConstantValues.Report.Flexiload.SEARCH) String search,
                                      @Field(ConstantValues.Report.Flexiload.START_DATE) String startDate,
                                      @Field(ConstantValues.Report.Flexiload.END_DATE) String endDate,
                                      Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/referral_report.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest referralReport(@Field(ConstantValues.ID) int id,
                                     @Field(ConstantValues.PASSWORD) String pass,
                                     Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/refer_pool.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest referralProductReport(@Field(ConstantValues.ID) int id,
                                            @Field(ConstantValues.PASSWORD) String pass,
                                            Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/global_pool.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest globalProductReport(@Field(ConstantValues.ID) int id,
                                          @Field(ConstantValues.PASSWORD) String pass,
                                          Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/salary_report.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest salaryReport(@Field(ConstantValues.ID) int id,
                                   @Field(ConstantValues.PASSWORD) String pass,
                                   Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/geminate_income.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest geminateIncomeReport(@Field(ConstantValues.ID) int id,
                                           @Field(ConstantValues.PASSWORD) String pass,
                                           Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/rank.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest rankReport(@Field(ConstantValues.ID) int id,
                                 @Field(ConstantValues.PASSWORD) String pass,
                                 Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/promotional_incentive.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest promotionalIncentiveReport(@Field(ConstantValues.ID) int id,
                                                 @Field(ConstantValues.PASSWORD) String pass,
                                                 Response.Listener<JSONObject> listener);

    @RequestUrl("apps/report/marketing_incentive.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest marketingIncentiveReport(@Field(ConstantValues.ID) int id,
                                               @Field(ConstantValues.PASSWORD) String pass,
                                               Response.Listener<JSONObject> listener);
}