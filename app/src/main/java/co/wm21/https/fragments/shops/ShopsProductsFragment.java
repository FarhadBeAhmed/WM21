package co.wm21.https.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.adapters.product.ProductAdapter;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.activities.ProductDetailsActivity;
import co.wm21.https.databinding.FragmentShopsProductsBinding;
import co.wm21.https.helpers.Constant;

public class ShopsProductsFragment extends Fragment {

    FragmentShopsProductsBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shops_products, container, false);

        Bundle thisBundle = this.getArguments();
        if (!thisBundle.getString(ConstantValues.admin_login.SHOP_TYPE, "").isEmpty() ) {
            binding.layoutItem.typeName.setText( thisBundle.getString(ConstantValues.admin_login.SHOP_TYPE, ""));
        }
        if (!thisBundle.getString(ConstantValues.admin_login.SHOP_NAME, "").isEmpty() ) {
            binding.layoutItem.shopName.setText( thisBundle.getString(ConstantValues.admin_login.SHOP_NAME, ""));
        }
        if (!thisBundle.getString(ConstantValues.admin_login.MOBILE, "").isEmpty() ) {
            binding.layoutItem.number.setText( thisBundle.getString(ConstantValues.admin_login.MOBILE, ""));
        }
        if (!thisBundle.getString(ConstantValues.admin_login.ADDRESS, "").isEmpty() ) {
            binding.layoutItem.address.setText( thisBundle.getString(ConstantValues.admin_login.ADDRESS, ""));
        }
        if (!thisBundle.getString(ConstantValues.admin_login.PHOTO, "").isEmpty() ) {
            Picasso.get().load(ConstantValues.web_url + "shop/img/shop/" + thisBundle.getString(ConstantValues.admin_login.PHOTO, "")).into( binding.layoutItem.shopCoverPhoto);

        }
        if (!thisBundle.getString(ConstantValues.admin_login.IMAGE, "").isEmpty() ) {
            Picasso.get().load(ConstantValues.web_url + "shop/img/shop/" + thisBundle.getString(ConstantValues.admin_login.IMAGE, "")).into( binding.layoutItem.shopImage);
        }

        if (thisBundle.getInt(ConstantValues.admin_login.SHOP_ID)!=0 && thisBundle.getInt(ConstantValues.admin_login.TYPE_ID)!=0) {
            product(thisBundle.getInt(ConstantValues.admin_login.TYPE_ID),thisBundle.getInt(ConstantValues.admin_login.SHOP_ID));
        }







        return binding.getRoot();
    }


    private void product(int shop,int sID) {

        ArrayList<ProductView> productViews = new ArrayList<>();
        co.wm21.https.FHelper.API api2 = co.wm21.https.FHelper.ConstantValues.getAPI();
        binding.shimmerProduct.setVisibility(View.VISIBLE);
        binding.productRecyclerView.setVisibility(View.GONE);
        MySingleton.getInstance(getContext()).addToRequestQueue(api2.shopsProducts(shop,sID, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("shopProducts");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    productViews.add(new ProductView(
                            json.getString(ConstantValues.PopularProduct.NAME),
                            json.getString(ConstantValues.PopularProduct.IMAGE),
                            json.getDouble(ConstantValues.PopularProduct.PREVIOUS_PRICE),
                            json.getDouble(ConstantValues.PopularProduct.DISCOUNT), 4,
                            json.getLong(Constant.Category.CATEGORY_ID),
                            json.getLong(Constant.Category.SUB_CATEGORY_ID),
                            json.getLong(Constant.Category.BRAND_ID),
                            json.getLong(Constant.PopularProduct.PRODUCT_ID),
                            json.getString(Constant.Product.OFFER_DATE),
                            json.getString(Constant.Product.UPLOAD_BY),
                            json.getDouble(ConstantValues.Product.POINT)
                    ));

                }
                binding.productRecyclerView.setAdapter(new ProductAdapter(getContext(), productViews).addOnClickListener((View, position2) -> {
                    ProductView productView = productViews.get(position2);
                    Activity activity = getActivity();
                    if (activity != null) {
                        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                        intent.putExtra(Constant.Product.PARCEL, productView);
                        startActivityForResult(intent, 2);

                    }
                }));
                binding.shimmerProduct.setVisibility(View.GONE);
                binding.productRecyclerView.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));

    }
}