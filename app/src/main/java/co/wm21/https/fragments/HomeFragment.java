package co.wm21.https.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.wm21.https.R;
import co.wm21.https.SliderItem;
import co.wm21.https.adapters.category.CategoryView;
import co.wm21.https.adapters.category.CategoryAdapter;
import co.wm21.https.adapters.SliderAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuView;
import co.wm21.https.adapters.product.ProductAdapter;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.api_request.Json;
import co.wm21.https.databinding.FragmentMainHomeBinding;
import co.wm21.https.helpers.*;

public class HomeFragment extends Fragment {

//    private static final String ARG_COLUMN_COUNT = "column-count";
//    private int mColumnCount = 1;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
//    }

    List<SliderItem> sliderItemList;
    ArrayList<ProductView> productViews;
    ArrayList<CategoryView> categoryList;
    static API API;
    static User user;
    SliderAdapter adapter;
    FragmentMainHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_home, container, false);

        API = Constant.getAPI();
        user = new User(getContext());

//        if (!user.getSession().isLoggedIn())
//            binding.ecommerceRecyclerView.setVisibility(View.GONE);
//        else {
            ArrayList<ItemMenuView> itemMenuViews = new ArrayList<>(Arrays.asList(
                    new ItemMenuView("Facebook", R.drawable.ic_facebook),
                    new ItemMenuView("Youtube", R.drawable.ic_youtube_dark),
                    new ItemMenuView("Order", R.drawable.ic_orders),
                    new ItemMenuView("Applied", R.drawable.ic_heart_outline),
                    new ItemMenuView("Delivered", R.drawable.ic_location_outline),
                    new ItemMenuView("Received", R.drawable.ic_shirt_outline)));

            binding.ecommerceRecyclerView.setAdapter(new ItemMenuAdapter(getContext(), itemMenuViews, R.layout.item_category_layout_2));
//        }
        sliderItemList = new ArrayList<>();
        adapter = new SliderAdapter(getContext());
        productViews = new ArrayList<>();

