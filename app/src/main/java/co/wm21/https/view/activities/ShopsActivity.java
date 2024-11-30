package co.wm21.https.view.activities;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.R;
import co.wm21.https.SliderItem;
import co.wm21.https.view.adapters.ShopsAdapter;
import co.wm21.https.view.adapters.SliderAdapter;
import co.wm21.https.view.adapters.product.ProductAdapter;
import co.wm21.https.databinding.ActivityShopsBinding;
import co.wm21.https.databinding.FragmentBrandShopBinding;
import co.wm21.https.databinding.FragmentMissionBazarBinding;
import co.wm21.https.databinding.FragmentPremierShopBinding;
import co.wm21.https.databinding.FragmentShowroomBinding;
import co.wm21.https.databinding.FragmentTeleShopBinding;
import co.wm21.https.databinding.FragmentVendorBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.view.fragments.CartFragment;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnCartItemListView;
import co.wm21.https.presenter.interfaces.OnHomeTopSliderImageView;
import co.wm21.https.presenter.interfaces.OnTeleShopProductView;
import co.wm21.https.model.ShopsModel;
import co.wm21.https.presenter.CartItemListPresenter;
import co.wm21.https.presenter.HomeTopSliderImagePresenter;
import co.wm21.https.presenter.TeleShopProductPresenter;

public class ShopsActivity extends AppCompatActivity implements OnCartItemListView {
    ActivityShopsBinding binding;

    static User user;


    public static String scannerText = "";
    Fragment currentFragment;
    int shopPos = 1111;
    public Menu TopMenu;
    public MenuItem notificationItem, signOutItem;
    CartItemListPresenter cartItemListPresenter;
    public static BadgeDrawable badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding=ActivityShopsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        switchFragment(new TeleShop(),"TeleShop");
        // binding.eShop1.setChecked(true);
        user = new User(getApplicationContext());
        // productList = new ArrayList<>();
        setSupportActionBar(binding.appBarLayout.mainToolbar);
        Bundle thisBundle = getIntent().getExtras();
        binding.appBarLayout.backBtn.setOnClickListener(this::backBtnClick);
        shopPos = thisBundle != null ? thisBundle.getInt(ConstantValues.ARGUMENT1) : 1111;
        cartItemListPresenter=new CartItemListPresenter(this);

