package co.wm21.https.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.R;
import co.wm21.https.adapters.product.ProductAdapter;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.api_request.Json;
import co.wm21.https.databinding.FragmentMainShopBinding;
import co.wm21.https.helpers.API;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.User;

public class ShopFragment extends Fragment {

    static co.wm21.https.helpers.API API;
    static User user;

    FragmentMainShopBinding binding;

    ArrayList<ProductView> productList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_shop, container, false);

        API = Constant.getAPI();
        user = new User(getContext());

        productList = new ArrayList<>();

        Json.addRequests(API.product(9).before(obj -> binding.productRecyclerView.setVisibility(View.GONE)).success(response -> {
            try {
                Log.d("PRODUCT", "onCreateView: "+ response +" - "+productList.size());

                for (int i = 0; i < response.length(); i++) {
                    JSONObject json = response.getJSONObject(i);
                    productList.add(new ProductView(json.getString(Constant.Product.NAME),
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
            binding.productRecyclerView.setAdapter(new ProductAdapter(getContext(), productList).addOnClickListener((View, position) -> {
                ProductView productView = productList.get(position);
//                startActivity(new Intent(getContext(), ProductDetailsActivity.class)
//                        .putExtra("cat_id", productView.getCatId())
//                        .putExtra("scat_id", productView.getScatId())
//                        .putExtra("brand_id", productView.getBrandId())
//                        .putExtra("product_id", productView.getProductId()));
            }));
            binding.productRecyclerView.setVisibility(View.VISIBLE);
            //load.loadFragment(this);
        }));
        return binding.getRoot();
    }
}
