package co.wm21.https.view.fragments;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.example.HomeDetailsResponse;
import com.example.example.SlideImage;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.FHelper.networks.Models.TopSellingProModel;
import co.wm21.https.R;
import co.wm21.https.presenter.HomeDetailsPresenter;
import co.wm21.https.presenter.interfaces.OnHomeDetailsView;
import co.wm21.https.view.activities.HomeMoreProductActivity;
import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.view.activities.ProductDetailsActivity;
import co.wm21.https.view.adapters.BlogsAdapter;
import co.wm21.https.view.adapters.HomeShopsItemAdapter;
import co.wm21.https.view.adapters.HotDealSliderAdapter;
import co.wm21.https.view.adapters.PopularCatProductAdapter;
import co.wm21.https.view.adapters.PopularProductAdapter;
import co.wm21.https.view.adapters.SliderAdapter;
import co.wm21.https.view.adapters.TopSellingAdapter;
import co.wm21.https.view.adapters.category.CategoryAdapter;
import co.wm21.https.FHelper.networks.Models.home.CategoryView;
import co.wm21.https.view.adapters.item_menu.ItemMenuView;
import co.wm21.https.databinding.FragmentMainHomeBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.activities.mlm.company.company_fragments.AboutUsFragment;
import co.wm21.https.view.activities.mlm.company.company_fragments.ContactUsFragment;
import co.wm21.https.view.fragments.manageOrder.AppliedFragment;
import co.wm21.https.view.fragments.manageOrder.DeliveredFragment;
import co.wm21.https.view.fragments.manageOrder.OrderFragment;
import co.wm21.https.view.fragments.manageOrder.ReceivedFragment;
import co.wm21.https.view.fragments.products.CategoryProductFragment;
import co.wm21.https.utils.Constant;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnBlogListView;
import co.wm21.https.presenter.interfaces.OnHomeCategoryView;
import co.wm21.https.presenter.interfaces.OnHomePopularCatProductView;
import co.wm21.https.presenter.interfaces.OnHomePopularProductView;
import co.wm21.https.presenter.interfaces.OnHotProductView;
import co.wm21.https.presenter.interfaces.OnTopSellingProView;
import co.wm21.https.presenter.BlogListPresenter;
import co.wm21.https.presenter.HomeCategoryPresenter;
import co.wm21.https.presenter.HomePopularCatProductPresenter;
import co.wm21.https.presenter.HomePopularProductPresenter;
import co.wm21.https.presenter.HotProductPresenter;
import co.wm21.https.presenter.TopSellingProPresenter;

public class HomeFragment extends Fragment implements OnHomeDetailsView, OnTopSellingProView {

    List<SlideImage> sliderItemList;

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
    ArrayList<ProductModel> hotProductViews;


    HotDealSliderAdapter hotDealSliderAdapter;
    PopularCatProductAdapter popularCatProductAdapter1;
    PopularCatProductAdapter popularCatProductAdapter2;
    PopularCatProductAdapter popularCatProductAdapter3;
    PopularCatProductAdapter popularCatProductAdapter4;
    LoadingDialog loadingDialog;
    TopSellingProPresenter topSellingProPresenter;
    HomeDetailsPresenter homeDetailsPresenter;

