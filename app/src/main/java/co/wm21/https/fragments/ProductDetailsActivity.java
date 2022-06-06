package co.wm21.https.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.wm21.https.R;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.databinding.ActivityProductDetailsBinding;


public class ProductDetailsActivity extends AppCompatActivity {
    ActivityProductDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        ProductView product = (ProductView) getIntent().getSerializableExtra("product");
        Intent intent = getIntent();
        ProductView product = new ProductView();
        product.setProductName(intent.getStringExtra("name"));
        product.setImage(intent.getStringExtra("image_url"));
        product.setPrice(intent.getDoubleExtra("price", 0));
        product.setDiscount(intent.getDoubleExtra("discount", 0));

        binding.productDisplayPhoto.setImageDrawable(product.getImage());
        binding.productName.setText(product.getProductName());
        binding.productPrice.setText(String.format("৳ %s", product.getCurrentPrice()));
        binding.productOldPrice.setText(String.format("৳ %s", product.getPrice()));

        binding.productOldPrice.setPaintFlags(binding.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }
}