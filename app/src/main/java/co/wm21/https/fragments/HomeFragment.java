package co.wm21.https.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.JsonArray;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.FHelper.networks.Models.TopSellingProModel;
import co.wm21.https.R;
import co.wm21.https.SliderItem;
import co.wm21.https.activities.HomeMoreProductActivity;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.activities.ProductDetailsActivity;
import co.wm21.https.adapters.AllBlogsAdapter;
import co.wm21.https.adapters.BlogsAdapter;
import co.wm21.https.adapters.HomeShopsItemAdapter;
import co.wm21.https.adapters.HotDealAdapter;
import co.wm21.https.adapters.HotDealSliderAdapter;
import co.wm21.https.adapters.PopularCatProductAdapter;
import co.wm21.https.adapters.PopularProductAdapter;
import co.wm21.https.adapters.SliderAdapter;
import co.wm21.https.adapters.TopSellingAdapter;
import co.wm21.https.adapters.category.CategoryAdapter;
import co.wm21.https.adapters.category.CategoryView;
import co.wm21.https.adapters.item_menu.ItemMenuAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuView;
import co.wm21.https.adapters.product.ProductAdapter;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.databinding.FragmentMainHomeBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.fragments.company.AboutUsFragment;
import co.wm21.https.fragments.company.ContactUsFragment;
import co.wm21.https.fragments.manageOrder.AppliedFragment;
import co.wm21.https.fragments.manageOrder.DeliveredFragment;
import co.wm21.https.fragments.manageOrder.OrderFragment;
import co.wm21.https.fragments.manageOrder.ReceivedFragment;
import co.wm21.https.fragments.products.CategoryProductFragment;
import co.wm21.https.fragments.shops.BrandShop;
import co.wm21.https.fragments.shops.MissionBazar;
import co.wm21.https.fragments.shops.PremierShop;
import co.wm21.https.fragments.shops.Showroom;
import co.wm21.https.fragments.shops.TeleShop;
import co.wm21.https.fragments.shops.Vendor;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.interfaces.OnHomeCategoryView;
import co.wm21.https.interfaces.OnHomePopularCatProductView;
import co.wm21.https.interfaces.OnHomePopularProductView;
import co.wm21.https.interfaces.OnHomeTopSliderImageView;
import co.wm21.https.interfaces.OnHotProductView;
import co.wm21.https.interfaces.OnTopSellingProView;
import co.wm21.https.model.Blogs;
import co.wm21.https.presenter.BlogListPresenter;
import co.wm21.https.presenter.HomeCategoryPresenter;
import co.wm21.https.presenter.HomePopularCatProductPresenter;
import co.wm21.https.presenter.HomePopularProductPresenter;
import co.wm21.https.presenter.HomeTopSliderImagePresenter;
import co.wm21.https.presenter.HotProductPresenter;
import co.wm21.https.presenter.TopSellingProPresenter;

public class HomeFragment extends Fragment implements OnHomeTopSliderImageView, OnHomeCategoryView, OnHomePopularProductView, OnHotProductView, OnHomePopularCatProductView, OnBlogListView, OnTopSellingProView {

    List<SliderItem> sliderItemList;

    ArrayList<CategoryView> categoryList;
    ArrayList<ProductModel> popularCatProductList1;
    ArrayList<ProductModel> popularCatProductList2;
    ArrayList<ProductModel> popularCatProductList3;
    ArrayList<ProductModel> popularCatProductList4;
    ArrayList<BlogsModel> blogsModels;
    ArrayList<TopSellingProModel>topSellingProList;


    static User user;
    SliderAdapter adapter;
    CategoryAdapter categoryAdapter;
    BlogsAdapter blogsAdapter;
    TopSellingAdapter topSellingAdapter;
    FragmentMainHomeBinding binding;
    Handler mainHandler = new Handler();
    //ProgressDialog progressDialog;
    API api;
    private final Handler sliderHandler = new Handler();
    ArrayList<ProductModel> hotProductViews;