        if (shopPos != 1111) {
            switch (shopPos) {
                case 0:
                    switchFragment(new TeleShop(), "TeleShop");
                    // binding.eShop1.setChecked(true);
                    break;
                case 1:
                    switchFragment(new Showroom(), "Showroom");
                    //  binding.eShop2.setChecked(true);
                    break;
                case 2:
                    switchFragment(new MissionBazar(), "MissionBazar");
                    // binding.eShop3.setChecked(true);
                    break;
                case 3:
                    switchFragment(new BrandShop(), "BrandShop");
                    //  binding.eShop4.setChecked(true);
                    break;
                case 4:
                    switchFragment(new PremierShop(), "PremierShop");
                    //binding.eShop5.setChecked(true);
                    break;
                case 5:
                    switchFragment(new Vendor(), "Vendor");
                    //binding.eShop6.setChecked(true);
                    break;
                default:
            }
        }


    }

    private void backBtnClick(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        @MenuRes int menuId;
        menuId = R.menu.toolbar_menu;
        getMenuInflater().inflate(menuId, TopMenu = menu);
        notificationItem = TopMenu.getItem(0);
        signOutItem = TopMenu.getItem(1);
        if (!user.getSession().isLoggedIn())
            signOutItem.setVisible(false);
        else
            signOutItem.setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }
    @SuppressLint({"NonConstantResourceId", "RtlHardcoded"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_sign_out:
                user.getSession().logoutUser();
                finishAffinity();
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.menu_cart:
                getCartItems();
                switchFragment(new CartFragment(), "CartFragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @SuppressLint("UnsafeOptInUsageError")
    @Override
    protected void onResume() {
        super.onResume();
        badge = BadgeDrawable.create(this);
        BadgeUtils.attachBadgeDrawable(badge, binding.appBarLayout.mainToolbar, R.id.menu_cart);
        badge.setBackgroundColor(Color.parseColor("#f6ac00"));
        badge.setVisible(true);
        badge.clearNumber();
        if (ProductDetailsActivity.totalSelected2 == 0) {
            badge.clearNumber();
            getCartItems();
        } else {
            badge.setNumber(ProductDetailsActivity.totalSelected2);
        }
    }
    @SuppressLint("UnsafeOptInUsageError")
    public void getCartItems() {
        badge = BadgeDrawable.create(this);
        BadgeUtils.attachBadgeDrawable(badge, binding.appBarLayout.mainToolbar, R.id.menu_cart);
        badge.setBackgroundColor(Color.parseColor("#f6ac00"));
        badge.setVisible(true);
        badge.clearNumber();
        String uId;
        @SuppressLint("HardwareIds")
        String val = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        uId = val.replaceAll("[\\D]", "");

        cartItemListPresenter.CartItemDataLoad(uId);

    }

    @Override
    public void onCartItemListDataLoad(CartItemsHead cartItems) {
        int totalSelected2=cartItems.getData().size();
        if (totalSelected2 == 0) {
            badge.clearNumber();
        } else {
            badge.setNumber(totalSelected2);
        }
    }

    @Override
    public void onCartItemListStartLoading() {

    }

    @Override
    public void onCartItemListStopLoading() {

    }

    @Override
    public void onCartItemListShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }


    public static class TeleShop extends Fragment implements OnHomeTopSliderImageView,OnTeleShopProductView {
        FragmentTeleShopBinding binding;
        ArrayList<ProductModel> productList;
        co.wm21.https.FHelper.API api;
        ShopsAdapter adapter;
        List<SliderItem> sliderTelrItemList;
        SliderAdapter sliderAdapter;
        ProductAdapter productAdapter;
        //MaterialDialog dialog;
        HomeTopSliderImagePresenter homeTopSliderImagePresenter;
        ArrayList<ProductModel>teleShopProductList;
        TeleShopProductPresenter teleShopProductPresenter;
        LoadingDialog loadingDialog;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            binding = FragmentTeleShopBinding.inflate(inflater, container, false);
            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);


            api = ConstantValues.getAPI();


            api=ConstantValues.getAPI();

            loadingDialog=new LoadingDialog(getActivity());



            String[] allShops = {"Tele Shop", "Showroom", "Mission Bazar", "Premier Shop", "BrandShop", "Vendor"};
            binding.shopsView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, allShops));
            binding.shopsView.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    boolean checked = false;
                    for (int j = 0; j < allShops.length; j++) {
                        if (charSequence.toString().equals(allShops[i]))
                            checked = true;
                    }
                    if (!checked) {
                        charSequence = "";
                    }
                }

                @Override
                public void onTextChanged(CharSequence shopName, int i, int i1, int i2) {
                    switch (shopName.toString()) {
                        case "Tele Shop":
                            switchFragment(new TeleShop(), "TeleShop");
                            // binding.eShop1.setChecked(true);
                            break;
                        case "Showroom":
                            switchFragment(new Showroom(), "Showroom");
                            //  binding.eShop2.setChecked(true);
                            break;
                        case "Mission Bazar":
                            switchFragment(new MissionBazar(), "MissionBazar");
                            // binding.eShop3.setChecked(true);
                            break;
                        case "BrandShop":
                            switchFragment(new BrandShop(), "BrandShop");
                            //  binding.eShop4.setChecked(true);
                            break;
                        case "Premier Shop":
                            switchFragment(new PremierShop(), "PremierShop");
                            //binding.eShop5.setChecked(true);
                            break;
                        case "Vendor":
                            switchFragment(new Vendor(), "Vendor");
                            //binding.eShop6.setChecked(true);
                            break;
                        default:
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            homeTopSliderImagePresenter=new HomeTopSliderImagePresenter(this);

            teleShopProductPresenter=new TeleShopProductPresenter(this);
            topSliderImage();


        }
        private void topSliderImage() {
            sliderTelrItemList = new ArrayList<>();
            sliderAdapter = new SliderAdapter(getContext(),sliderTelrItemList);
            //adapter.renewItems(sliderItemList);
            binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            binding.imageSlider.startAutoCycle();
            binding.imageSlider.setSliderAdapter(sliderAdapter);

            parseData("10");
        }
        private void parseData(String s) {
            homeTopSliderImagePresenter.getSliderImageDataResponse(s);
        }



        private void getTeleShopsProducts() {
            teleShopProductList=new ArrayList<>();
            productAdapter=  new ProductAdapter(getContext(), teleShopProductList).addOnClickListener((View, position) -> {
               // ProductModel productView = productList.get(position);

                startActivity(new Intent(getContext(), ProductDetailsActivity.class)
                        .putExtra(Constant.Product.PARCEL, teleShopProductList.get(position)));
            });
            binding.productTelRecyclerView.setAdapter(productAdapter);

            teleShopProductPresenter.getTeleShopProduct("1");


        }
        public void switchFragment(Fragment fragment, String tag) {
            FragmentManager fm = getParentFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
                fm.popBackStack();
            FragmentTransaction childFragTrans = fm.beginTransaction();
            childFragTrans.replace(R.id.child_fragment_container, fragment, tag);
            childFragTrans.addToBackStack(tag);
            childFragTrans.commit();
        }

        @Override
        public void onHomeSliderDataLoaded(List<SliderItem> sliderItem) {
            sliderTelrItemList.addAll(sliderItem);
            sliderAdapter.notifyDataSetChanged();

        }

        @Override
        public void onHomeSliderDataStartLoading() {
            loadingDialog.startLoadingAlertDialog();
            binding.shimmerImageSlider.setVisibility(View.VISIBLE);
            binding.imageSlider.setVisibility(View.GONE);
            getTeleShopsProducts();
        }

        @Override
        public void onHomeSliderDataStopLoading() {

            binding.shimmerImageSlider.setVisibility(View.GONE);
            binding.imageSlider.setVisibility(View.VISIBLE);
        }

        @Override
        public void onHomeSliderDataShowMessage(String errMsg) {
            Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onTeleShopProductDataLoaded(List<ProductModel> teleShopProducts) {
            binding.productTelRecyclerView.setVisibility(View.VISIBLE);
            teleShopProductList.addAll(teleShopProducts);
            productAdapter.notifyDataSetChanged();
        }

        @Override
        public void onTeleShopProductStartLoading() {
            binding.productTelRecyclerView.setVisibility(View.GONE);
            binding.shimmerProduct.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTeleShopProductStopLoading() {

            binding.shimmerProduct.setVisibility(View.GONE);
            binding.productTelRecyclerView.setVisibility(View.VISIBLE);
            loadingDialog.dismissDialog();
        }

        @Override
        public void onTeleShopProductShowMessage(String errMsg) {
            Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
        }


    }

    public static class Showroom extends Fragment {
        FragmentShowroomBinding binding;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            binding = FragmentShowroomBinding.inflate(inflater, container, false);

            return binding.getRoot();
        }
    }

    public static class MissionBazar extends Fragment {
        FragmentMissionBazarBinding binding;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            binding = FragmentMissionBazarBinding.inflate(inflater, container, false);

            return binding.getRoot();
        }
    }

    public static class BrandShop extends Fragment {
        FragmentBrandShopBinding binding;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            binding = FragmentBrandShopBinding.inflate(inflater, container, false);

            return binding.getRoot();
        }
    }

    public static class PremierShop extends Fragment {
        FragmentPremierShopBinding binding;
        co.wm21.https.FHelper.API api;
        ShopsAdapter adapter;
        List<SliderItem> sliderItemList;
        SliderAdapter sliderAdapter;




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            binding = FragmentPremierShopBinding.inflate(inflater, container, false);

            api=ConstantValues.getAPI();
            sliderItemList = new ArrayList<>();
            sliderAdapter = new SliderAdapter(getContext(),sliderItemList);
            co.wm21.https.FHelper.API api = co.wm21.https.FHelper.ConstantValues.getAPI();
            binding.shimmerImageSlider.setVisibility(View.VISIBLE);
            binding.imageSlider.setVisibility(View.GONE);
            MySingleton.getInstance(getContext()).addToRequestQueue(api.slide(response -> {
                try {
                    JSONArray array = response.getJSONArray("slides");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject json = array.getJSONObject(i);
                        json.getString(Constant.Slide.INFO);
                        sliderItemList.add(i,
                                new SliderItem(
                                        json.getString(Constant.Slide.IMAGE),
                                        json.getString(Constant.Slide.INFO)
                                ));

                    }
                    sliderAdapter.renewItems(sliderItemList);
                    binding.imageSlider.setSliderAdapter(sliderAdapter);
                    binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                    binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    binding.imageSlider.startAutoCycle();
                    binding.shimmerImageSlider.setVisibility(View.GONE);
                    binding.imageSlider.setVisibility(View.VISIBLE);
                    getAllShops();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }));

            String[] allShops = {"Tele Shop", "Showroom", "Mission Bazar", "Premier Shop", "BrandShop", "Vendor"};
            binding.shopsView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, allShops));
            binding.shopsView.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    boolean checked = false;
                    for (int j = 0; j < allShops.length; j++) {
                        if (charSequence.toString().equals(allShops[i]))
                            checked = true;
                    }
                    if (!checked) {
                        charSequence = "";
                    }
                }

                @Override
                public void onTextChanged(CharSequence shopName, int i, int i1, int i2) {
                    switch (shopName.toString()) {
                        case "Tele Shop":
                            switchFragment(new TeleShop(), "TeleShop");
                            // binding.eShop1.setChecked(true);
                            break;
                        case "Showroom":
                            switchFragment(new Showroom(), "Showroom");
                            //  binding.eShop2.setChecked(true);
                            break;
                        case "Mission Bazar":
                            switchFragment(new MissionBazar(), "MissionBazar");
                            // binding.eShop3.setChecked(true);
                            break;
                        case "BrandShop":
                            switchFragment(new BrandShop(), "BrandShop");
                            //  binding.eShop4.setChecked(true);
                            break;
                        case "Premier Shop":
                            switchFragment(new PremierShop(), "PremierShop");
                            //binding.eShop5.setChecked(true);
                            break;
                        case "Vendor":
                            switchFragment(new Vendor(), "Vendor");
                            //binding.eShop6.setChecked(true);
                            break;
                        default:
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            binding.searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            binding.scanSearch.setOnClickListener(view -> {
                startActivity(new Intent(getContext(), ShopSearchScanActivity.class));
            });

            binding.searchExpandableLayout.collapse();
            binding.searchExpandButton.setOnClickListener(v -> {
                if (binding.searchExpandableLayout.isExpanded()) {
                    binding.searchExpandableLayout.collapse();
                    binding.searchExpandableLayout.setDuration(450);
                    binding.shopTxt.setVisibility(View.VISIBLE);
                } else {
                    binding.searchExpandableLayout.expand();
                    binding.shopTxt.setVisibility(View.GONE);
                }
            });



            return binding.getRoot();
        }
        private void getAllShops() {
            ArrayList<ShopsModel> shopList=new ArrayList<>();
            MySingleton.getInstance(getContext()).addToRequestQueue(api.premiumShop(5,response -> {
                try {
                    JSONArray array=response.getJSONArray("shops");
                    for (int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        shopList.add(new ShopsModel(
                                object.getString(ConstantValues.admin_login.SHOP_ID),
                                object.getString(ConstantValues.admin_login.SHOP_TYPE),
                                object.getString(ConstantValues.admin_login.IMAGE),
                                object.getString(ConstantValues.admin_login.SHOP_NAME),
                                object.getString(ConstantValues.admin_login.MOBILE),
                                object.getString(ConstantValues.admin_login.DISTRICT),
                                object.getString(ConstantValues.admin_login.THANA),
                                object.getString(ConstantValues.admin_login.UNION),
                                object.getString(ConstantValues.admin_login.PHOTO),
                                object.getInt(ConstantValues.admin_login.TYPE_ID)

                        ));
                    }
                    binding.recycleView.setVisibility(View.VISIBLE);
                    binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    adapter = new ShopsAdapter(shopList,getContext(),R.layout.layout_item_premium_shop);
                    if (!shopList.isEmpty()) {
                        binding.recycleView.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));


        }

        @Override
        public void onResume() {
            super.onResume();
            if (!scannerText.equals("")){
                Snackbar.make(((MainActivity)requireContext()).binding.fragmentContainer,scannerText,Snackbar.LENGTH_SHORT).show();
            }
        }

        public void switchFragment(Fragment fragment, String tag) {
            FragmentManager fm = getParentFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
                fm.popBackStack();
            FragmentTransaction childFragTrans = fm.beginTransaction();
            childFragTrans.replace(R.id.child_fragment_container, fragment, tag);
            childFragTrans.addToBackStack(tag);
            childFragTrans.commit();
        }
    }

    public static class Vendor extends Fragment {
        FragmentVendorBinding binding;
        co.wm21.https.FHelper.API api;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = FragmentVendorBinding.inflate(inflater, container, false);
            api=ConstantValues.getAPI();
            return binding.getRoot();
        }


    }


    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        FragmentTransaction childFragTrans = fm.beginTransaction();
        childFragTrans.replace(R.id.child_fragment_container, fragment, tag);
        childFragTrans.addToBackStack(tag);
        childFragTrans.commit();
    }
}