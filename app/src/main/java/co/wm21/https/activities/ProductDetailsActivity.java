package co.wm21.https.fragments.productDetails;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.adapters.product.ProductAdapter;
import co.wm21.https.adapters.product.ProductView;
import co.wm21.https.databinding.ActivityProductDetailsBinding;
import co.wm21.https.helpers.Constant;
import co.wm21.https.helpers.User;


public class ProductDetailsActivity extends AppCompatActivity {
    ActivityProductDetailsBinding binding;
    ProductView productView;
    long qty;
    long productId;
    API api;

    List<String> colorList = new ArrayList<>();
    List<String> sizeList = new ArrayList<>();
    String colors = "";
    String sizes = "";
    View thisView;
    User user;
    RadioGroup colorsRadioGroup, sizeRadioGroup;
    RadioButton colorsRadioButton, sizeRadioButton;
    String venId = "";
    String scat = "";
    String reviewPhoneNumber = "";
    ProductView product;
    ProgressDialog progressBar;
    String uId = "";
    String selectedColor = "", selectedSize = "";
    public static int totalSelected2 = 0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = new User(getApplicationContext());
        api = ConstantValues.getAPI();
        progressBar = new ProgressDialog(this);
        product = new ProductView();


//        ProductView product = (ProductView) getIntent().getSerializableExtra("product");
        Intent intent = getIntent();

        if (getIntent().getParcelableExtra(Constant.Product.PARCEL) != null) {
            productView = getIntent().getParcelableExtra(Constant.Product.PARCEL);
            product.setProductName(productView.getProductName());
            product.setImageUrl(productView.getImageUrl());
            product.setPrice(productView.getPrice());
            product.setDiscount(productView.getDiscount());
            product.setProductId(productView.getProductId());
        }

