package co.wm21.https.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.R;
import co.wm21.https.view.adapters.PopularCatProductAdapter;
import co.wm21.https.databinding.ActivityHomeMoreProductBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.utils.Constant;
import co.wm21.https.presenter.interfaces.OnHomePopularCatProductView;
import co.wm21.https.presenter.HomePopularCatProductPresenter;

public class HomeMoreProductActivity extends AppCompatActivity implements OnHomePopularCatProductView {

    ActivityHomeMoreProductBinding binding;
    HomePopularCatProductPresenter popularCatProductPresenter;
    ArrayList<ProductModel> popularCatProductList;
    PopularCatProductAdapter productAdapter;
    LoadingDialog loadingDialog;
    String catID;
    String catName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeMoreProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog=new LoadingDialog(this);
        popularCatProductPresenter=new HomePopularCatProductPresenter(this);
        binding.backButtonId.setOnClickListener(view -> {
            this.onBackPressed();
            Animatoo.INSTANCE.animateSlideRight(this);
        });
        Bundle thisBundle = getIntent().getExtras();
        assert thisBundle != null;
        catID = !thisBundle.equals("") ? thisBundle.getString(ConstantValues.ARGUMENT1) : "";
        catName = !thisBundle.equals("") ? thisBundle.getString(ConstantValues.ARGUMENT2) : "";
        popularCategoryProduct(catID,"","","");

    }
    private void popularCategoryProduct(String value1,String value2,String value3,String value4) {

        binding.name.setText(catName);

        popularCatProductList = new ArrayList<>();

        productAdapter=new PopularCatProductAdapter( popularCatProductList,getApplicationContext(), R.layout.layout_item_product2,200,1).addOnClickListener((View, position2) -> {
            ProductModel productView = popularCatProductList.get(position2);

                Intent intent = new Intent(this, ProductDetailsActivity.class);
                intent.putExtra(Constant.Product.PARCEL, productView);
                startActivityForResult(intent, 2);


        });

        binding.catProductsRecView.setAdapter(productAdapter);

        popularCatProductPresenter.getHomePopularProduct(value1,value2,value3,value4);



    }

    @Override
    public void onHomePopularCatProductLoaded(List<ProductModel> productViews) {
        popularCatProductList.addAll(productViews);
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHomePopularCatProductStartLoading() {
        loadingDialog.startLoadingAlertDialog();
        binding.shimmerProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHomePopularCatProductStopLoading() {
        loadingDialog.dismissDialog();
        binding.shimmerProduct.setVisibility(View.GONE);
    }

    @Override
    public void onHomePopularCatProductShowMessage(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }
}