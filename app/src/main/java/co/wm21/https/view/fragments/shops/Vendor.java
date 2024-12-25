package co.wm21.https.view.fragments.shops;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.SliderItem;
import co.wm21.https.view.activities.ShopSearchScanActivity;
import co.wm21.https.view.adapters.ShopsAdapter;
import co.wm21.https.view.adapters.SliderAdapter;
import co.wm21.https.databinding.FragmentVendorBinding;
import co.wm21.https.view.activities.mlm.company.company_fragments.AboutUsFragment;
import co.wm21.https.view.activities.mlm.company.company_fragments.ContactUsFragment;
import co.wm21.https.utils.Constant;
import co.wm21.https.model.ShopsModel;

public class Vendor extends Fragment {
    FragmentVendorBinding binding;
    API api;
    ShopsAdapter adapter;
    List<SliderItem> sliderItemList;
    SliderAdapter sliderAdapter;
    Handler mainHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVendorBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                SecondThread thread = new SecondThread();
                thread.start();
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
               /* switch (shopName.toString()) {
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
                }*/
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


        binding.scanSearch.setOnClickListener(view1 -> {
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

    public class SecondThread extends Thread {
        @Override
        public void run() {
            super.run();
            getAllShops();
        }

        private void getAllShops() {
            ArrayList<ShopsModel> shopList = new ArrayList<>();
            mainHandler.post(() -> {
                binding.recycleView.setVisibility(View.GONE);
                binding.shimmerShowroom.setVisibility(View.VISIBLE);
            });
            MySingleton.getInstance(getContext()).addToRequestQueue(api.premiumShop(6, response -> {
                try {
                    JSONArray array = response.getJSONArray("shops");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
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
                    mainHandler.post(() -> {
                        binding.shimmerShowroom.setVisibility(View.GONE);
                        binding.recycleView.setVisibility(View.VISIBLE);
                    });
                    binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    adapter = new ShopsAdapter(shopList, getContext(), R.layout.layout_item_premium_shop);
                    if (!shopList.isEmpty()) {
                        binding.recycleView.setAdapter(adapter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));


        }


    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction childFragTrans = fm.beginTransaction();
        childFragTrans.replace(R.id.fragmentContainer, fragment, tag);
        childFragTrans.addToBackStack(tag);
        childFragTrans.commit();
    }
}