    HotDealAdapter hotDealAdapter;
    HotDealSliderAdapter hotDealSliderAdapter;
    PopularCatProductAdapter popularCatProductAdapter1;
    PopularCatProductAdapter popularCatProductAdapter2;
    PopularCatProductAdapter popularCatProductAdapter3;
    PopularCatProductAdapter popularCatProductAdapter4;
   LoadingDialog loadingDialog;
    HomeTopSliderImagePresenter homeTopSliderImagePresenter;
    HotProductPresenter hotProductPresenter;
    HomeCategoryPresenter homeCategoryPresenter;
    HomePopularProductPresenter homePopularProductPresenter;
    HomePopularCatProductPresenter popularCatProductPresenter;
    TopSellingProPresenter topSellingProPresenter;
    BlogListPresenter blogListPresenter;

    ArrayList<ProductModel> popularProductList;
    PopularProductAdapter popularProductAdapter;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_home, container, false);

        user = new User(getContext());
        categoryList = new ArrayList<>();
        api = ConstantValues.getAPI();
       loadingDialog=new LoadingDialog(getActivity());

        if (!user.getSession().isLoggedIn()) {
            binding.ecommerceMedia.setVisibility(View.GONE);
        } else {
            binding.facebookBtn.setOnClickListener(this::socialActivity);
            binding.ecommerceMedia.setVisibility(View.VISIBLE);
        }

        ArrayList<ItemMenuView> shopList = new ArrayList<ItemMenuView>(Arrays.asList(
                new ItemMenuView("TELE SHOP", "#ffbfbf"),
                new ItemMenuView("SHOWROOM", "#ffbfbf"),
                new ItemMenuView("MISSION BAZAR", "#ffbfbf"),
                new ItemMenuView("BRAND SHOP", "#ffbfbf"),
                new ItemMenuView("PREMIER SHOP", "#ffbfbf"),
                new ItemMenuView("INSURANCE", "#ffbfbf")
        ));

        binding.shopsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.shopsRecyclerView.setAdapter(new HomeShopsItemAdapter(getContext(), shopList, R.layout.layout_item_digital_shop_service).addOnClickListener((View, position) -> {
            switch (position) {
                case 0:
                    switchFragment(new TeleShop(), "TeleShop");
                    break;
                case 1:
                    switchFragment(new Showroom(), "Showroom");
                    break;
                case 2:
                    switchFragment(new MissionBazar(), "MissionBazar");
                    break;
                case 3:
                    switchFragment(new BrandShop(), "BrandShop");
                    break;
                case 4:
                    switchFragment(new PremierShop(), "PremierShop");
                    break;
                case 5:
                    switchFragment(new Vendor(), "Vendor");
                    break;
                default:
            }
        }));
        binding.pullToRefresh.setOnRefreshListener(() -> binding.pullToRefresh.setRefreshing(false));

        binding.orderBtn.setOnClickListener(this::orderManege);
        binding.appliedBtn.setOnClickListener(this::orderManege);
        binding.deliveredBtn.setOnClickListener(this::orderManege);
        binding.receivedBtn.setOnClickListener(this::orderManege);
        binding.bestSellerTxt.setTextColor(Color.parseColor("#FE0000"));
        binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
        binding.bestSellerBtn.setOnClickListener(this::clickForFeatured);
        binding.mostPopularBtn.setOnClickListener(this::clickForFeatured);
        binding.featuredBtn.setOnClickListener(this::clickForFeatured);
        binding.newArrivalBtn.setOnClickListener(this::clickForFeatured);
        binding.footerId.MyAccExpandableLayout.collapse();
        binding.footerId.CustomerExpandableLayout.collapse();
        binding.footerId.infoExpandableLayout.collapse();
        binding.footerId.footerCompany.setOnClickListener(v -> {
            if (binding.footerId.infoExpandableLayout.isExpanded()) {
                binding.footerId.infoExpandableLayout.collapse();
                binding.footerId.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.infoExpandableLayout.expand();
                binding.footerId.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.footerId.footerMyAccount.setOnClickListener(v -> {
            if (binding.footerId.MyAccExpandableLayout.isExpanded()) {
                binding.footerId.MyAccExpandableLayout.collapse();
                binding.footerId.MyAccExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.MyAccExpandableLayout.expand();
                binding.footerId.MyAccExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.footerId.footerCustomer.setOnClickListener(v -> {
            if (binding.footerId.CustomerExpandableLayout.isExpanded()) {
                binding.footerId.CustomerExpandableLayout.collapse();
                binding.footerId.CustomerExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.CustomerExpandableLayout.expand();
                binding.footerId.CustomerExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.footerId.aboutUs.setOnClickListener(view -> {
            switchFragment(new AboutUsFragment(), "AboutUsFragment");
        });
        binding.footerId.contactUs.setOnClickListener(view -> {
            switchFragment(new ContactUsFragment(), "ContactUsFragment");
        });
        binding.moreGroceryProduct.setOnClickListener(this::moreProducts);
        binding.moreElectronicsProduct.setOnClickListener(this::moreProducts);
        binding.beautyMoreProduct.setOnClickListener(this::moreProducts);
        binding.fashionMoreProduct.setOnClickListener(this::moreProducts);
        binding.moreHotDeal.setOnClickListener(this::moreProducts);
        binding.allBlogsBtn.setOnClickListener(this::moreProducts);

        homeTopSliderImagePresenter=new HomeTopSliderImagePresenter(this);
        homeCategoryPresenter=new HomeCategoryPresenter(this);
        homePopularProductPresenter=new HomePopularProductPresenter(this);
        hotProductPresenter=new HotProductPresenter(this);
        popularCatProductPresenter=new HomePopularCatProductPresenter(this);
        blogListPresenter=new BlogListPresenter(this);
        topSellingProPresenter=new TopSellingProPresenter(this);
        topSliderImage();
        //categories();
        popularProduct("2");
        //hotProduct();


        topSellingProduct(8);
        return binding.getRoot();
    }

    private void topSliderImage() {
        sliderItemList = new ArrayList<>();
        adapter = new SliderAdapter(getContext(),sliderItemList);
        //adapter.renewItems(sliderItemList);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.startAutoCycle();
        binding.imageSlider.setSliderAdapter(adapter);

        homeTopSliderImagePresenter.getSliderImageDataResponse("10");
    }

    @SuppressLint("NonConstantResourceId")
    private void moreProducts(View view) {
        Bundle bundle = new Bundle();
        ProductsFragment fragment = new ProductsFragment();
        Intent intent=null;
        switch (view.getId()) {
            case R.id.moreGroceryProduct:
                 intent=new Intent(getContext(), HomeMoreProductActivity.class);
                intent.putExtra(ConstantValues.ARGUMENT1, categoryList.get(0).getCatID());
                intent.putExtra(ConstantValues.ARGUMENT2, categoryList.get(0).getCategoryName());
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(getContext());
                break;
            case R.id.moreElectronicsProduct:
                 intent=new Intent(getContext(), HomeMoreProductActivity.class);
                intent.putExtra(ConstantValues.ARGUMENT1, categoryList.get(1).getCatID());
                intent.putExtra(ConstantValues.ARGUMENT2, categoryList.get(1).getCategoryName());
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(getContext());
                break;
            case R.id.beautyMoreProduct:
                intent=new Intent(getContext(), HomeMoreProductActivity.class);
                intent.putExtra(ConstantValues.ARGUMENT1, categoryList.get(2).getCatID());
                intent.putExtra(ConstantValues.ARGUMENT2, categoryList.get(2).getCategoryName());
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(getContext());
 /*               bundle.putString(ConstantValues.ARGUMENT1, categoryList.get(2).getCatID());
                fragment.setArguments(bundle);
                switchFragment(fragment, "ProductsFragment");*/
                break;
            case R.id.fashionMoreProduct:
                intent=new Intent(getContext(), HomeMoreProductActivity.class);
                intent.putExtra(ConstantValues.ARGUMENT1, categoryList.get(3).getCatID());
                intent.putExtra(ConstantValues.ARGUMENT2, categoryList.get(3).getCategoryName());
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(getContext());
                /*bundle.putString(ConstantValues.ARGUMENT1, categoryList.get(3).getCatID());
                fragment.setArguments(bundle);
                switchFragment(fragment, "ProductsFragment");*/
                break;
            case R.id.moreHotDeal:
                switchFragment(new AllHotDealFragment(), "AllHotDealFragment");
                break;

            case R.id.allBlogsBtn:
                switchFragment(new BlogsFragment(), "BlogsFragment");
                break;
            default:
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void orderManege(View view) {
        switch (view.getId()) {
            case R.id.orderBtn:
                switchFragment(new OrderFragment(), "OrderFragment");
                break;
            case R.id.appliedBtn:
                switchFragment(new AppliedFragment(), "AppliedFragment");
                break;
            case R.id.deliveredBtn:
                switchFragment(new DeliveredFragment(), "DeliveredFragment");
                break;
            case R.id.receivedBtn:
                switchFragment(new ReceivedFragment(), "ReceivedFragment");
                break;
            default:
        }
    }
    private void categories() {
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryList, Constant.GRID_LAYOUT).addOnClickListener((View, position) -> {

            String cat_id = categoryList.get(position).getCatID();
            String name = categoryList.get(position).getCategoryName();
            Bundle bundle = new Bundle();
            bundle.putString(co.wm21.https.FHelper.ConstantValues.ARGUMENT1, cat_id);
            bundle.putString(co.wm21.https.FHelper.ConstantValues.NAME, name);
            CategoryProductFragment categoryProductFragment = new CategoryProductFragment();
            categoryProductFragment.setArguments(bundle);
            //((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.main_home_contain, subSubCategoryFragment, "SubCategoryFragment").addToBackStack("SubCategory").commit();
            ((MainActivity) getActivity()).switchFragment(categoryProductFragment, "CategoryProductFragment");

        });
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoryRecyclerView.setAdapter(categoryAdapter);
        homeCategoryPresenter.getCategoryDataResponse("100");

    }


    @SuppressLint("NonConstantResourceId")
    private void socialActivity(View view) {
        switch (view.getId()) {
            case R.id.facebookBtn:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.FACEBOOK_URL)));
                break;
            case R.id.youtubeBtn:
                break;
            case R.id.orderBtn:
                break;
            case R.id.deliveredBtn:
                break;
            case R.id.receivedBtn:
                break;
            default:
        }
    }


    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    private void clickForFeatured(View view) {

        switch (view.getId()) {
            case R.id.bestSellerBtn:
                binding.bestSellerTxt.setTextColor(Color.parseColor("#FE0000"));
                binding.mostPopularTxt.setTextColor(Color.parseColor("#000000"));
                binding.featuredTxt.setTextColor(Color.parseColor("#000000"));
                binding.newArrivalTxt.setTextColor(Color.parseColor("#000000"));
                binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
                binding.mostPopularBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.newArrivalBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.featuredBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                popularProduct("2");
                break;
            case R.id.mostPopularBtn:
                binding.mostPopularTxt.setTextColor(Color.parseColor("#FE0000"));
                binding.bestSellerTxt.setTextColor(Color.parseColor("#000000"));
                binding.featuredTxt.setTextColor(Color.parseColor("#000000"));
                binding.newArrivalTxt.setTextColor(Color.parseColor("#000000"));

                binding.mostPopularBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
                binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.newArrivalBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.featuredBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));

                popularProduct("3");
                break;
            case R.id.featuredBtn:
                binding.featuredTxt.setTextColor(Color.parseColor("#FE0000"));
                binding.bestSellerTxt.setTextColor(Color.parseColor("#000000"));
                binding.mostPopularTxt.setTextColor(Color.parseColor("#000000"));
                binding.newArrivalTxt.setTextColor(Color.parseColor("#000000"));

                binding.featuredBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
                binding.mostPopularBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.newArrivalBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));

                popularProduct("4");
                break;
            case R.id.newArrivalBtn:
                binding.newArrivalTxt.setTextColor(Color.parseColor("#FE0000"));
                binding.bestSellerTxt.setTextColor(Color.parseColor("#000000"));
                binding.mostPopularTxt.setTextColor(Color.parseColor("#000000"));
                binding.featuredTxt.setTextColor(Color.parseColor("#000000"));

                binding.newArrivalBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
                binding.mostPopularBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.featuredBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));

                popularProduct("1");
                break;
        }

    }

    private void popularProduct(String value) {
        popularProductList = new ArrayList<>();
        popularProductAdapter=new PopularProductAdapter(popularProductList,getContext()).addOnClickListener((View, position2) -> {
            ProductModel productView = popularProductList.get(position2);
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(Constant.Product.PARCEL, productView);
                activity.startActivityForResult(intent, 2);
            }
        });

        binding.productRecyclerView.setAdapter(popularProductAdapter);
        homePopularProductPresenter.getHomePopularProduct(value);



    }

    private void hotProduct() {
        hotProductViews = new ArrayList<>();
        binding.hotDealSlider.setScrollTimeInSec(5);
        binding.hotDealSlider.startAutoCycle();
        hotDealSliderAdapter=new HotDealSliderAdapter(getContext(), hotProductViews, R.layout.layout_item_product_hot_deal);
    /*.addOnClickListener((View, position3) -> {
//            ProductModel productView = hotProductViews.get(position3);
//            Activity activity = getActivity();
//            if (activity != null) {
//                startActivity(new Intent(getContext(), ProductDetailsActivity.class)
//                        .putExtra(Constant.Product.PARCEL, productView));
//            }
        });*/

        binding.hotDealSlider.setSliderAdapter(hotDealSliderAdapter);
        hotProductPresenter.getHotProduct(30);
        fromOurBlog(5);




    }
    private void popularCategoryProduct(String value1,String value2,String value3,String value4) {

        binding.catNameText1.setText(categoryList.get(0).getCategoryName());
        binding.catNameText2.setText(categoryList.get(1).getCategoryName());
        binding.catNameText3.setText(categoryList.get(2).getCategoryName());
        binding.catNameText4.setText(categoryList.get(3).getCategoryName());

        popularCatProductList1 = new ArrayList<>();
        popularCatProductList2 = new ArrayList<>();
        popularCatProductList3 = new ArrayList<>();
        popularCatProductList4 = new ArrayList<>();

        popularCatProductAdapter1=new PopularCatProductAdapter( popularCatProductList1,getContext(), R.layout.layout_item_product2,8).addOnClickListener((View, position2) -> {
                ProductModel productView = popularCatProductList1.get(position2);
                Activity activity = getActivity();
                if (activity != null) {
                    Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                    intent.putExtra(Constant.Product.PARCEL, productView);
                    activity.startActivityForResult(intent, 2);

                }
        });
        binding.productGroceryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.productGroceryRecyclerView.setAdapter(popularCatProductAdapter1);

        popularCatProductAdapter2=   new PopularCatProductAdapter( popularCatProductList2,getContext(), R.layout.layout_item_product2,8).addOnClickListener((View, position2) -> {
            ProductModel productView = popularCatProductList2.get(position2);
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(Constant.Product.PARCEL, productView);
                activity.startActivityForResult(intent, 2);

            }
        });
        binding.productCatRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.productCatRecyclerView2.setAdapter(popularCatProductAdapter2);


        popularCatProductAdapter3=   new PopularCatProductAdapter(popularCatProductList3, getContext(),  R.layout.layout_item_product2,8).addOnClickListener((View, position2) -> {
            ProductModel productView = popularCatProductList3.get(position2);
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(Constant.Product.PARCEL, productView);
                activity.startActivityForResult(intent, 2);

            }
        });
        binding.productCatRecyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.productCatRecyclerView3.setAdapter(popularCatProductAdapter3);


        popularCatProductAdapter4=  new PopularCatProductAdapter( popularCatProductList4,getContext(), R.layout.layout_item_product2,8).addOnClickListener((View, position2) -> {
            ProductModel productView = popularCatProductList4.get(position2);
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(Constant.Product.PARCEL, productView);
                activity.startActivityForResult(intent, 2);

            }
        });
        binding.productCatRecyclerView4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.productCatRecyclerView4.setAdapter(popularCatProductAdapter4);

        popularCatProductPresenter.getHomePopularProduct(value1,value2,value3,value4);



    }

    private void fromOurBlog(int limit) {
       blogsModels = new ArrayList<>();

        binding.blogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        blogsAdapter= new BlogsAdapter(getContext(), blogsModels, R.layout.layout_item_blog);
        binding.blogRecyclerView.setAdapter(blogsAdapter);
        blogListPresenter.BlogDataLoad(limit);

      //  topSellingProduct();

    }

    private void topSellingProduct(int limit) {

        topSellingProList=new ArrayList<>();

        binding.topRecyclerView4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topSellingAdapter= new TopSellingAdapter(getContext(), topSellingProList, R.layout.layout_item_top_sel).addOnClickListener((View, position2) -> {
            TopSellingProModel productView = topSellingProList.get(position2);
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(Constant.Product.PARCEL, productView);
                activity.startActivityForResult(intent, 2);
            }
        });
        binding.topRecyclerView4.setAdapter(topSellingAdapter);

        topSellingProPresenter.topSellingProDataLoad(limit);
    }

    @Override
    public void onHomeSliderDataLoaded(List<SliderItem> sliderItem) {
        sliderItemList.addAll(sliderItem);
        adapter.notifyDataSetChanged();
        categories();
    }

    @Override
    public void onHomeSliderDataStartLoading() {
        loadingDialog.startLoadingAlertDialog();
        binding.shimmerImageSlider.setVisibility(View.VISIBLE);
        binding.imageSlider.setVisibility(View.GONE);
    }

    @Override
    public void onHomeSliderDataStopLoading() {
        //loadingDialog.dismissDialog();
        binding.shimmerImageSlider.setVisibility(View.GONE);
        binding.imageSlider.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHomeSliderDataShowMessage(String errMsg) {
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onHomeCategoryDataLoaded(List<CategoryView> catList) {
        categoryList.addAll(catList);
        categoryAdapter.notifyDataSetChanged();
        popularCategoryProduct(categoryList.get(0).getCatID(),categoryList.get(1).getCatID(),categoryList.get(2).getCatID(),categoryList.get(3).getCatID());
        binding.catNameText1.setText(categoryList.get(0).getCategoryName());
        hotProduct();
    }

    @Override
    public void onHomeCategoryDataStartLoading() {
        //loadingDialog.startLoadingAlertDialog();
        binding.shimmerCategory.setVisibility(View.VISIBLE);
        binding.categoryRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onHomeCategoryDataStopLoading() {
        //loadingDialog.dismissDialog();
        binding.shimmerCategory.setVisibility(View.GONE);
        binding.categoryRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHomeCategoryDataShowMessage(String errMsg) {
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onHotProductDataLoaded(List<ProductModel> sliderItem) {
        hotProductViews.addAll(sliderItem);
        hotDealSliderAdapter.notifyDataSetChanged();

    }

    @Override
    public void onHotProductStartLoading() {
        // loadingDialog.startLoadingAlertDialog();
        binding.shimmerHotProduct.setVisibility(View.VISIBLE);
        binding.hotDealSlider.setVisibility(View.GONE);
    }

    @Override
    public void onHotProductStopLoading() {
        loadingDialog.dismissDialog();
        binding.shimmerHotProduct.setVisibility(View.GONE);
        binding.hotDealSlider.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHotProductShowMessage(String errMsg) {
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onHomePopularProductLoaded(List<ProductModel> productViews) {
        popularProductList.addAll(productViews);
        popularProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHomePopularProductStartLoading() {
        //loadingDialog.startLoadingAlertDialog();
        binding.shimmerProduct.setVisibility(View.VISIBLE);
        binding.productRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onHomePopularProductStopLoading() {
        //loadingDialog.dismissDialog();
        binding.shimmerProduct.setVisibility(View.GONE);
        binding.productRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHomePopularProductShowMessage(String errMsg) {
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onHomePopularCatProductLoaded(List<ProductModel> productViews) {
        for (int i = 0; i < productViews.size(); i++) {
            if (productViews.get(i).getCatId().equals(categoryList.get(0).getCatID())){
                popularCatProductList1.add(productViews.get(i));
            } else if (productViews.get(i).getCatId().equals(categoryList.get(1).getCatID())) {
                popularCatProductList2.add(productViews.get(i));
            } else if (productViews.get(i).getCatId().equals(categoryList.get(2).getCatID())) {
                popularCatProductList3.add(productViews.get(i));
            } else if (productViews.get(i).getCatId().equals(categoryList.get(3).getCatID())) {
                popularCatProductList4.add(productViews.get(i));
            }

        }
        popularCatProductAdapter1.notifyDataSetChanged();
        popularCatProductAdapter2.notifyDataSetChanged();
        popularCatProductAdapter3.notifyDataSetChanged();
        popularCatProductAdapter4.notifyDataSetChanged();

    }

    @Override
    public void onHomePopularCatProductStartLoading() {
        //loadingDialog.startLoadingAlertDialog();
        binding.shimmerGroceryProduct.setVisibility(View.VISIBLE);
        binding.productGroceryRecyclerView.setVisibility(View.GONE);
        binding.shimmerCatProduct2.setVisibility(View.VISIBLE);
        binding.productCatRecyclerView2.setVisibility(View.GONE);
        binding.shimmerCatProduct3.setVisibility(View.VISIBLE);
        binding.productCatRecyclerView3.setVisibility(View.GONE);
        binding.shimmerCatProduct4.setVisibility(View.VISIBLE);
        binding.productCatRecyclerView4.setVisibility(View.GONE);

    }

    @Override
    public void onHomePopularCatProductStopLoading() {
        //loadingDialog.dismissDialog();
        binding.shimmerGroceryProduct.setVisibility(View.GONE);
        binding.productGroceryRecyclerView.setVisibility(View.VISIBLE);
        binding.shimmerCatProduct2.setVisibility(View.GONE);
        binding.productCatRecyclerView2.setVisibility(View.VISIBLE);
        binding.shimmerCatProduct3.setVisibility(View.GONE);
        binding.productCatRecyclerView3.setVisibility(View.VISIBLE);
        binding.shimmerCatProduct4.setVisibility(View.GONE);
        binding.productCatRecyclerView4.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHomePopularCatProductShowMessage(String errMsg) {
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }



    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
     /*   for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();*/
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBlogListDataLoad(List<BlogsModel> blogs) {
        blogsModels.addAll(blogs);
        blogsAdapter.notifyDataSetChanged();


    }

    @Override
    public void onBlogListStartLoading() {
        loadingDialog.startLoadingAlertDialog();
        binding.shimmerBlog.setVisibility(View.VISIBLE);
        binding.blogRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onBlogListStopLoading() {
        loadingDialog.dismissDialog();
        binding.shimmerBlog.setVisibility(View.GONE);
        binding.blogRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBlogListShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onTopSellingProDataLoad(List<TopSellingProModel> topSellingProModel) {
        if (!topSellingProModel.isEmpty()){
            binding.shimmertopSel.setVisibility(View.GONE);
            binding.topRecyclerView4.setVisibility(View.VISIBLE);
        }
        topSellingProList.addAll(topSellingProModel);
        topSellingAdapter.notifyDataSetChanged();
        ((MainActivity) requireContext()).getCartItems();
    }

    @Override
    public void onTopSellingProStartLoading() {
        binding.shimmertopSel.setVisibility(View.VISIBLE);
        binding.topRecyclerView4.setVisibility(View.GONE);
    }

    @Override
    public void onTopSellingProStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onTopSellingProShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }

    class MyThread extends Thread {
        public void run() {
         // topSellingProduct();
        }
    }



}//worldmission3     123456