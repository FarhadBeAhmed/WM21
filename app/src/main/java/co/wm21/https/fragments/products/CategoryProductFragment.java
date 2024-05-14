package co.wm21.https.fragments.products;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.DrawerCatModel;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.R;
import co.wm21.https.adapters.category.SubCatAdapter;
import co.wm21.https.adapters.category.SubCatModel;
import co.wm21.https.adapters.product.ProductAdapter;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.activities.ProductDetailsActivity;
import co.wm21.https.databinding.FragmentCategoryProductBinding;
import co.wm21.https.interfaces.OnDrawerCatListView;
import co.wm21.https.interfaces.OnRelatedProductListView;
import co.wm21.https.presenter.DrawerCatListPresenter;
import co.wm21.https.presenter.RelatedProductListPresenter;


public class CategoryProductFragment extends Fragment implements OnDrawerCatListView, OnRelatedProductListView {

    String catId, subCat_Id,brandId;
    ArrayList<ProductModel> productViews = new ArrayList<>();
    ArrayList<DrawerCatModel> CategoryList = new ArrayList<>();
    FragmentCategoryProductBinding binding;
    SubCatAdapter adapter;
    ProductAdapter productAdapter;
    DrawerCatListPresenter drawerCatListPresenter;
    RelatedProductListPresenter relatedProductListPresenter;
    MaterialDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_product, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new MaterialDialog.Builder(getActivity()).title(getResources().getString(R.string.loading))
                .content(getResources().getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build();

        drawerCatListPresenter=new DrawerCatListPresenter(this);
        relatedProductListPresenter=new RelatedProductListPresenter(this);


        binding.scatRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new SubCatAdapter(getContext(), CategoryList, R.layout.item_category_layout_2);
        //  holder.subcatRecView.setHasFixedSize(true);
        binding.scatRecView.setAdapter(adapter);

        binding.catProductsRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productAdapter= new ProductAdapter(getContext(), productViews).addOnClickListener((View, position2) -> {
            ProductModel productView = productViews.get(position2);
            Activity activity = getActivity();
            if (activity != null)
                startActivity(new Intent(getContext(), ProductDetailsActivity.class)
                        .putExtra(ConstantValues.Product.PARCEL, productView));
        });
        binding.catProductsRecView.setAdapter(productAdapter);



        Bundle thisBundle = this.getArguments();
        if (!thisBundle.getString(ConstantValues.SUB_CAT_ID, "").isEmpty() ) {
            subCat_Id = thisBundle.getString(ConstantValues.SUB_CAT_ID, "");
            binding.name.setText( thisBundle.getString(ConstantValues.NAME, ""));
            if (subCat_Id != null) {
                drawerCatListPresenter.onDrawerCatDataLoad(2,catId);

                relatedProductListPresenter.RelatedProductDataLoad("200","0", subCat_Id, "0");


            }
        }else if (!thisBundle.getString(ConstantValues.ARGUMENT1, "").isEmpty()) {
            catId = thisBundle.getString(ConstantValues.ARGUMENT1, "");
            binding.name.setText( thisBundle.getString(ConstantValues.NAME, ""));
            if (catId != null) {
                drawerCatListPresenter.onDrawerCatDataLoad(1,catId);
                relatedProductListPresenter.RelatedProductDataLoad("200",catId, "0", "0");


            }
        }else if (!thisBundle.getString(ConstantValues.BRAND_ID, "").isEmpty()) {
            brandId = thisBundle.getString(ConstantValues.BRAND_ID, "");
            binding.name.setText( thisBundle.getString(ConstantValues.NAME, ""));
            if (brandId != null) {
                binding.scatRecView.setVisibility(View.GONE);
                binding.shimmerScat.setVisibility(View.GONE);
                drawerCatListPresenter.onDrawerCatDataLoad(1,catId);
                relatedProductListPresenter.RelatedProductDataLoad("200","0", "0", brandId);



            }
        }




    }

    @Override
    public void onDrawerCatListDataLoad(List<DrawerCatModel> drawerCatModels) {

        drawerCatModels.addAll(drawerCatModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDrawerCatListStartLoading() {
        binding.shimmerScat.setVisibility(View.VISIBLE);
        binding.scatRecView.setVisibility(View.GONE);
    }

    @Override
    public void onDrawerCatListStopLoading() {
        binding.shimmerScat.setVisibility(View.GONE);
        binding.scatRecView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDrawerCatListShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRelatedProductListDataLoad(List<ProductModel> relatedProductModelHeads) {
        productViews.addAll(relatedProductModelHeads);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRelatedProductListStartLoading() {
        binding.shimmerProduct.setVisibility(View.VISIBLE);
        binding.noDataFound.setVisibility(View.GONE);
        binding.catProductsRecView.setVisibility(View.GONE);

    }

    @Override
    public void onRelatedProductListStopLoading() {
        binding.shimmerProduct.setVisibility(View.GONE);
        binding.catProductsRecView.setVisibility(View.VISIBLE);
        binding.noDataFound.setVisibility(View.GONE);

    }

    @Override
    public void onRelatedProductListShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }
}