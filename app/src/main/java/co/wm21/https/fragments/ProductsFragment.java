package co.wm21.https.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.SliderItem;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.adapters.ShopsAdapter;
import co.wm21.https.adapters.SliderAdapter;
import co.wm21.https.adapters.product.ProductAdapter;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.databinding.FragmentProductsBinding;
import co.wm21.https.activities.mlm.company.company_fragments.AboutUsFragment;
import co.wm21.https.activities.mlm.company.company_fragments.ContactUsFragment;
import co.wm21.https.fragments.shops.BrandShop;
import co.wm21.https.fragments.shops.MissionBazar;
import co.wm21.https.fragments.shops.PremierShop;
import co.wm21.https.fragments.shops.Showroom;
import co.wm21.https.fragments.shops.TeleShop;
import co.wm21.https.fragments.shops.Vendor;
import co.wm21.https.helpers.Constant;

public class ProductsFragment extends Fragment {
    FragmentProductsBinding binding;
    ArrayList<ProductView> productList;
    co.wm21.https.FHelper.API api;
    ShopsAdapter adapter;
    List<SliderItem> sliderItemList;
    SliderAdapter sliderAdapter;
    ProductAdapter productAdapter;
    boolean isGrid = false;
    String catId = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            catId = bundle.getString(ConstantValues.ARGUMENT1);
        }

        api = ConstantValues.getAPI();
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
                    sliderItemList.add(i, new SliderItem(json.getString(Constant.Slide.IMAGE), json.getString(Constant.Slide.INFO)));
                }
                sliderAdapter.renewItems(sliderItemList);
                binding.imageSlider.setSliderAdapter(sliderAdapter);
                binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                binding.imageSlider.startAutoCycle();
                binding.shimmerImageSlider.setVisibility(View.GONE);
                binding.imageSlider.setVisibility(View.VISIBLE);
                if (!catId.equals("")) getProducts();
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
        binding.footerId.aboutUs.setOnClickListener(v -> {
            switchFragment(new AboutUsFragment(), "AboutUsFragment");
        });
        binding.footerId.contactUs.setOnClickListener(v -> {
            switchFragment(new ContactUsFragment(), "ContactUsFragment");
        });


    }

    private void getProducts() {
        productList = new ArrayList<>();
        binding.productTelRecyclerView.setVisibility(View.GONE);
        binding.shimmerProduct.setVisibility(View.VISIBLE);
        MySingleton.getInstance(getContext()).addToRequestQueue(api.moreProducts(1, Integer.parseInt(catId), response -> {
            try {
                JSONArray array = response.getJSONArray("product");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    productList.add(new ProductView(
                            json.getString(Constant.Product.NAME),
                            json.getString(Constant.Product.IMAGE),
                            json.getDouble(Constant.Product.PREVIOUS_PRICE),
                            json.getDouble(Constant.Product.DISCOUNT), 4,
                            json.getLong(Constant.Category.CATEGORY_ID),
                            json.getLong(Constant.Category.SUB_CATEGORY_ID),
                            json.getLong(Constant.Category.BRAND_ID),
                            json.getLong(Constant.Product.PRODUCT_ID),
                            json.getString(Constant.Product.OFFER_DATE),
                            json.getString(Constant.Product.UPLOAD_BY),
                            json.getDouble(Constant.Product.POINT)));
                }
                binding.shimmerProduct.setVisibility(View.GONE);
                binding.productTelRecyclerView.setVisibility(View.VISIBLE);
                /*productAdapter = new ProductAdapter(getContext(), productList).addOnClickListener((View, position) -> {
                    ProductView productView = productList.get(position);
                    startActivity(new Intent(getContext(), ProductDetailsActivity.class).putExtra(Constant.Product.PARCEL, productList.get(position)));
                });*/
                binding.productTelRecyclerView.setAdapter(productAdapter);
                binding.productTelRecyclerView.setVisibility(View.VISIBLE);
                isGrid = true;
                binding.ghomeIbGrid.setOnClickListener(v -> {
                    binding.productTelRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 3));
                    binding.productTelRecyclerView.setAdapter(productAdapter);
                    isGrid = true;
                });

                binding.ghomeIbList.setOnClickListener(v -> {
                    binding.productTelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.productTelRecyclerView.setAdapter(productAdapter);
                    isGrid = false;
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = ((MainActivity) getActivity()).getSupportFragmentManager();
        /*for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();*/
        FragmentTransaction childFragTrans = fm.beginTransaction();
        childFragTrans.replace(R.id.fragmentContainer, fragment, tag);
        childFragTrans.addToBackStack(tag);
        childFragTrans.commit();
    }
}