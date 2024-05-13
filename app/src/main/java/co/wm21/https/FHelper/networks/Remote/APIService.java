package co.wm21.https.FHelper.networks.Remote;

import com.google.gson.JsonObject;
import com.wm21ltd.wm21.activities.AgentApply.MarchentAgentModelClass;
import com.wm21ltd.wm21.activities.EcommarceModule.ModelClass.ECOShoppingSlideDataModel;
import com.wm21ltd.wm21.activities.EcommarceModule.ModelClass.HomeCategoryModel;
import com.wm21ltd.wm21.activities.EcommarceModule.ModelClass.HomeSpecialOfferModel;
import com.wm21ltd.wm21.activities.EcommarceModule.ModelClass.PaymentMethodModel;
import com.wm21ltd.wm21.activities.MerchantList.modelclass.MerchantListModelClass;
import com.wm21ltd.wm21.activities.OTPModule.OTPModelClass;
import com.wm21ltd.wm21.networks.Models.AccountExpenseDataModel;
import com.wm21ltd.wm21.networks.Models.AccountIncomeDataModel;
import com.wm21ltd.wm21.networks.Models.AppMarketPermittedModel;
import com.wm21ltd.wm21.networks.Models.BrandAmbassadorDataModel;
import com.wm21ltd.wm21.networks.Models.CheckExistingUserModel;
import com.wm21ltd.wm21.networks.Models.CountryDataModel;
import com.wm21ltd.wm21.networks.Models.ECOShoppingCategoryDetailModel;
import com.wm21ltd.wm21.networks.Models.ECOShoppingCustomerInfoModel;
import com.wm21ltd.wm21.networks.Models.ECOShoppingHomeCategoryModel;
import com.wm21ltd.wm21.networks.Models.ECOShoppingProductDetailsModel;
import com.wm21ltd.wm21.networks.Models.ECOShoppingProductModel;
import com.wm21ltd.wm21.networks.Models.ECOShoppingProductSearchModel;
import com.wm21ltd.wm21.networks.Models.ForgetPasswordModel;
import com.wm21ltd.wm21.networks.Models.FranchiseAccountDataModel;
import com.wm21ltd.wm21.networks.Models.FranchiseInfoSearchDataModel;
import com.wm21ltd.wm21.networks.Models.FundTransferReportDataModel;
import com.wm21ltd.wm21.networks.Models.GalleryImageModel;
import com.wm21ltd.wm21.networks.Models.GenerationDataModel;
import com.wm21ltd.wm21.networks.Models.GenerationDetailsDataModel;
import com.wm21ltd.wm21.networks.Models.HomeMenuDataModel;
import com.wm21ltd.wm21.networks.Models.IncomeBalaceReportDataModel;
import com.wm21ltd.wm21.networks.Models.IncomeDetailsModel;
import com.wm21ltd.wm21.networks.Models.InternetPakageModel;
import com.wm21ltd.wm21.networks.Models.LoginInfoDataModel;
import com.wm21ltd.wm21.networks.Models.MatchingModel;
import com.wm21ltd.wm21.networks.Models.MemberDetailsModel;
import com.wm21ltd.wm21.networks.Models.MigratePoductModel;
import com.wm21ltd.wm21.networks.Models.MinisterBranchModel;
import com.wm21ltd.wm21.networks.Models.MinisterLoginModel;
import com.wm21ltd.wm21.networks.Models.MinisterProductDetailsModel;
import com.wm21ltd.wm21.networks.Models.MinisterProductModel;
import com.wm21ltd.wm21.networks.Models.MinisterPurchaseModel;
import com.wm21ltd.wm21.networks.Models.MyFranchiseDataModel;
import com.wm21ltd.wm21.networks.Models.NotificationOfferModel;
import com.wm21ltd.wm21.networks.Models.OTPMainModel;
import com.wm21ltd.wm21.networks.Models.OwnFundInfo;
import com.wm21ltd.wm21.networks.Models.PreRegistrationDataModel;
import com.wm21ltd.wm21.networks.Models.PurchaseReportModel;
import com.wm21ltd.wm21.networks.Models.ReferInfoDataModel;
import com.wm21ltd.wm21.networks.Models.RewardAchievementDataModel;
import com.wm21ltd.wm21.networks.Models.RewardFundDataModel;
import com.wm21ltd.wm21.networks.Models.RewardGalleryDataModel;
import com.wm21ltd.wm21.networks.Models.RewardPolicyDataModel;
import com.wm21ltd.wm21.networks.Models.SearchModel;
import com.wm21ltd.wm21.networks.Models.SendToSearchModel;
import com.wm21ltd.wm21.networks.Models.ServiceSubCategoryModel;
import com.wm21ltd.wm21.networks.Models.TSNFCategoryDetailsModel;
import com.wm21ltd.wm21.networks.Models.TeamInfoDataModel;
import com.wm21ltd.wm21.networks.Models.TopupRateModel;
import com.wm21ltd.wm21.networks.Models.TopupReportModel;
import com.wm21ltd.wm21.networks.Models.TopupUserInfo;
import com.wm21ltd.wm21.networks.Models.TrainingServiceNewsDataModel;
import com.wm21ltd.wm21.networks.Models.UserProfileInfoModel;
import com.wm21ltd.wm21.networks.Models.WalletModel;
import com.wm21ltd.wm21.networks.Models.WithdrawReportModel;
import com.wm21ltd.wm21.networks.Models.ZonalModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {

    //For get reference id information
    @POST("api/refer_info.php")
    @FormUrlEncoded
    Call<ReferInfoDataModel> getReferInfo(@Field("user") String referUserName);

    //get country list
    @POST("api/rbp_list.php")
    @FormUrlEncoded
    Call<CountryDataModel> getCountryList(@Field("country_check") String countryCheckData);


    //Check existing user
    @POST("api/check.php")
    @FormUrlEncoded
    Call<CheckExistingUserModel> checkExistingUser(@Field("user_check") String existingUserName);

    //pre-Registration submit
    @POST("api/registration.php")
    @FormUrlEncoded
    Call<PreRegistrationDataModel> submitPreRegistration(@Field("referral_id") String referID,
                                                         @Field("user") String newUserName,
                                                         @Field("name") String fullName,
                                                         @Field("gender") String gender,
                                                         @Field("email") String emailAddress,
                                                         @Field("mobile") String mobileNumber,
                                                         @Field("mobile2") String mobileNumberTwo,
                                                         @Field("country") String countryID,
                                                         @Field("district") String district,
                                                         @Field("ps") String thana,
                                                         @Field("address") String address,
                                                         @Field("comment") String comments,
                                                         @Field("device_id") String deviceID);

    //get home menu dynamically
    @POST("api/menu.php")
    @FormUrlEncoded
    Call<HomeMenuDataModel> getHomeMenuList(@Field("login") String userIsLogin);

    //get login response
    @POST("api/login.php")
    @FormUrlEncoded
    Call<LoginInfoDataModel> submitLogin(@Field("user_name") String userName,
                                         @Field("password") String userPass,
                                         @Field("category") String userCategory);

    //for load tree
    @POST("api/tree.php")
    @FormUrlEncoded
    Call<JsonObject> getTreeData(@Field("id") String ID, @Field("user_id") String userID);

    //for load tree user profile info
    @POST("api/trees.php")
    @FormUrlEncoded
    Call<JsonObject> getTreesData(@Field("id") String userID);

    //get Joining form info
    @POST("api/join_form.php")
    @FormUrlEncoded
    Call<JsonObject> getJoiningFormFields(@Field("type") String userType,
                                          @Field("minister") String minister,
                                          @Field("euser") String userID);

    //Check Joining form existing user(user name)
    @POST("api/check.php")
    @FormUrlEncoded
    Call<JsonObject> checkExistinigUser(@Field("user_query") String userKeyward);

    @POST("api/check.php")
    @FormUrlEncoded
    Call<JsonObject> getRefferelID(@Field("refer_check") String nullValue,
                                   @Field("user") String userKeyward);

    //Check Placement Hand for user
    @POST("api/check.php")
    @FormUrlEncoded
    Call<JsonObject> getPlacementHand(@Field("placement_hand") String nullValue,
                                      @Field("user") String userKeyward);

    //Get Country List
    @POST("api/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getCountrysList(@Field("country_check") String identifyNull);

    //Get Division List
    @POST("api/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getDivisionList(@Field("division_check") String identifyNull,
                                     @Field("Country_ID") String countryID);

    //Get District List
    @POST("api/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getDistrictList(@Field("district_check") String identifyNull,
                                     @Field("Division_ID") String divisionID,
                                     @Field("Country_ID") String countryID);

    //Get Thana List
    @POST("api/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getThanaList(@Field("thana_check") String identifyNull,
                                  @Field("District_ID") String districtID,
                                  @Field("Division_ID") String divisionID,
                                  @Field("Country_ID") String countryID);

    //Get Thana List
    @POST("api/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getUnionList(@Field("union_check") String identifyNull,
                                  @Field("Thana_ID") String thanaID,
                                  @Field("District_ID") String districtID,
                                  @Field("Division_ID") String divisionID,
                                  @Field("Country_ID") String countryID);

    //Get Thana List
    @POST("api/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getwordList(@Field("word_check") String identifyNull,
                                 @Field("Union_ID") String unionID,
                                  @Field("Thana_ID") String thanaID,
                                  @Field("District_ID") String districtID,
                                  @Field("Division_ID") String divisionID,
                                  @Field("Country_ID") String countryID);

    //Submit Joining form
    @Multipart
    @POST("api/join.php")
    Call<JsonObject> submitJoiningForm(@Part("user_name") RequestBody userName,
                                       @Part("password") RequestBody userPass,
                                       @Part("minister") RequestBody minister,
                                       @Part("category") RequestBody userCategory,
                                       @Part("user") RequestBody joinUser,
                                       @Part("email") RequestBody newEmail,
                                       @Part("name") RequestBody fullName,
                                       @Part("mobile") RequestBody userMobile,
                                       @Part("gender") RequestBody userGender,
                                       @Part("agent") RequestBody agentID,
                                       @Part("country") RequestBody countryID,
                                       @Part("division") RequestBody divisionID,
                                       @Part("distict") RequestBody districtID,
                                       @Part("thana") RequestBody thanaID,
                                       @Part("nid") RequestBody nidNumber,
                                       @Part("referral_id") RequestBody referralId,
                                       @Part("product") RequestBody productName,
                                       @Part("placement_id") RequestBody placementID,
                                       @Part("placement_hand") RequestBody placementHand,
                                       @Part MultipartBody.Part image,
                                       @Part("first_password") RequestBody newUserPassword,
                                       @Part("re_password") RequestBody newUserRePassword,
                                       @Part("type") RequestBody userType,
                                       @Part("euser") RequestBody userID,
                                       @Part("merchant") RequestBody merchant);

    //Submit Joining form(without image)
    @POST("api/join.php")
    @FormUrlEncoded
    Call<JsonObject> submitJoiningFormTwo(@Field("user_name") String userName,
                                          @Field("password") String userPass,
                                          @Field("minister") String minister,
                                          @Field("category") String userCategory,
                                          @Field("user") String joinUser,
                                          @Field("email") String newEmail,
                                          @Field("name") String fullName,
                                          @Field("mobile") String userMobile,
                                          @Field("gender") String userGender,
                                          @Field("agent") String agentID,
                                          @Field("country") String countryID,
                                          @Field("division") String divisionID,
                                          @Field("distict") String districtID,
                                          @Field("thana") String thanaID,
                                          @Field("nid") String nidNumber,
                                          @Field("referral_id") String referralId,
                                          @Field("product") String productName,
                                          @Field("placement_id") String placementID,
                                          @Field("placement_hand") String placementHand,
                                          @Field("image") String imageUrl,
                                          @Field("first_password") String newUserPassword,
                                          @Field("re_password") String newUserRePassword,
                                          @Field("type") String userType,
                                          @Field("euser") String userID,
                                          @Field("merchant") String merchant);


    //Get User Profile Information
    @POST("api/profile_info.php")
    @FormUrlEncoded
    Call<UserProfileInfoModel> getUserProfileInfo(@Field("user_id") String userId);


    //search user
    @POST("api/search.php")
    @FormUrlEncoded
    Call<SearchModel> searchUser(@Field("search") String search,
                                 @Field("whom") String whom);


    //for get Income balance statement
    //for get balance statement
    @POST("api/accounts.php")
    @FormUrlEncoded
    Call<IncomeBalaceReportDataModel> getIncomeStatus(@Field("id") String userID);

/*    @POST("api/accounts.php")
    @FormUrlEncoded
    Call<JsonObject> getIncomeStatus(@Field("id") String userID);*/

    //Get withdraw information
    @POST("api/withdraw_info.php")
    @FormUrlEncoded
    Call<JsonObject> getWithdrawInfo(@Field("member_id") String userID);

    //for get payment methods
    @POST("api/online_payment.php")
    @FormUrlEncoded
    Call<JsonObject> getPaymentMethod(@Field("member_id") String userID);

    //confirm withdraw
    @POST("api/withdraw.php")
    @FormUrlEncoded
    Call<JsonObject> submitWithdraw(@Field("user_name") String userName,
                                    @Field("password") String userPass,
                                    @Field("category") String userCategory,
                                    @Field("payment_id") String paymentID,
                                    @Field("withdraw_charge") String withdrawCharge,
                                    @Field("amount") String withdrawAmount,
                                    @Field("payment_charge") String paymentCharge,
                                    @Field("zonal") String zonal);


    //for get generation List
    @POST("api/generation.php")
    @FormUrlEncoded
    Call<GenerationDataModel> getGeneration(@Field("id") String userID);

    //Get Generation Details
    @POST("api/generation_details.php")
    @FormUrlEncoded
    Call<GenerationDetailsDataModel> getGenerationDetailsList(@Field("serial") String serial,
                                                              @Field("member_id") String memberID,
                                                              @Field("limit") String dataLimit);

    //for team Info data
    @POST("api/team_info.php")
    @FormUrlEncoded
    Call<TeamInfoDataModel> getTeamInfoData(@Field("id") String userID);


    //Change Password
    @POST("api/password.php")
    @FormUrlEncoded
    Call<JsonObject> changePassword(@Field("user_name") String user_name,
                                    @Field("password") String password,
                                    @Field("category") String category,
                                    @Field("old_password") String old_password,
                                    @Field("new_password") String new_password,
                                    @Field("again_password") String again_password);


    //check existing phone number
    @POST("api/mobile_check.php")
    @FormUrlEncoded
    Call<JsonObject> checkExistingPhone(@Field("mobile") String mobileNumber);


    //ECO Home Product List
    @POST("api/home_page.php")
    @FormUrlEncoded
    Call<ECOShoppingProductModel> getEcoHomeProducts(@Field("user_id") String userID);


    //E-commerce Product Details
    @POST("api/product_details.php")
    @FormUrlEncoded
    Call<ECOShoppingProductDetailsModel> getEcoProductDetails(@Field("product_id") String ecoProductId);


    //ECO Home Product Category List
    @POST("api/home_page_category.php")
    @FormUrlEncoded
    Call<ECOShoppingHomeCategoryModel> getEcoHomeCategories(@Field("user_id") String userID);


    //E-commerce Product Search
    @POST("api/search_product.php")
    @FormUrlEncoded
    Call<ECOShoppingProductSearchModel> getEcoProductSearchList(@Field("user_id") String userID,
                                                                @Field("search") String search);


    //E-commerce Product Category
    @POST("api/category_wise_prodict.php")
    @FormUrlEncoded
    Call<ECOShoppingCategoryDetailModel> getEcoProductsByCategory(@Field("category") String category,
                                                                  @Field("limit") String limit);

    //get My Franchise Information
    @POST("api/franchise_under.php")
    @FormUrlEncoded
    Call<MyFranchiseDataModel> getMyFranchiseData(@Field("user_id") String userID);


    //E-commerce Product Category
    @POST("api/cart_submit.php")
    @FormUrlEncoded
    Call<ECOShoppingCustomerInfoModel> submitEcoCustomerInfo(@Field("unique_id") String unique_id,
                                                             @Field("device_id") String device_id,
                                                             @Field("user_name") String user_name,
                                                             @Field("name") String name,
                                                             @Field("mobile") String mobile,
                                                             @Field("address") String address);

    @POST("api/cart_insert.php")
    @FormUrlEncoded
    Call<JsonObject> submitEcoOrder(@Field("unique_id") String uniqueID,
                                    @Field("product_id") String productID,
                                    @Field("quantity") String productQty,
                                    @Field("point") String point,
                                    @Field("size") String productSize,
                                    @Field("color") String productColor,
                                    @Field("special_price") String productSpecialPrice,
                                    @Field("money") String productMoney,
                                    @Field("device_id") String productDeviceID,
                                    @Field("user_id") String productUserID,
                                    @Field("ship_cost") String productShipCost);


    //for blood request
    @POST("api/blood.php")
    @FormUrlEncoded
    Call<JsonObject> submitBloodRequest(@Field("user_name") String userName,
                                        @Field("category") String userCategory,
                                        @Field("blood") String bloodGroup);

    //Brand ambassador list
    @POST("api/brand_ambassador.php")
    @FormUrlEncoded
    Call<BrandAmbassadorDataModel> getBrandAmbassador(@Field("limit") String limit);


    //Training Service News
    @POST("api/training_service_category.php")
    @FormUrlEncoded
    Call<TrainingServiceNewsDataModel> getTrainingServiceNews(@Field("type") String type,
                                                              @Field("limit") String limit);

    //Training Service News Franchise details
    @POST("api/training_service.php")
    @FormUrlEncoded
    Call<TSNFCategoryDetailsModel> getTSNFCategoryDetails(@Field("type") String type,
                                                          @Field("category") String category,
                                                          @Field("limit") String limit);

    //for search franchise
    @POST("api/franchise_list.php")
    @FormUrlEncoded
    Call<FranchiseInfoSearchDataModel> getFranchiseBySearch(@Field("division") String divisionID,
                                                            @Field("district") String districtID,
                                                            @Field("thana") String thanaID);

    //for submit franchise application
    @POST("api/apply.php")
    @FormUrlEncoded
    Call<JsonObject> submitFranchiseApplication(@Field("service_id") String serviceID,
                                                @Field("user_id") String userID,
                                                @Field("type") String reqType,
                                                @Field("division") String divisionID,
                                                @Field("district") String districtID,
                                                @Field("thana") String thanaID,
                                                @Field("name") String applicationName,
                                                @Field("address") String applicationAddress,
                                                @Field("license") String licenseNo,
                                                @Field("category") String category);

    // get franchise account/commision report
    @POST("api/franchise_commission.php")
    @FormUrlEncoded
    Call<FranchiseAccountDataModel> getFranchiseAccountCommission(@Field("user_id") String userID,
                                                                  @Field("limit") String dataLimit);

    // get Notification and Offer
    @POST("api/offers_notify.php")
    @FormUrlEncoded
    Call<NotificationOfferModel> getNotificationOffer(@Field("limit") String limit,
                                                      @Field("type") String type,
                                                      @Field("category") String category);

    //submit training request
    @POST("api/trainer_apply.php")
    @FormUrlEncoded
    Call<JsonObject> submitTrainerRequest(@Field("user_id") String userID,
                                          @Field("training_category") String trainingCategory,
                                          @Field("title") String title,
                                          @Field("details") String details,
                                          @Field("trainer") String trainer,
                                          @Field("charge") String charge,
                                          @Field("duration") String duration,
                                          @Field("venue") String venue,
                                          @Field("seat") String seat,
                                          @Field("target_date") String target_date,
                                          @Field("division") String division,
                                          @Field("district") String district,
                                          @Field("thana") String thana);

    //get reward policy
    @GET("api/reward_policy.php")
    Call<RewardPolicyDataModel> getRewardPolicy();

    //get Reward fund
    @POST("api/reward_fund.php")
    @FormUrlEncoded
    Call<RewardFundDataModel> getRewardFund(@Field("user_id") String userID);

    //get reward achievement list
    @POST("api/reward_achievement.php")
    @FormUrlEncoded
    Call<RewardAchievementDataModel> getRewardAchievementList(@Field("user_id") String userID);

    //get reward gallery List
    @POST("api/reward_gallery.php")
    @FormUrlEncoded
    Call<RewardGalleryDataModel> getRewardGallery(@Field("user_id") String userID,
                                                  @Field("limit") String dataLimit);

    // get gallery albums
    @POST("api/gallery.php")
    @FormUrlEncoded
    Call<GalleryImageModel> getGalleryImages(@Field("type") String type,
                                             @Field("limit") String limit);

    // get gallery Album images
    @POST("api/gallery_details.php")
    @FormUrlEncoded
    Call<GalleryImageModel> getGalleryAlbumImages(@Field("gallery_id") String galleryId,
                                                  @Field("limit") String limit);

    //Submit Online Payment
    @POST("api/online_payment_submit.php")
    @FormUrlEncoded
    Call<JsonObject> submitOnlinePayment(@Field("user_name") String userName,
                                         @Field("password") String userPass,
                                         @Field("category") String userCategory,
                                         @Field("id") String paymentMtdID,
                                         @Field("account_name") String accName,
                                         @Field("bank") String bankName,
                                         @Field("account_number") String bankAccNO);


    //Edit Profile
    @POST("api/profile_edit.php")
    @FormUrlEncoded
    Call<JsonObject> submitProfileEdit(@Field("user_id") String userId,
                                       @Field("email") String email,
                                       @Field("birth") String birth,
                                       @Field("blood") String blood,
                                       @Field("address") String address,
                                       @Field("profession") String profession,
                                       @Field("education") String education,
                                       @Field("nid") String nid,
                                       @Field("father_name") String fatherName,
                                       @Field("mother_name") String motherName,
                                       @Field("division") String division,
                                       @Field("district") String district,
                                       @Field("thana") String thana);

    @Multipart
    @POST("api/profile_update.php")
    Call<JsonObject> uploadProfileImage(@Part("user_id") RequestBody user_name,
                                        @Part("password") RequestBody password,
                                        @Part MultipartBody.Part image,
                                        @Part("category") RequestBody category);


    // get trainer list
    @POST("api/trainer_list.php")
    @FormUrlEncoded
    Call<JsonObject> getTrainerList(@Field("limit") String limit);

    //Maintenance Break api
    @POST("api/construction.php")
    @FormUrlEncoded
    Call<JsonObject> getMaintenanceStatus(@Field("user_id") String userID,
                                          @Field("password") String password);

    //Training Service News Franchise details
    @POST("api/search_in_service.php")
    @FormUrlEncoded
    Call<TSNFCategoryDetailsModel> getServiceDetails(@Field("type") String type,
                                                     @Field("division") String division,
                                                     @Field("district") String district,
                                                     @Field("thana") String thana,
                                                     @Field("category") String category,
                                                     @Field("sub_category") String sub_category,
                                                     @Field("limit") String limit);

    //Service Sub Category
    @POST("api/training_service_subcat.php")
    @FormUrlEncoded
    Call<ServiceSubCategoryModel> getServiceSubCategory(@Field("type") String type,
                                                        @Field("category") String category,
                                                        @Field("limit") String limit);


    //firebase token send to server
    @POST("api/push_notification.php")
    @FormUrlEncoded
    Call<JsonObject> sendFirebaseToken(@Field("user_name") String userName,
                                       @Field("password") String userPassword,
                                       @Field("category") String userCategory,
                                       @Field("token") String fbaseToken,
                                       @Field("device_id") String deviceID);


    //get Member Details
    @POST("api/rank_list.php")
    @FormUrlEncoded
    Call<MemberDetailsModel> getMemberDetails(@Field("user_id") String userId,
                                              @Field("rank") String rank,
                                              @Field("limit") String limit);

    //get Member Details
    @POST("api/ministerlogin.php")
    @FormUrlEncoded
    Call<MinisterLoginModel> getMinisterLogin(@Field("user_name") String userName,
                                              @Field("password") String password);

    // get minister product
    @GET("api/ministerproduct.php")
    Call<MinisterProductModel> getMinisterproducts();


    // get minister product src
    @POST("api/minister_product_src.php")
    @FormUrlEncoded
    Call<MinisterProductModel> getMinisterProductsSearch(@Field("search") String search);


    //submit Minister Sale
    @POST("api/ministersale.php")
    @FormUrlEncoded
    Call<JsonObject> submitMinisterSale(@Field("id") String id,
                                        @Field("name") String name,
                                        @Field("mobile") String mobile,
                                        @Field("address") String address,
                                        @Field("price") String price,
                                        @Field("branch") String branch,
                                        @Field("product") String product,
                                        @Field("referral_id") String referralId);


    //Get Minister Purchases report
    @POST("api/minister_report.php")
    @FormUrlEncoded
    Call<MinisterPurchaseModel> getMinisterPurchases(@Field("id") String id,
                                                     @Field("type") String type,
                                                     @Field("day") String day,
                                                     @Field("month") String month,
                                                     @Field("year") String year,
                                                     @Field("limit") String limit);

    //Get minister product detail
    @POST("api/minister_product_detail.php")
    @FormUrlEncoded
    Call<MinisterProductDetailsModel> getMinisterProductDetails(@Field("product_id") String productId);

    // get minister branch
    @POST("api/minister_branch.php")
    @FormUrlEncoded
    Call<MinisterBranchModel> getMinisterBranches(@Field("division") String division,
                                                  @Field("district") String district,
                                                  @Field("limit") String limit);

    // get matching list
    @POST("api/matching_list.php")
    @FormUrlEncoded
    Call<MatchingModel> getMatchingList(@Field("user_id") String userId,
                                        @Field("limit") String limit);

    // get matching list
    @POST("api/withdraw_report.php")
    @FormUrlEncoded
    Call<WithdrawReportModel> getWithdrawReport(@Field("user_id") String userId,
                                                @Field("limit") String limit);


    //get fund transfer request initial info
    @POST("api/fund_transfer_info.php")
    @FormUrlEncoded
    Call<JsonObject> getFundTransferInfo(@Field("member_id") String userID,
                                         @Field("type") String transferType);

    //get status
    @POST("api/status.php")
    @FormUrlEncoded
    Call<JsonObject> getStatus(@Field("user_id") String userId);

    //get Send to fund user name search result
    @POST("api/fund_transfer_info1.php")
    @FormUrlEncoded
    Call<SendToSearchModel> getSendToList(@Field("search") String receiverName);


    //get fun transfer option
    @GET("api/fund_transfer_method.php")
    Call<JsonObject> getFundTransferMethods();

    //get fun transfer option
    @GET("api/fund_transfer_method2.php")
    Call<JsonObject> getFundTransferMethods2();

    //submit fund transfer form
    @POST("api/fund_transfer.php")
    @FormUrlEncoded
    Call<JsonObject> submitFundTransfer(@Field("user_name") String userName,
                                        @Field("password") String userPass,
                                        @Field("category") String userCategory,
                                        @Field("type") String fundTransferType,
                                        @Field("receiver_id") String receiverID,
                                        @Field("amount") String amount,
                                        @Field("payment_charge") String paymentCharge,
                                        @Field("commend") String userComments);


    //get wallets
    @POST("api/wallet.php")
    @FormUrlEncoded
    Call<WalletModel> getWallets(@Field("user_id") String userId);


    //get Fund transfer report list
    @POST("api/transfer_report.php")
    @FormUrlEncoded
    Call<FundTransferReportDataModel> getFundTransferReport(@Field("user_id") String userId,
                                                            @Field("limit") String listLimit,
                                                            @Field("type") String reportType);

    //get Fund purchase report list
    @POST("api/purchase_report.php")
    @FormUrlEncoded
    Call<PurchaseReportModel> getPurchaseReport(@Field("user_id") String userId,
                                                @Field("limit") String listLimit);

    // get Join Report
    @POST("api/join_report.php")
    @FormUrlEncoded
    Call<PurchaseReportModel> getJoinReport(@Field("user_id") String userId,
                                            @Field("date") String date,
                                            @Field("limit") String listLimit);

    //submit won transfer
    @POST("api/own_fund_transfer.php")
    @FormUrlEncoded
    Call<JsonObject> submitWonTransfer(@Field("user_name") String userName,
                                       @Field("password") String userPass,
                                       @Field("category") String userCategory,
                                       @Field("amount") String userAmount);


    // own fund transfer info
    @POST("api/own_fund_transfer_info.php")
    @FormUrlEncoded
    Call<OwnFundInfo> getOwnFundInfo(@Field("user_id") String user_id);


    //for get Income balance statement
    @POST("api/accounts_income.php")
    @FormUrlEncoded
    Call<AccountIncomeDataModel> getIncomeBalanceReportStatus(@Field("id") String userID);

    //for get Expense balance statement
    @POST("api/accounts_expense.php")
    @FormUrlEncoded
    Call<AccountExpenseDataModel> getExpenseBalanceReportStatus(@Field("id") String userID);


    //for get balance statement
    @POST("api/accounts2.php")
    @FormUrlEncoded
    Call<IncomeBalaceReportDataModel> getIncomeStatus2(@Field("id") String userID);

    //for get shop balance statement
    @POST("api/accounts_income2.php")
    @FormUrlEncoded
    Call<AccountIncomeDataModel> getIncomeBalanceReportStatus2(@Field("id") String userID);

    //for get Expense2 balance statement
    @POST("api/accounts_expense2.php")
    @FormUrlEncoded
    Call<AccountExpenseDataModel> getExpenseBalanceReportStatus2(@Field("id") String userID);


    //app marketing permitted list
    @POST("api/appspermit.php")
    @FormUrlEncoded
    Call<AppMarketPermittedModel> getAppMarketsPrmitted(@Field("search") String search);


    //forget password -
    @POST("api/email_check.php")
    @FormUrlEncoded
    Call<ForgetPasswordModel> sendVerifyCode(@Field("user_check") String userCheck);


    //forget password -
    @POST("api/email_check.php")
    @FormUrlEncoded
    Call<JsonObject> submitVerifyCode(@Field("user") String user,
                                      @Field("code") String code);

    //Forget password
    @POST("api/email_check.php")
    @FormUrlEncoded
    Call<JsonObject> submitForgetPassword(@Field("user") String user,
                                          @Field("new") String newPassword,
                                          @Field("again") String againPassword);


    //Rank gallery details List
    @POST("api/report.php")
    @FormUrlEncoded
    Call<IncomeDetailsModel> getIncomeDetails(@Field("user_id") String userId,
                                              @Field("title") String title,
                                              @Field("limit") String limit);

    //Rank gallery details List
    @POST("api/report2.php")
    @FormUrlEncoded
    Call<IncomeDetailsModel> getIncomeDetails2(@Field("user_id") String userId,
                                               @Field("title") String title,
                                               @Field("limit") String limit);


    //get Zonal List
    @GET("api/zonal_selection.php")
    Call<ZonalModel> getZonalList();


    //submit Migration api
    @POST("api/migration.php")
    @FormUrlEncoded
    Call<JsonObject> submitMigration(@Field("user_id") String userID,
                                     @Field("product") String product);


    // get trainer list
    @GET("api/joining_product.php")
    Call<MigratePoductModel> getMigrateProductList();



    //submit won transfer
    @POST("api/load_request.php")
    @FormUrlEncoded
    Call<JsonObject> submitFlaxiload(@Field("user_id") String userId,
                                     @Field("password") String password,
                                     @Field("number") String number,
                                     @Field("amount") String amount,
                                     @Field("type") String type,
                                     @Field("operator") String operator,
                                     @Field("category") String category,
                                     @Field("country") String country);

    //submit won transfer
    @POST("api/load_request2.php")
    @FormUrlEncoded
    Call<JsonObject> submitBankingOtherCountry(@Field("user_id") String userId,
                                               @Field("name") String name,
                                               @Field("bank") String bank,
                                               @Field("branch") String branch,
                                               @Field("password") String password,
                                               @Field("number") String number,
                                               @Field("amount") String amount,
                                               @Field("type") String type,
                                               @Field("operator") String operator,
                                               @Field("account") String account,
                                               @Field("country") String country);

    //get Load Rate and commission
    @POST("api/load_rate.php")
    @FormUrlEncoded
    Call<TopupRateModel> getLoadRate(@Field("user_id") String userId,
                                     @Field("country") String country,
                                     @Field("operator") String operator,
                                     @Field("amount") String amount);


    //load permission
    @POST("api/load_permission.php")
    @FormUrlEncoded
    Call<TopupUserInfo> getTopupInfo(@Field("user_id") String userId,
                                     @Field("country") String country,
                                     @Field("category") String category);

    //load permission
    @POST("api/load_permission2.php")
    @FormUrlEncoded
    Call<TopupUserInfo> getTopupInfo2(@Field("user_id") String userId,
                                      @Field("country") String country,
                                      @Field("category") String category);


    //get Topup Report
    @POST("api/load_report.php")
    @FormUrlEncoded
    Call<TopupReportModel> getTopupReport(@Field("user_id") String userId,
                                          @Field("type") String type,
                                          @Field("limit") String limit);

    //submit won transfer
    @POST("api/load_package.php")
    @FormUrlEncoded
    Call<InternetPakageModel> getInternetPakage(@Field("user_id") String userId,
                                                @Field("operator") String operator);

    @POST("api/otp.php")
    @FormUrlEncoded
    Call<OTPMainModel>getOTPCode(@Field("user_id")String userID,
                                 @Field("fund_transfer")String isFundTransfer);

    //-------------------------------new-----------------------------------//

    //E-commerce Product Category
    @POST("api/cart_submit.php")
    @FormUrlEncoded
    Call<com.wm21ltd.wm21.activities.EcommarceModule.ModelClass.ECOShoppingCustomerInfoModel> submitEcoCustomerInfonew(@Field("unique_id") String unique_id,
                                                                                                                       @Field("payment_method") String paymentMethod,
                                                                                                                       @Field("trans_id") String transId,
                                                                                                                       @Field("device_id") String device_id,
                                                                                                                       @Field("user_name") String user_name,
                                                                                                                       @Field("name") String name,
                                                                                                                       @Field("mobile") String mobile,
                                                                                                                       @Field("address") String address);

    //ECO Home Product Category List
    @GET("api/home_page_category.php")
    Call<com.wm21ltd.wm21.activities.EcommarceModule.ModelClass.ECOShoppingHomeCategoryModel> getEcoHomeCategoriesnew();

    //ECO Home Product List
    @GET("api/home_page.php")
    Call<com.wm21ltd.wm21.activities.EcommarceModule.ModelClass.ECOShoppingProductModel> getEcoHomeProductsnew();

    @POST("api/cart_insert.php")
    @FormUrlEncoded
    Call<JsonObject> submitEcoOrdernew(@Field("unique_id") String uniqueID,
                                    @Field("product_id") String productID,
                                    @Field("quantity") String productQty,
                                    @Field("point") String point,
                                    @Field("size") String productSize,
                                    @Field("color") String productColor,
                                    @Field("special_price") String productSpecialPrice,
                                    @Field("money") String productMoney,
                                    @Field("device_id") String productDeviceID,
                                    @Field("user_id") String productUserID,
                                    @Field("ship_cost") String productShipCost,
                                    @Field("payment_method") String paymentMethod,
                                    @Field("trans_id") String transId);

    //E-commerce Product Category
    @POST("api/category_wise_prodict.php")
    @FormUrlEncoded
    Call<ECOShoppingCategoryDetailModel> getEcoProductsByCategorynew(@Field("category") String category,
                                                                  @Field("limit") String limit);

    //E-commerce Product Details
    @POST("api/product_details.php")
    @FormUrlEncoded
    Call<ECOShoppingProductDetailsModel> getEcoProductDetailsnew(@Field("product_id") String ecoProductId);

    //E-commerce Product Search
    @POST("api/search_product.php")
    @FormUrlEncoded
    Call<ECOShoppingProductSearchModel> getEcoProductSearchListnew(@Field("search") String search);

    //ECO Home Slide Image List
    @GET("api/slide.php")
    Call<ECOShoppingSlideDataModel> getEcoSlideList();

    //E-commerce Product Category
    @GET("api/category_wise_product.php")
    Call<HomeCategoryModel> getProductByCategoryHome();

    //ECO Home special Product List
    @GET("api/home_special_category.php")
    Call<HomeSpecialOfferModel> getEcoHomeSpecialOfferProducts();

    //get Payment Method
    @POST("api/online_payment.php")
    @FormUrlEncoded
    Call<PaymentMethodModel> getPaymentMethodList(@Field("member_id") String memberId);

    //Agent Apply form
    @POST("api/merchant_post.php")
    @FormUrlEncoded
    Call<MarchentAgentModelClass> agentApply(@Field("shop_type") String shop_type,
                                             @Field("location") String location,
                                             @Field("shop_name") String shop_name,
                                             @Field("shop_phone_number") String shop_phone_number,
                                             @Field("shop_physicial_address") String shop_physicial_address,
                                             @Field("trade_lisence_number") String trade_lisence_number,
                                             @Field("trade_lisence_expire_date") String trade_lisence_expire_date,
                                             @Field("reference_user") String reference_user);
    //preRegistration Form
    @POST("api/registration.php")
    @FormUrlEncoded
    Call<PreRegistrationDataModel> preREgistration(@Field("name") String name,
                                              @Field("email") String email,
                                              @Field("mobile") String mobile,
                                              @Field("referral_id") String referral_id,
                                              @Field("device_id") String device_id,
                                              @Field("password") String password);

    //ECO Home Slide Image List
    @GET("api/merchant_list.php")
    Call<MerchantListModelClass> getMerchantList();

    //otp pin
    //preRegistration Form
    @POST("api/otp.php")
    @FormUrlEncoded
    Call<OTPModelClass> OtpConfirm (@Field("mobile_check") String mobile_check,
                                    @Field("mobile") String mobile);





}