    ArrayList<ProductModel> popularProductList;
    ArrayList<ProductModel> bestSellerProductList;
    ArrayList<ProductModel> mostPopularProductList;
    ArrayList<ProductModel> featuredProductList;
    ArrayList<ProductModel> newArrivalProductList;
    PopularProductAdapter popularProductAdapter;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                new ItemMenuView("VENDOR", "#ffbfbf")
        ));

        binding.shopsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.shopsRecyclerView.setAdapter(new HomeShopsItemAdapter(getContext(), shopList, R.layout.layout_item_digital_shop_service).addOnClickListener((View, position) -> {
         //every fragment is in ShopsActivity
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

        topSellingProPresenter=new TopSellingProPresenter(this);




        homeDetailsPresenter=new HomeDetailsPresenter(this);
        topSliderImage();

        topSellingProduct();
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
        categories();



        homeDetailsPresenter.onHomeDetailsResponseData();


    }

    @SuppressLint("NonConstantResourceId")
    private void moreProducts(View view) {
        Bundle bundle = new Bundle();
        Intent intent=null;
        switch (view.getId()) {
            case R.id.moreGroceryProduct:
                 intent=new Intent(getContext(), HomeMoreProductActivity.class);
                intent.putExtra(ConstantValues.ARGUMENT1, categoryList.get(0).catID);
                intent.putExtra(ConstantValues.ARGUMENT2, categoryList.get(0).categoryName);
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(requireContext());
                break;
            case R.id.moreElectronicsProduct:
                 intent=new Intent(getContext(), HomeMoreProductActivity.class);
                intent.putExtra(ConstantValues.ARGUMENT1, categoryList.get(1).catID);
                intent.putExtra(ConstantValues.ARGUMENT2, categoryList.get(1).categoryName);
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(requireContext());
                break;
            case R.id.beautyMoreProduct:
                intent=new Intent(getContext(), HomeMoreProductActivity.class);
                intent.putExtra(ConstantValues.ARGUMENT1, categoryList.get(2).catID);
                intent.putExtra(ConstantValues.ARGUMENT2, categoryList.get(2).categoryName);
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(requireContext());
                break;
            case R.id.fashionMoreProduct:
                intent=new Intent(getContext(), HomeMoreProductActivity.class);
                intent.putExtra(ConstantValues.ARGUMENT1, categoryList.get(3).catID);
                intent.putExtra(ConstantValues.ARGUMENT2, categoryList.get(3).categoryName);
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(requireContext());
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

            String cat_id = categoryList.get(position).catID;
            String name = categoryList.get(position).categoryName;
            Bundle bundle = new Bundle();
            bundle.putString(co.wm21.https.FHelper.ConstantValues.ARGUMENT1, cat_id);
            bundle.putString(co.wm21.https.FHelper.ConstantValues.NAME, name);
            CategoryProductFragment categoryProductFragment = new CategoryProductFragment();
            categoryProductFragment.setArguments(bundle);
            //((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.main_home_contain, subSubCategoryFragment, "SubCategoryFragment").addToBackStack("SubCategory").commit();
            ((MainActivity) requireActivity()).switchFragment(categoryProductFragment, "CategoryProductFragment");

        });
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoryRecyclerView.setAdapter(categoryAdapter);


        hotProduct();

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


    @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
    private void clickForFeatured(View view) {

        switch (view.getId()) {
            case R.id.bestSellerBtn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.bestSellerBtn.setBackgroundColor(getResources().getColor(R.color.white, null));
                }
                binding.bestSellerTxt.setTextColor(Color.parseColor("#FE0000"));
                binding.mostPopularTxt.setTextColor(Color.parseColor("#000000"));
                binding.featuredTxt.setTextColor(Color.parseColor("#000000"));
                binding.newArrivalTxt.setTextColor(Color.parseColor("#000000"));
                binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
                binding.mostPopularBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.newArrivalBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.featuredBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));

                popularProductList.clear();
                popularProductList.addAll(bestSellerProductList);
                popularProductAdapter.notifyDataSetChanged();

                //popularProduct("2");
                break;
            case R.id.mostPopularBtn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.mostPopularBtn.setBackgroundColor(getResources().getColor(R.color.white, null));
                }
                binding.mostPopularTxt.setTextColor(Color.parseColor("#FE0000"));
                binding.bestSellerTxt.setTextColor(Color.parseColor("#000000"));
                binding.featuredTxt.setTextColor(Color.parseColor("#000000"));
                binding.newArrivalTxt.setTextColor(Color.parseColor("#000000"));

                binding.mostPopularBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
                binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.newArrivalBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.featuredBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));

                popularProductList.clear();
                popularProductList.addAll(mostPopularProductList);
                popularProductAdapter.notifyDataSetChanged();
                //popularProduct("3");
                break;
            case R.id.featuredBtn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.featuredBtn.setBackgroundColor(getResources().getColor(R.color.white, null));
                }
                binding.featuredTxt.setTextColor(Color.parseColor("#FE0000"));
                binding.bestSellerTxt.setTextColor(Color.parseColor("#000000"));
                binding.mostPopularTxt.setTextColor(Color.parseColor("#000000"));
                binding.newArrivalTxt.setTextColor(Color.parseColor("#000000"));

                binding.featuredBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
                binding.mostPopularBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.newArrivalBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));

                popularProductList.clear();
                popularProductList.addAll(featuredProductList);
                popularProductAdapter.notifyDataSetChanged();
                //popularProduct("4");
                break;
            case R.id.newArrivalBtn:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.newArrivalBtn.setBackgroundColor(getResources().getColor(R.color.white, null));
                }
                binding.newArrivalTxt.setTextColor(Color.parseColor("#FE0000"));
                binding.bestSellerTxt.setTextColor(Color.parseColor("#000000"));
                binding.mostPopularTxt.setTextColor(Color.parseColor("#000000"));
                binding.featuredTxt.setTextColor(Color.parseColor("#000000"));

                binding.newArrivalBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_selected_btn));
                binding.mostPopularBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.featuredBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                binding.bestSellerBtn.setBackground(getResources().getDrawable(R.drawable.bg_home_page_normal_btn));
                popularProductList.clear();
                popularProductList.addAll(newArrivalProductList);
                popularProductAdapter.notifyDataSetChanged();
                //popularProduct("1");
                break;
        }

    }

    private void popularProduct() {
        popularProductList = new ArrayList<>();
        bestSellerProductList = new ArrayList<>();
        mostPopularProductList = new ArrayList<>();
        featuredProductList = new ArrayList<>();
        newArrivalProductList = new ArrayList<>();
        popularProductAdapter=new PopularProductAdapter(popularProductList,requireContext()).addOnClickListener((View, position2) -> {
            ProductModel productView = popularProductList.get(position2);
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(Constant.Product.PARCEL, productView);
                activity.startActivityForResult(intent, 2);
            }
        });
        binding.productRecyclerView.setAdapter(popularProductAdapter);
       // homePopularProductPresenter.getHomePopularProduct(value);



    }

    private void hotProduct() {
        hotProductViews = new ArrayList<>();
        binding.hotDealSlider.setScrollTimeInSec(5);
        binding.hotDealSlider.startAutoCycle();
        hotDealSliderAdapter=new HotDealSliderAdapter(requireContext(), hotProductViews, R.layout.layout_item_product_hot_deal);

        binding.hotDealSlider.setSliderAdapter(hotDealSliderAdapter);
       // hotProductPresenter.getHotProduct(30);


        fromOurBlog();




    }
    private void popularCategoryProduct(String value1,String value2,String value3,String value4) {

        binding.catNameText1.setText(categoryList.get(0).categoryName);
        binding.catNameText2.setText(categoryList.get(1).categoryName);
        binding.catNameText3.setText(categoryList.get(2).categoryName);
        binding.catNameText4.setText(categoryList.get(3).categoryName);

        popularCatProductList1 = new ArrayList<>();
        popularCatProductList2 = new ArrayList<>();
        popularCatProductList3 = new ArrayList<>();
        popularCatProductList4 = new ArrayList<>();

        popularCatProductAdapter1=new PopularCatProductAdapter( popularCatProductList1,requireContext(), R.layout.layout_item_product2,8).addOnClickListener((View, position2) -> {
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

        popularCatProductAdapter2=   new PopularCatProductAdapter( popularCatProductList2,requireContext(), R.layout.layout_item_product2,8).addOnClickListener((View, position2) -> {
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


        popularCatProductAdapter3=   new PopularCatProductAdapter(popularCatProductList3, requireContext(),  R.layout.layout_item_product2,8).addOnClickListener((View, position2) -> {
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

       // popularCatProductPresenter.getHomePopularProduct(value1,value2,value3,value4);



    }

    private void fromOurBlog() {
       blogsModels = new ArrayList<>();

        binding.blogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        blogsAdapter= new BlogsAdapter(getContext(), blogsModels, R.layout.layout_item_blog);
        binding.blogRecyclerView.setAdapter(blogsAdapter);

    }

    private void topSellingProduct() {

        topSellingProList=new ArrayList<>();

        binding.topRecyclerView4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topSellingAdapter= new TopSellingAdapter(requireContext(), topSellingProList, R.layout.layout_item_top_sel).addOnClickListener((View, position2) -> {
            TopSellingProModel productView = topSellingProList.get(position2);
            Activity activity = getActivity();
            if (activity != null) {
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(Constant.Product.PARCEL, productView);
                activity.startActivityForResult(intent, 2);
            }
        });
        binding.topRecyclerView4.setAdapter(topSellingAdapter);

        topSellingProPresenter.topSellingProDataLoad(8);
    }



    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();

        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
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
      //  loadingDialog.dismissDialog();
    }

    @Override
    public void onTopSellingProShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onHomeDetailsLoadSuccess(@Nullable HomeDetailsResponse response) {


        sliderItemList.addAll(response.getData().getSlideImage());
        if (!sliderItemList.isEmpty()){
            binding.shimmerImageSlider.setVisibility(View.GONE);
            binding.imageSlider.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();


        categoryList.addAll(response.getData().getCategory());
        categoryAdapter.notifyDataSetChanged();


        hotProductViews.addAll(response.getData().getHotProducts());
        hotDealSliderAdapter.notifyDataSetChanged();
        binding.shimmerHotProduct.setVisibility(View.GONE);
        binding.hotDealSlider.setVisibility(View.VISIBLE);


        popularProduct();
        //popularProductList.addAll(response.getData().getPopularProduct());

        bestSellerProductList.addAll(response.getData().getPopularProduct().subList(0, 12));
        mostPopularProductList.addAll(response.getData().getPopularProduct().subList(13, 24));
        featuredProductList.addAll(response.getData().getPopularProduct().subList(25, 36));
        newArrivalProductList.addAll(response.getData().getPopularProduct().subList(37, 48));

        popularProductList.clear();
        popularProductList.addAll(bestSellerProductList);
        popularProductAdapter.notifyDataSetChanged();
        binding.shimmerProduct.setVisibility(View.GONE);
        binding.productRecyclerView.setVisibility(View.VISIBLE);


        popularCategoryProduct(categoryList.get(0).catID, categoryList.get(1).catID, categoryList.get(2).catID, categoryList.get(3).catID);
        binding.catNameText1.setText(categoryList.get(0).categoryName);


        for (int i = 0; i < response.getData().getPopularCategoryProduct().size(); i++) {
            if (response.getData().getPopularCategoryProduct().get(i).getCatId().equals(categoryList.get(0).catID)){
                popularCatProductList1.add(response.getData().getPopularCategoryProduct().get(i));
                if(popularCatProductAdapter1.getSize()>0){
                    binding.shimmerGroceryProduct.setVisibility(View.GONE);
                    binding.productGroceryRecyclerView.setVisibility(View.VISIBLE);
                }
            } else if (response.getData().getPopularCategoryProduct().get(i).getCatId().equals(categoryList.get(1).catID)) {
                popularCatProductList2.add(response.getData().getPopularCategoryProduct().get(i));
                if(!popularCatProductList2.isEmpty()){
                    binding.shimmerCatProduct2.setVisibility(View.GONE);
                    binding.productCatRecyclerView2.setVisibility(View.VISIBLE);
                }
            } else if (response.getData().getPopularCategoryProduct().get(i).getCatId().equals(categoryList.get(2).catID)) {
                popularCatProductList3.add(response.getData().getPopularCategoryProduct().get(i));
                if(!popularCatProductList3.isEmpty()){
                    binding.shimmerCatProduct3.setVisibility(View.GONE);
                    binding.productCatRecyclerView3.setVisibility(View.VISIBLE);
                }
            } else if (response.getData().getPopularCategoryProduct().get(i).getCatId().equals(categoryList.get(3).catID)) {
                popularCatProductList4.add(response.getData().getPopularCategoryProduct().get(i));
                if(!popularCatProductList4.isEmpty()){
                    binding.shimmerCatProduct4.setVisibility(View.GONE);
                    binding.productCatRecyclerView4.setVisibility(View.VISIBLE);
                }
            }

        }
        popularCatProductAdapter1.notifyDataSetChanged();
        popularCatProductAdapter2.notifyDataSetChanged();
        popularCatProductAdapter3.notifyDataSetChanged();
        popularCatProductAdapter4.notifyDataSetChanged();



        blogsModels.addAll(response.getData().getAllBlogs());
        blogsAdapter.notifyDataSetChanged();
        binding.shimmerBlog.setVisibility(View.GONE);
        binding.blogRecyclerView.setVisibility(View.VISIBLE);


        loadingDialog.dismissDialog();

    }

    @Override
    public void onStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onError(@Nullable String errmsg) {

    }


}//wm20886455     @123456