        Picasso.get().load(ConstantValues.web_url + "image/product/small/" + productView.getImageUrl()).into(binding.productDisplayPhoto);
        binding.productName.setText(product.getProductName());
        binding.productPrice.setText(String.format("৳ %s", product.getCurrentPrice()));
        binding.productOldPrice.setText(String.format("৳ %s", product.getPrice()));
        binding.productOldPrice.setPaintFlags(binding.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.addToCardButton.setOnClickListener(this::addToCart);

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.product_details(product.getProductId(), response -> {
            try {
                if (response.getString(ConstantValues.ERROR).equals("0")) {

                    colors = response.getString(ConstantValues.Product.COLOR);
                    venId = response.getString(ConstantValues.Product.UPLOAD_BY);
                    sizes = response.getString(ConstantValues.Product.SIZE);
                    scat = response.getString(ConstantValues.Product.SCAT_ID);
                    binding.prInfo.setText(response.getString(ConstantValues.Product.INFO).equals("") ? "" : response.getString(ConstantValues.Product.INFO));
                    getProductDetails();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));
        binding.plusQty.setOnClickListener(view -> {
            String q = binding.quantity.getText().toString();
            int qty = Integer.parseInt(q);
            qty += 1;
            binding.quantity.setText(String.valueOf(qty));
        });
        binding.minusQty.setOnClickListener(view -> {
            String q = binding.quantity.getText().toString();
            int qty = Integer.parseInt(q);
            if (qty > 1) qty -= 1;
            binding.quantity.setText(String.valueOf(qty));
        });

        binding.reviewDialogBtn.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            // ...Irrelevant code for customizing the buttons and title
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.submit_review_dialog, null);
            dialogBuilder.setView(dialogView);
            /*AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailsActivity.this);
            ViewGroup viewGroup = view.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(ProductDetailsActivity.this).inflate(R.layout.submit_review_dialog, viewGroup, false);*/
            //SubmitReviewDialogBinding dialogBinding= DataBindingUtil.inflate(getLayoutInflater(),R.layout.submit_review_dialog,viewGroup,false);
            //SubmitReviewDialogBinding dialogBinding= SubmitReviewDialogBinding.inflate(LayoutInflater.from(getApplicationContext()));
            dialogBuilder.setView(dialogView);
            AlertDialog alertDialog = dialogBuilder.create();
            RatingBar ratingBar2 = dialogView.findViewById(R.id.ratingBar);
            TextView ratingText = dialogView.findViewById(R.id.ratingText);
            TextInputLayout mobileNum = dialogView.findViewById(R.id.mobileNumber);
            AppCompatButton submitBtn = dialogView.findViewById(R.id.reviewSubmitButton);
            ratingBar2.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                ratingText.setText(String.valueOf(rating));
            });
            submitBtn.setOnClickListener(view1 -> {
                if (ratingBar2.getRating() > 0) {
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Rating Shouldn't be empty", Toast.LENGTH_SHORT).show();
                }
            });
            if (user.getSession().isLoggedIn()) {
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.profile(user.getUsername(), user.getPassword(), response -> {
                    try {
                        mobileNum.getEditText().setText(response.getString(ConstantValues.profile.MOBILE));
                        mobileNum.getEditText().setClickable(false);
                        mobileNum.getEditText().setFocusable(false);
                        mobileNum.getEditText().setEnabled(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }));
            }

            alertDialog.show();
        });

    }

    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    private void getProductDetails() {
        if (!colors.equals("")) {
            binding.colorsLayout.setVisibility(View.VISIBLE);
            String[] col = colors.split(",");
            colorList.addAll(Arrays.asList(col));

            if (!colorList.isEmpty()) {
                colorsRadioGroup = new RadioGroup(this);
                colorsRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
                colorsRadioGroup.setPadding(10, 0, 0, 0);

                RadioGroup.LayoutParams layoutParams;
                for (int i = 0; i < colorList.size(); i++) {
                    colorsRadioButton = new RadioButton(this);
                    colorsRadioButton.setText(colorList.get(i));
                    colorsRadioButton.setBackground(getResources().getDrawable(R.drawable.radio_selector));
                    colorsRadioButton.setButtonDrawable(getResources().getDrawable(R.color.transparent));
                    //colorsRadioButton.setTextColor(getResources().getColor(R.drawable.radio_text_color_pd));
                    colorsRadioButton.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.radio_text_color_pd));
                    colorsRadioButton.setPadding(20, 12, 12, 20);
                    layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(15, 0, 0, 0);
                   // colorsRadioGroup.addView(colorsRadioButton, layoutParams);
                    binding.multiLineRadioGroupForColors.checkAt(0);
                    binding.multiLineRadioGroupForColors.setMaxInRow(4);
                    binding.multiLineRadioGroupForColors.addView(colorsRadioButton, layoutParams);

                   // if (i == 0) colorsRadioButton.setChecked(true);
                }
                //GridLayout.LayoutParams params= (GridLayout.LayoutParams) binding.colorGroup.getLayoutParams();params.columnSpec=2;
               // binding.colorGroup.addView(colorsRadioGroup);
                if (colorList.size()>5)
                colorsRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                    RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                });

            }
        } else {
            binding.colorsLayout.setVisibility(View.GONE);
        }

        if (!sizes.equals("")) {
            binding.sizeLayout.setVisibility(View.VISIBLE);
            String[] siz = sizes.split(",");
            sizeList.addAll(Arrays.asList(siz));
            if (!sizes.isEmpty()) {
                sizeRadioGroup = new RadioGroup(this);
                sizeRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
                sizeRadioGroup.setPadding(10, 0, 0, 0);

                RadioGroup.LayoutParams layoutParams;
                for (int i = 0; i < sizeList.size(); i++) {
                    sizeRadioButton = new RadioButton(this);
                    sizeRadioButton.setText(sizeList.get(i));
                    sizeRadioButton.setBackground(getResources().getDrawable(R.drawable.radio_selector));
                    sizeRadioButton.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.radio_text_color_pd));
                    sizeRadioButton.setButtonDrawable(getResources().getDrawable(R.color.transparent));
                    sizeRadioButton.setPadding(20, 12, 12, 20);
                    layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(15, 0, 0, 0);
                    sizeRadioGroup.addView(sizeRadioButton, layoutParams);
                    if (i == 0) sizeRadioButton.setChecked(true);
                }
                binding.sizeGroup.addView(sizeRadioGroup);
                sizeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                       // Toast.makeText(getApplicationContext(), checkedRadioButton.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        } else {
            binding.sizeLayout.setVisibility(View.GONE);
        }
        vendorDetails();
        vendorProducts(venId);
    }

    private void vendorDetails() {
        if (!venId.equals("")) {
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.vendor_details(venId, response -> {
                try {
                    if (response.getString("error").equals("0")) {
                        binding.venType.setText(response.getString(ConstantValues.vendor.VEN_TYPE).equals("") ? "{Not Found}" : response.getString(ConstantValues.vendor.VEN_TYPE));
                        binding.venName.setText(response.getString(ConstantValues.vendor.VEN_NAME).equals("") ? "{Not Found}" : response.getString(ConstantValues.vendor.VEN_NAME));
                        binding.venAddress.setText(response.getString(ConstantValues.vendor.ADDRESS).equals("") ? "{Not Found}" : response.getString(ConstantValues.vendor.ADDRESS));
                        binding.venNumber.setText(response.getString(ConstantValues.vendor.VEN_NUMBER).equals("") ? "{Not Found}" : response.getString(ConstantValues.vendor.VEN_NUMBER));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));
        }

    }

    private void vendorProducts(String venId) {
        if (!venId.equals("")) {
            ArrayList<ProductView> productViews = new ArrayList<>();
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.ven_products(venId, response -> {
                try {

                    if (!response.getString("rows").equals("0")) {
                        JSONArray jsonArray = response.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            productViews.add(new ProductView(
                                    json.getString(Constant.PopularProduct.NAME),
                                    json.getString(Constant.PopularProduct.IMAGE),
                                    json.getDouble(Constant.PopularProduct.PREVIOUS_PRICE),
                                    json.getDouble(Constant.PopularProduct.DISCOUNT), 4,
                                    json.getLong(Constant.Category.CATEGORY_ID),
                                    json.getLong(Constant.Category.SUB_CATEGORY_ID),
                                    json.getLong(Constant.Category.BRAND_ID),
                                    json.getLong(Constant.PopularProduct.PRODUCT_ID),
                                    json.getString(Constant.Product.OFFER_DATE),
                                    json.getString(Constant.Product.UPLOAD_BY),
                                    json.getDouble(Constant.Product.POINT)));

                        }
                        binding.vendorProductRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                        binding.vendorProductRecyclerView.setAdapter(new ProductAdapter(getApplicationContext(), productViews, R.layout.layout_item_product_for_horizontal).addOnClickListener((View, position2) -> {
                            ProductView productView = productViews.get(position2);
                            startActivity(new Intent(this, ProductDetailsActivity.class)
                                    .putExtra(Constant.Product.PARCEL, productView));
                        }));
                    }
                    relatedProducts();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));


        }
    }

    private void relatedProducts() {
        if (!scat.equals("")) {
            ArrayList<ProductView> productViews = new ArrayList<>();
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.apps_products("0", scat, "0", response -> {
                try {
                    if (!response.getString("rows").equals("0")) {
                        JSONArray jsonArray = response.getJSONArray("appsProduct");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.getJSONObject(i);
                            productViews.add(new ProductView(
                                    json.getString(Constant.PopularProduct.NAME),
                                    json.getString(Constant.PopularProduct.IMAGE),
                                    json.getDouble(Constant.PopularProduct.PREVIOUS_PRICE),
                                    json.getDouble(Constant.PopularProduct.DISCOUNT), 4,
                                    json.getLong(Constant.Category.CATEGORY_ID),
                                    json.getLong(Constant.Category.SUB_CATEGORY_ID),
                                    json.getLong(Constant.Category.BRAND_ID),
                                    json.getLong(Constant.PopularProduct.PRODUCT_ID),
                                    json.getString(Constant.Product.OFFER_DATE),
                                    json.getString(Constant.Product.UPLOAD_BY),
                                    json.getDouble(Constant.Product.POINT)));

                        }
                        binding.relatedProductRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                        binding.relatedProductRecyclerView.setAdapter(new ProductAdapter(getApplicationContext(), productViews, R.layout.layout_item_product_for_horizontal).addOnClickListener((View, position2) -> {
                            ProductView productView = productViews.get(position2);
                            startActivity(new Intent(this, ProductDetailsActivity.class)
                                    .putExtra(Constant.Product.PARCEL, productView));
                        }));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }));
        }

    }

    @SuppressLint("HardwareIds")
    private void addToCart(View view) {
        progressBar.setCancelable(false);
        progressBar.setMessage("Loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();//displays the progress bar
        if (!colors.equals("")) {
            int selectedIdCol = binding.multiLineRadioGroupForColors.getCheckedRadioButtonId();
            colorsRadioButton = (RadioButton) findViewById(selectedIdCol);
            selectedColor = this.colorsRadioButton.getText().toString();
        }
        if (!sizes.equals("")) {
            int selectedIdSiz = sizeRadioGroup.getCheckedRadioButtonId();
            sizeRadioButton = (RadioButton) findViewById(selectedIdSiz);
            selectedSize = sizeRadioButton.getText().toString();
        }

        String val = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        uId = val.replaceAll("[\\D]", "");
        int qty = Integer.parseInt(binding.quantity.getText().toString());
        String serial = String.valueOf(productView.getProductId());

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.addToCart(serial, uId, selectedColor, selectedSize, qty, response -> {
            try {
                String status = response.getString(ConstantValues.MSG);
                String error = response.getString(ConstantValues.ERROR);
                if (error.equals("0") || error.equals("2")) {
                    progressBar.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                    View dialogView = this.getLayoutInflater().inflate(R.layout.layout_add_to_cart_item, viewGroup, false);
                    RelativeLayout ok = dialogView.findViewById(R.id.okBtnId);
                    RelativeLayout goToCart = dialogView.findViewById(R.id.goBtnId);
                    TextView msg = dialogView.findViewById(R.id.textMsg);
                    msg.setText(response.getString(ConstantValues.MSG));
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    ok.setOnClickListener(view1 -> {
                        alertDialog.dismiss();
                    });
                    goToCart.setOnClickListener(view1 -> {
                        Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                        intent.putExtra("fromCart", "cart");
                        startActivity(intent);
                    });
                    API api = ConstantValues.getAPI();
                    String uId;
                    @SuppressLint("HardwareIds")
                    String val1 = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                    uId = val1.replaceAll("[\\D]", "");
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.cartItems(uId, response1 -> {
                        try {
                            JSONArray categories = response1.getJSONArray("cartItems");
                            totalSelected2 = categories.length();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }));
                }
                Snackbar.make(findViewById(R.id.pr_details), status, Snackbar.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

}