//        new SliderImageTask().execute();
        Json.addRequests(API.slide().before(obj -> binding.imageSlider.setVisibility(View.GONE)).success(response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject json = response.getJSONObject(i);
                    json.getString(Constant.Slide.INFO);
                    sliderItemList.add(i, new SliderItem(json.getString(Constant.Slide.IMAGE), json.getString(Constant.Slide.INFO)));
                }
                Log.d("SLIDER IMAGE", "onCreateView: "+ response +" - "+sliderItemList.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).after(returnObj -> {
            binding.imageSlider.setVisibility(View.VISIBLE);
            adapter.renewItems(sliderItemList);
            binding.imageSlider.setSliderAdapter(adapter);

            binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            binding.imageSlider.startAutoCycle();
        }));

        Json.addRequests(API.category().before(obj -> {
            binding.categoryRecyclerView.setVisibility(View.GONE);
            binding.titleCategory.setVisibility(View.GONE);
        }).success(response -> {
            categoryList = new ArrayList<>();
            try {
                Log.d("CATEGORY", "onCreateView: " + response + " - " + categoryList.size());
                for (int i = 0; i < response.length(); i++) {
                    JSONObject json = response.getJSONObject(i);
                    categoryList.add(new CategoryView(json.getString(Constant.Category.CATEGORY_NAME), json.getString(Constant.Category.CATEGORY_IMAGE)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).after(returnObj -> {
            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList, Constant.GRID_LAYOUT);
            binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.categoryRecyclerView.setAdapter(categoryAdapter);
            binding.titleCategory.setVisibility(View.VISIBLE);
            binding.categoryRecyclerView.setVisibility(View.VISIBLE);
        }));

        Json.addRequests(API.product().before(obj -> binding.productRecyclerView.setVisibility(View.GONE)).success(response -> {
            try {
                Log.d("PRODUCT", "onCreateView: "+ response +" - "+productViews.size());

                for (int i = 0; i < response.length(); i++) {
                    JSONObject json = response.getJSONObject(i);
                    productViews.add(new ProductView(json.getString(Constant.Product.NAME),
                            json.getString(Constant.Product.IMAGE),
                            json.getDouble(Constant.Product.PREVIOUS_PRICE),
                            json.getDouble(Constant.Product.DISCOUNT), 4,
                            json.getLong(Constant.Category.CATEGORY_ID),
                            json.getLong(Constant.Category.SUB_CATEGORY_ID),
                            json.getLong(Constant.Category.BRAND_ID),
                            json.getLong(Constant.Product.PRODUCT_ID)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).after(returnObj -> {
            binding.productRecyclerView.setAdapter(new ProductAdapter(getContext(), productViews).addOnClickListener((View, position) -> {
                ProductView productView = productViews.get(position);
//                startActivity(new Intent(getContext(), ProductDetailsActivity.class)
//                        .putExtra("cat_id", productView.getCatId())
//                        .putExtra("scat_id", productView.getScatId())
//                        .putExtra("brand_id", productView.getBrandId())
//                        .putExtra("product_id", productView.getProductId()));
            }));
            binding.productRecyclerView.setVisibility(View.VISIBLE);
            //load.loadFragment(this);
        }));

        binding.pullToRefresh.setOnRefreshListener(() -> {
            sliderItemList = new ArrayList<>();
            productViews = new ArrayList<>();

            adapter = new SliderAdapter(getContext());

            Json.addRequests(API.slide().before(obj -> binding.imageSlider.setVisibility(View.GONE)).success(response -> {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject json = response.getJSONObject(i);
                        json.getString(Constant.Slide.INFO);
                        sliderItemList.add(i, new SliderItem(json.getString(Constant.Slide.IMAGE), json.getString(Constant.Slide.INFO)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).after(returnObj -> {
                binding.imageSlider.setVisibility(View.VISIBLE);
                adapter.renewItems(sliderItemList);
                binding.imageSlider.setSliderAdapter(adapter);

                binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                binding.imageSlider.startAutoCycle();
            }), API.product().before(obj -> binding.productRecyclerView.setVisibility(View.GONE)).success(response -> {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject json = response.getJSONObject(i);
                        productViews.add(new ProductView(json.getString(Constant.Product.NAME),
                                json.getString(Constant.Product.IMAGE),
                                json.getDouble(Constant.Product.PREVIOUS_PRICE),
                                json.getDouble(Constant.Product.DISCOUNT), 4,
                                json.getLong(Constant.Category.CATEGORY_ID),
                                json.getLong(Constant.Category.SUB_CATEGORY_ID),
                                json.getLong(Constant.Category.BRAND_ID),
                                json.getLong(Constant.Product.PRODUCT_ID)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).after(returnObj -> {
                binding.productRecyclerView.setVisibility(View.VISIBLE);
                binding.productRecyclerView.setAdapter(new ProductAdapter(getContext(), productViews).addOnClickListener((View, position) -> {
                    ProductView productView = productViews.get(position);
//                    startActivity(new Intent(getContext(), ProductDetailsActivity.class)
//                            .putExtra("cat_id", productView.getCatId())
//                            .putExtra("scat_id", productView.getScatId())
//                            .putExtra("brand_id", productView.getBrandId())
//                            .putExtra("product_id", productView.getProductId()));
                }));
            }), API.sub_category().before(obj -> binding.categoryRecyclerView.setVisibility(View.GONE)).success(response -> {
                ArrayList<CategoryView> categoryList = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject json = response.getJSONObject(i);
                        categoryList.add(new CategoryView(json.getString(Constant.Category.CATEGORY_NAME), json.getString(Constant.Category.CATEGORY_IMAGE)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return categoryList;
            }).after(returnObj -> {
                binding.categoryRecyclerView.setAdapter(new CategoryAdapter(getContext(), (ArrayList<CategoryView>) returnObj, Constant.GRID_LAYOUT));
                binding.categoryRecyclerView.setVisibility(View.VISIBLE);
            }));
            binding.pullToRefresh.setRefreshing(false);
        });
        return binding.getRoot();
    }

    class SliderImageTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            sliderItemList = new ArrayList<>();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Json.addRequests(API.slide().success(response -> {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject json = response.getJSONObject(i);
                        sliderItemList.add(new SliderItem(json.getString(Constant.Slide.IMAGE), json.getString(Constant.Slide.INFO)));
                        adapter.addItem(new SliderItem(json.getString(Constant.Slide.IMAGE), json.getString(Constant.Slide.INFO)));
                    }
                    Log.d("SLIDER IMAGE", "onCreateView: "+ response +" - "+sliderItemList.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
//            if (sliderItemList.size() > 0) {
//            adapter.renewItems(sliderItemList);
            binding.imageSlider.setSliderAdapter(adapter);

            binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            binding.imageSlider.startAutoCycle();
            binding.imageSlider.setVisibility(View.VISIBLE);
//            }
        }
    }
}