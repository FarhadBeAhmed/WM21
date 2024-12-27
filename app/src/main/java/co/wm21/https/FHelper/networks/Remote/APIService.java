package co.wm21.https.FHelper.networks.Remote;

import com.google.gson.JsonObject;

import co.wm21.https.FHelper.networks.Models.AccountExpenseDataModel;
import co.wm21.https.FHelper.networks.Models.AccountIncomeDataModel;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.AppliedProductModelHead;
import co.wm21.https.FHelper.networks.Models.BlogsModelHead;
import co.wm21.https.FHelper.networks.Models.BrandAmbassadorDataModel;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.FHelper.networks.Models.CheckoutModel;
import co.wm21.https.FHelper.networks.Models.DeleteItem;
import co.wm21.https.FHelper.networks.Models.DeliveryItemsModelHead;
import co.wm21.https.FHelper.networks.Models.DeliveryReceiveModel;
import co.wm21.https.FHelper.networks.Models.DrawerCatModelHead;
import co.wm21.https.FHelper.networks.Models.EshopListModelHead;
import co.wm21.https.FHelper.networks.Models.FranchiseAccountDataModel;
import co.wm21.https.FHelper.networks.Models.FranchiseInfoSearchDataModel;
import co.wm21.https.FHelper.networks.Models.HomeCategoryHead;
import co.wm21.https.FHelper.networks.Models.HotProductModelHead;
import co.wm21.https.FHelper.networks.Models.LocationModelHead;
import co.wm21.https.FHelper.networks.Models.LoginModel;
import co.wm21.https.FHelper.networks.Models.OrderConfirmModel;
import co.wm21.https.FHelper.networks.Models.OrderItemModelHead;
import co.wm21.https.FHelper.networks.Models.PopularCatProductModelHead;
import co.wm21.https.FHelper.networks.Models.PopularProductViewModelHead;
import co.wm21.https.FHelper.networks.Models.HomeTopSliderImageModelHead;
import co.wm21.https.FHelper.networks.Models.IncomeBalaceReportDataModel;
import co.wm21.https.FHelper.networks.Models.MemberDetailsModel;
import co.wm21.https.FHelper.networks.Models.MyFranchiseDataModel;
import co.wm21.https.FHelper.networks.Models.PremierShopResponseModel;
import co.wm21.https.FHelper.networks.Models.PremierShopViewResponse;
import co.wm21.https.FHelper.networks.Models.ProductDetailsHead;
import co.wm21.https.FHelper.networks.Models.ProductReviewModelHead;
import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead;
import co.wm21.https.FHelper.networks.Models.RatingSubmitModelHead;
import co.wm21.https.FHelper.networks.Models.ReceivedItemsModelHead;
import co.wm21.https.FHelper.networks.Models.RelatedProductModelHead;
import co.wm21.https.FHelper.networks.Models.RewardAchievementDataModel;
import co.wm21.https.FHelper.networks.Models.RewardFundDataModel;
import co.wm21.https.FHelper.networks.Models.RewardGalleryDataModel;
import co.wm21.https.FHelper.networks.Models.SearchModel;
import co.wm21.https.FHelper.networks.Models.ShopTypeModelHead;
import co.wm21.https.FHelper.networks.Models.SignupModel;
import co.wm21.https.FHelper.networks.Models.SignupNumberVerifyModel;
import co.wm21.https.FHelper.networks.Models.TSNFCategoryDetailsModel;
import co.wm21.https.FHelper.networks.Models.TeamInfoDataModel;
import co.wm21.https.FHelper.networks.Models.TeleShopProductModelHead;
import co.wm21.https.FHelper.networks.Models.TopSellingProModelHead;
import co.wm21.https.FHelper.networks.Models.TrainingServiceNewsDataModel;
import co.wm21.https.FHelper.networks.Models.TreeModel;
import co.wm21.https.FHelper.networks.Models.TreesModel;
import co.wm21.https.FHelper.networks.Models.UpdateQty;
import co.wm21.https.FHelper.networks.Models.VendorDetailsModelHead;
import co.wm21.https.FHelper.networks.Models.VendorProductModelHead;
import co.wm21.https.FHelper.networks.Models.VerificationModel;
import co.wm21.https.FHelper.networks.Models.application.BDPStatusResponse;
import co.wm21.https.FHelper.networks.Models.application.EShopRefComResponse;
import co.wm21.https.FHelper.networks.Models.application.EShopStatusResponse;
import co.wm21.https.FHelper.networks.Models.balanceResponse.BalanceModel;
import co.wm21.https.FHelper.networks.Models.genealogy.GenealogyListResponse;
import co.wm21.https.FHelper.networks.Models.transaction.WithdrawalHistoryResponse;
import co.wm21.https.view.fragments.member.model.GenerationDataModel;
import co.wm21.https.view.fragments.member.model.RewardPolicyResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {

    @Multipart
    @POST("apps/profileImage_update.php")
    Call<JsonObject> uploadProfileImage(@Part("user_id") RequestBody user_name,
                                        @Part("password") RequestBody password,
                                        @Part MultipartBody.Part image,
                                        @Part("category") RequestBody category);

    @POST("apps/accounts_income.php")
    @FormUrlEncoded
    Call<AccountIncomeDataModel> getIncomeBalanceReportStatus(@Field("id") String userID);

    @POST("apps/accounts_expense.php")
    @FormUrlEncoded
    Call<AccountExpenseDataModel> getExpenseBalanceReportStatus(@Field("id") String userID);

    @POST("apps/accounts.php")
    @FormUrlEncoded
    Call<IncomeBalaceReportDataModel> getIncomeStatus(@Field("id") String userID);

   /* @GET("apps/reward_policy.php")
    Call<RewardPolicyDataModel> getRewardPolicy();*/

    @POST("apps/reward_fund.php")
    @FormUrlEncoded
    Call<RewardFundDataModel> getRewardFund(@Field("user_id") String userID);

    @POST("apps/reward_achievement.php")
    @FormUrlEncoded
    Call<RewardAchievementDataModel> getRewardAchievementList(@Field("user_id") String userID);

    @POST("apps/apply.php")
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

    @POST("apps/reward_gallery.php")
    @FormUrlEncoded
    Call<RewardGalleryDataModel> getRewardGallery(@Field("user_id") String userID,
                                                  @Field("limit") String dataLimit);


    @POST("apps/genealogy_list.php")
        //@FormUrlEncoded
    Call<GenealogyListResponse> getGenealogyList(@Body JsonObject user_id);

    @POST("apps/withdrawHistory.php")
        //@FormUrlEncoded
    Call<WithdrawalHistoryResponse> getWithdrawalHistory(@Body JsonObject user_id);


    @POST("apps/brand_ambassador.php")
    @FormUrlEncoded
    Call<BrandAmbassadorDataModel> getBrandAmbassador(@Field("limit") String limit);

    @POST("apps/search.php")
    @FormUrlEncoded
    Call<SearchModel> searchUser(@Field("search") String search,
                                 @Field("whom") String whom);

    @POST("apps/blood.php")
    @FormUrlEncoded
    Call<JsonObject> submitBloodRequest(@Field("user_name") String userName,
                                        @Field("category") String userCategory,
                                        @Field("blood") String bloodGroup);


    @POST("apps/team_info.php")
    @FormUrlEncoded
    Call<TeamInfoDataModel> getTeamInfoData(@Field("id") String userID);


    //get Member Details
    @POST("apps/rank_list.php")
    @FormUrlEncoded
    Call<MemberDetailsModel> getMemberDetails(@Field("user_id") String userId,
                                              @Field("rank") String rank,
                                              @Field("limit") String limit);

    @POST("apps/training_service_category.php")
    @FormUrlEncoded
    Call<TrainingServiceNewsDataModel> getTrainingServiceNews(@Field("type") String type,
                                                              @Field("limit") String limit);

    @POST("apps/training_service.php")
    @FormUrlEncoded
    Call<TSNFCategoryDetailsModel> getTSNFCategoryDetails(@Field("type") String type,
                                                          @Field("category") String category,
                                                          @Field("limit") String limit);

    @POST("apps/trainer_apply.php")
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


    @POST("apps/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getDivisionList(@Field("division_check") String identifyNull,
                                     @Field("Country_ID") String countryID);

    //Get District List
    @POST("apps/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getDistrictList(@Field("district_check") String identifyNull,
                                     @Field("Division_ID") String divisionID,
                                     @Field("Country_ID") String countryID);

    @POST("apps/trees.php")
    @FormUrlEncoded
    Call<TreesModel> getTreesData(@Field("id") String userID);

    /* @POST("api/trees.php")
     @FormUrlEncoded
     Call<TreesModel> getTreesData(@Field("id") String userID);*/
    //for load tree
    @POST("apps/tree.php")
    @FormUrlEncoded
    Call<TreeModel> getTreeData(@Field("id") String ID, @Field("user_id") String userID);

    @POST("apps/balance.php")
    @FormUrlEncoded
    Call<BalanceModel> allBalances(@Field("user_id") String userID);


    @POST("apps/reward_policy.php")
    @FormUrlEncoded
    Call<RewardPolicyResponse> getRewardPolicy(@Field("user_id") String userID);


    //for get generation List
    @POST("api/generation.php")
    @FormUrlEncoded
    Call<GenerationDataModel> getGeneration(@Field("id") String userID);

    @POST("apps/categories.php")
    @FormUrlEncoded
    Call<DrawerCatModelHead> getDrawerCatList(@Field("id") int id,
                                              @Field("cat_id") String cat_id);

    @POST("apps/teleshop_product.php")
    @FormUrlEncoded
    Call<TeleShopProductModelHead> getTeleShopProduct(@Field("shop") String shop);

    //Get Thana List
    @POST("apps/rbp_list.php")
    @FormUrlEncoded
    Call<JsonObject> getThanaList(@Field("thana_check") String identifyNull,
                                  @Field("District_ID") String districtID,
                                  @Field("Division_ID") String divisionID,
                                  @Field("Country_ID") String countryID);

    // get trainer list
    @POST("apps/trainer_list.php")
    @FormUrlEncoded
    Call<JsonObject> getTrainerList(@Field("limit") String limit);

    @POST("apps/all_blogs.php")
    @FormUrlEncoded
    Call<BlogsModelHead> getAllBlogs(@Field("limit") int limit);

    @POST("apps/top_selling_product.php")
    @FormUrlEncoded
    Call<TopSellingProModelHead> topSelling(@Field("limit") int limit);

    @POST("apps/customer_product_review.php")
    @FormUrlEncoded
    Call<ProductReviewModelHead> getAllProductReview(@Field("id") String id);

    @POST("apps/product_details.php")
    @FormUrlEncoded
    Call<ProductDetailsHead> getProductDetails(@Field("id") String id);

    @POST("apps/vendor_details.php")
    @FormUrlEncoded
    Call<VendorDetailsModelHead> getVendorDetails(@Field("id") String id);

    @POST("apps/ven_products.php")
    @FormUrlEncoded
    Call<VendorProductModelHead> getVendorProduct(
            @Field("id") String id,
            @Field("limit") String limit
    );

    @POST("apps/apps_products.php")
    @FormUrlEncoded
    Call<RelatedProductModelHead> getRelatedProduct(
            @Field("value") String value,
            @Field("cat_id") String cat_id,
            @Field("scat_id") String scat_id,
            @Field("brand_id") String brand_id
    );



    /*@RequestUrl("apps/add_to_cart.php")
    @SendMethod(Request.Method.POST)
    @NonNull
    JsonObjectRequest addToCart(@co.wm21.https.FHelper.Annotations.Field(ConstantValues.Cart.PRODUCT_ID) String p_id,
                                @co.wm21.https.FHelper.Annotations.Field(ConstantValues.Cart.DEVICE_ID) String u_id,
                                @co.wm21.https.FHelper.Annotations.Field(ConstantValues.Cart.COLOR) String color,
                                @co.wm21.https.FHelper.Annotations.Field(ConstantValues.Cart.SIZE) String size,
                                @co.wm21.https.FHelper.Annotations.Field(ConstantValues.Cart.QUANTITY) int qty,
                                Response.Listener<JSONObject> listener);*/


    @POST("apps/addToCart.php")
    @FormUrlEncoded
    Call<AddToCartModel> addToCart(
            @Field("p_id") String p_id,
            @Field("u_id") String u_id,
            @Field("color") String color,
            @Field("size") String size,
            @Field("qty") int qty
    );

    @POST("apps/cart_items.php")
    @FormUrlEncoded
    Call<CartItemsHead> getCartItems(
            @Field("u_id") String u_id
    );

    @POST("apps/app_checkout.php")
    @FormUrlEncoded
    Call<CheckoutModel> checkout(
            @Field("u_id") String u_id,
            @Field("username") String username
    );

    @POST("apps/update_quantity.php")
    @FormUrlEncoded
    Call<UpdateQty> update_quantity(
            @Field("u_id") String u_id,
            @Field("p_id") String p_id,
            @Field("color") String color,
            @Field("size") String size,
            @Field("qty") int qty,
            @Field("type") int type
    );

    @POST("apps/delete_cart_item.php")
    @FormUrlEncoded
    Call<DeleteItem> deleteFromCart(
            @Field("p_id") String p_id,
            @Field("type") int type
    );

    @POST("apps/app_checkout.php")
    @FormUrlEncoded
    Call<DeleteItem> appCheckout(
            @Field("u_id") String u_id,
            @Field("user_id") String user_id
    );

    @POST("apps/login.php")
    @FormUrlEncoded
    Call<LoginModel> login(
            @Field("user_id") String user_id,
            @Field("password") String password
    );

    @POST("apps/user_profile.php")
    @FormUrlEncoded
    Call<ProfileDetailsHead> profileDetails(
            @Field("user_id") String user_id
    );

    @POST("apps/verification.php")
    @FormUrlEncoded
    Call<VerificationModel> verification(
            @Field("user_id") String user_id
    );

    @POST("apps/order_items.php")
    @FormUrlEncoded
    Call<OrderItemModelHead> orderItems(@Field("user_id") String user_id);

    @POST("apps/all_locations.php")
    @FormUrlEncoded
    Call<LocationModelHead> location(
            @Field("id") String id,
            @Field("value") String value
    );

    @POST("apps/shop_type.php")
    @FormUrlEncoded
    Call<ShopTypeModelHead> shopType(
            @Field("user_id") String user_id
    );

    @POST("apps/eshop_list.php")
    @FormUrlEncoded
    Call<EshopListModelHead> order_eshop(
            @Field("user_id") String user_id,
            @Field("location") String location,
            @Field("locationType") String locationType,
            @Field("searchTxt") String searchTxt
    );

    @POST("apps/order_confirm.php")
    @FormUrlEncoded
    Call<OrderConfirmModel> orderConfirm(
            @Field("user_id") String user_id,
            @Field("eshop") String eshop,
            @Field("shipping") String shipping,
            @Field("address") String address,
            @Field("adjust") int adjust
    );

    @POST("apps/delivery_items.php")
    @FormUrlEncoded
    Call<DeliveryItemsModelHead> delivery_items(
            @Field("user_id") String user_id
    );

    @POST("apps/received_items.php")
    @FormUrlEncoded
    Call<ReceivedItemsModelHead> receivedItems(
            @Field("user_id") String user_id
    );

    @POST("apps/apply_datas.php")
    @FormUrlEncoded
    Call<AppliedProductModelHead> appliedProducts(
            @Field("user_id") String user_id
    );


    @POST("apps/delivery_receive.php")
    @FormUrlEncoded
    Call<DeliveryReceiveModel> deliveryReceive(
            @Field("user_id") String user_id,
            @Field("pin") String pin,
            @Field("action") String action,
            @Field("serial") String serial
    );

    @POST("apps/signUpFirstStep.php")
    @FormUrlEncoded
    Call<SignupModel> signUpFirstStep(
            @Field("mobile") String mobile,
            @Field("country") String country
    );

    @POST("apps/signUpNumberVerify.php")
    @FormUrlEncoded
    Call<SignupNumberVerifyModel> signUpNumberVerify(
            @Field("mobile") String mobile,
            @Field("country") String country,
            @Field("code") String code
    );

    @POST("apps/signUpFinal.php")
    @FormUrlEncoded
    Call<SignupModel> signUpFinal(
            @Field("mobile") String mobile,
            @Field("name") String name,
            @Field("refer") String refer,
            @Field("email") String email,
            @Field("division") String division,
            @Field("country") String country
    );


    @POST("apps/home_top_slider_image.php")
    @FormUrlEncoded
    Call<HomeTopSliderImageModelHead> getHomeTopSlideImageList(@Field("limit") String limit);

    //Get Thana List
    @POST("api/rbp_list.php")
    @FormUrlEncoded
    Call<RatingSubmitModelHead> getRatingSubmit(@Field("username") String username,
                                                @Field("serial") String serial,
                                                @Field("review") String review,
                                                @Field("rating") String rating);

    @POST("apps/category.php")
    @FormUrlEncoded
    Call<HomeCategoryHead> getCategoryList(@Field("limit") String limit);

    @POST("apps/hot_products.php")
    @FormUrlEncoded
    Call<HotProductModelHead> getHotProduct(@Field("limit") int limit);

    @POST("apps/popularProduct.php")
    @FormUrlEncoded
    Call<PopularProductViewModelHead> getPopularProductList(@Field("value") String value);

    @POST("apps/popular_category_product.php")
    @FormUrlEncoded
    Call<PopularCatProductModelHead> getPopularCatProductList(
            @Field("value1") String value1,
            @Field("value2") String value2,
            @Field("value3") String value3,
            @Field("value4") String value4
    );

    //get My Franchise Information
    @POST("apps/franchise_under.php")
    @FormUrlEncoded
    Call<MyFranchiseDataModel> getMyFranchiseData(@Field("user_id") String userID);

    //for search franchise
    @POST("apps/franchise_list.php")
    @FormUrlEncoded
    Call<FranchiseInfoSearchDataModel> getFranchiseBySearch(@Field("division") String divisionID,
                                                            @Field("district") String districtID,
                                                            @Field("thana") String thanaID);

    @POST("apps/franchise_commission.php")
    @FormUrlEncoded
    Call<FranchiseAccountDataModel> getFranchiseAccountCommission(@Field("user_id") String userID,
                                                                  @Field("limit") String dataLimit);


    @POST("apps/eShopStatus.php")
        //@FormUrlEncoded
    Call<EShopStatusResponse> getEShopStatus(@Body JsonObject user_id);

    @POST("apps/eShopRefCom.php")
        //@FormUrlEncoded
    Call<EShopRefComResponse> getEShopRefCom(@Body JsonObject user_id);


    @POST("apps/bdpStatus.php")
        //@FormUrlEncoded
    Call<BDPStatusResponse> getBDPStatus(@Body JsonObject user_id);

    @POST("apps/premierShop.php")
        //@FormUrlEncoded
    Call<PremierShopResponseModel> getPremierShopResponse(@Body JsonObject user_id);


    @POST("apps/premierShopDetails.php")
        //@FormUrlEncoded
    Call<PremierShopViewResponse> getPremierShopViewResponse(
            @Body JsonObject request);


}