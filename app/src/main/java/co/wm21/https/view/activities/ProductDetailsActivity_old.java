package co.wm21.https.view.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.FHelper.networks.Models.ProductDetails;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.FHelper.networks.Models.ProductReviewModel;
import co.wm21.https.FHelper.networks.Models.VendorDetailsModel;
import co.wm21.https.R;
import co.wm21.https.databinding.ActivityProductDetailsBinding;
import co.wm21.https.helpers.SessionHandler;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.AddToCartPresenter;
import co.wm21.https.presenter.CartItemListPresenter;
import co.wm21.https.presenter.ProductDetailsPresenter;
import co.wm21.https.presenter.ProductReviewListPresenter;
import co.wm21.https.presenter.RatingSubmitPresenter;
import co.wm21.https.presenter.RelatedProductListPresenter;
import co.wm21.https.presenter.VendorDetailsPresenter;
import co.wm21.https.presenter.VendorProductListPresenter;
import co.wm21.https.presenter.interfaces.OnAddToCartView;
import co.wm21.https.presenter.interfaces.OnCartItemListView;
import co.wm21.https.presenter.interfaces.OnProductDetailsView;
import co.wm21.https.presenter.interfaces.OnProductReviewListView;
import co.wm21.https.presenter.interfaces.OnRatingSubmitView;
import co.wm21.https.presenter.interfaces.OnRelatedProductListView;
import co.wm21.https.presenter.interfaces.OnVendorDetailsView;
import co.wm21.https.presenter.interfaces.OnVendorProductListView;
import co.wm21.https.utils.Constant;
import co.wm21.https.utils.SvgSoftwareLayerSetter;
import co.wm21.https.view.adapters.ProductReviewAdapter;
import co.wm21.https.view.adapters.product.ProductAdapter;

public class ProductDetailsActivity_old extends AppCompatActivity implements OnProductReviewListView, OnRatingSubmitView, OnProductDetailsView, OnVendorProductListView, OnRelatedProductListView, OnVendorDetailsView, OnAddToCartView, OnCartItemListView {
    private ActivityProductDetailsBinding binding;
    private ProductModel productView;
    API api;
    private String[] col;
    Handler mainHandler = new Handler();
    private List<String> colorList = new ArrayList<>();
    private  List<String> sizeList = new ArrayList<>();
    private ArrayList<ProductModel>vendorProductModelsList;
    private String colors = "", sizes = "", point = "";
    private User user;
    private RadioGroup colorsRadioGroup, sizeRadioGroup;
    private RadioButton colorsRadioButton, sizeRadioButton;
    private String venId = "";
    private String scat = "";
    private View cartView;
  // public ProductModel product;

    //private ProgressDialog progressBar;
    private ProgressDialog progressDialog;
    private  String uId = "";
    private  String selectedColor = "", selectedSize = "";
    public static int totalSelected2 = 0;
    private TextInputLayout review;
    boolean cusExpand = false, disExpand = false, venExpand = false;

    private SessionHandler sessionHandler;
    private ArrayList<ProductReviewModel>productReviewModelArrayList;
    private ArrayList<ProductModel>relatedProductsList;




    private  ProductReviewAdapter productReviewAdapter;
    private ProductAdapter productAdapter;
    private ProductAdapter relatedPrAdepter;
    private ProductReviewListPresenter productReviewListPresenter;
    private RatingSubmitPresenter ratingSubmitPresenter;
    private VendorProductListPresenter vendorProductListPresenter;
    private  RelatedProductListPresenter relatedProductListPresenter;
    private ProductDetailsPresenter productDetailsPresenter;
    private VendorDetailsPresenter vendorDetailsPresenter;
    private AddToCartPresenter addToCartPresenter;
    CartItemListPresenter cartItemListPresenter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = new User(getApplicationContext());


        sessionHandler = new SessionHandler(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.home_progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if (getIntent().getParcelableExtra(Constant.Product.PARCEL) != null) {
            productView = getIntent().getParcelableExtra(Constant.Product.PARCEL);
            binding.productName.setText(productView.getName());
            binding.productPrice.setText(String.format("৳ %s", productView.getSprice()));
            binding.productOldPrice.setText(String.format("৳ %s", productView.getPrice()));
        }
        //Picasso.get().load(ConstantValues.URL + "image/product/small/" + productView.getImg()).into(binding.productDisplayPhoto);
        /*if (productView.getImg().endsWith(".svg")) {
            // Replace ".svg" with ".png"
            productView.setImg(productView.getImg().replace(".svg", ".png"));
        }*/

        /*Glide.with(this)
                .asDrawable()
                .load(ConstantValues.URL + "image/product/small/" + productView.getImg())  // SVG URL
                .transition(DrawableTransitionOptions.withCrossFade()) // Optional: Fade transition
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // Cache strategy
                .into(binding.productDisplayPhoto);*/

      /*  ImageLoaderHelper.loadImage(
                this,                 // Context (Activity or Application)
                binding.productDisplayPhoto,            // ImageView where the image will be loaded
                ConstantValues.URL + "image/product/small/" + productView.getImg(),             // Image URL
                R.drawable.ic_image_temp,     // Placeholder image
                R.drawable.ic_information           // Error image
        );*/


        Glide.with(this)
                .as(PictureDrawable.class)
                .listener(new SvgSoftwareLayerSetter())
                .load( ConstantValues.imageURL + "image/product/app/" + productView.getImg())
                .into(binding.productDisplayPhoto);





        binding.productOldPrice.setPaintFlags(binding.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        binding.addToCardButton.setOnClickListener(this::addToCart);
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
        binding.reviewDialogBtn.setOnClickListener(this::reviewSubmit);
        binding.reviewRecView.setVisibility(View.GONE);
        binding.customerReview.setOnClickListener(this::expandLayout);
        binding.disExpandLayout.setVisibility(View.GONE);
        binding.disExpandButton.setOnClickListener(this::expandLayout);
        binding.venExpandLayout.setVisibility(View.GONE);
        binding.venExpandButton.setOnClickListener(this::expandLayout);
        binding.backBtn.setOnClickListener(view -> onBackPressed());
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
             //switchFragment(new AboutUsFragment(),"AboutUsFragment");
        });
        binding.footerId.contactUs.setOnClickListener(v -> {
            // switchFragment(new ContactUsFragment(),"ContactUsFragment");
        });

        binding.facebookId.setOnClickListener(this::socialMedia);
        binding.twitterId.setOnClickListener(this::socialMedia);
        binding.pinterestId.setOnClickListener(this::socialMedia);
        binding.whatsappId.setOnClickListener(this::socialMedia);
        binding.linkedInId.setOnClickListener(this::socialMedia);
        binding.favouriteId.setOnClickListener(this::socialMedia);
        binding.visitStore.setOnClickListener(view -> {

        });


        ratingSubmitPresenter=new RatingSubmitPresenter(this);
        productReviewListPresenter=new ProductReviewListPresenter(this);
        vendorProductListPresenter=new VendorProductListPresenter(this);
        relatedProductListPresenter=new RelatedProductListPresenter(this);
        productDetailsPresenter=new ProductDetailsPresenter(this);
        vendorDetailsPresenter=new VendorDetailsPresenter(this);
        addToCartPresenter=new AddToCartPresenter(this);
        cartItemListPresenter=new CartItemListPresenter(this);

        productDetails();
        customerReview();


    }

    private void productDetails() {
        if (productView.getSerial() != null) {
            productDetailsPresenter.onProductDetailsRequestData(productView.getSerial());

        }

    }


    private void customerReview() {
        productReviewModelArrayList=new ArrayList<>();
        productReviewAdapter= new ProductReviewAdapter(this, productReviewModelArrayList, R.layout.item_product_review);
        binding.reviewRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        binding.reviewRecView.setAdapter(productReviewAdapter);
        String id= productView.getSerial();
        productReviewListPresenter.ProductReviewDataLoad(id);



    }

    private void reviewSubmit(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.submit_review_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        RatingBar ratingBar2 = dialogView.findViewById(R.id.ratingBar);
        TextView ratingText = dialogView.findViewById(R.id.ratingText);
        TextView userName = dialogView.findViewById(R.id.username);
        TextView userNumber = dialogView.findViewById(R.id.userNumber);
        review = dialogView.findViewById(R.id.review);
        TextInputLayout mobileNum = dialogView.findViewById(R.id.mobileNumber);
        AppCompatButton cancel = dialogView.findViewById(R.id.cancelButton);
        AppCompatButton submitBtn = dialogView.findViewById(R.id.reviewSubmitButton);
        LinearLayout userLayout = dialogView.findViewById(R.id.userLayout);

        ratingBar2.setRating(5);
        ratingText.setText("5.0");


        ratingBar2.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            ratingText.setText(String.valueOf(rating));
        });
        submitBtn.setOnClickListener(view1 -> {
            String reviewTxt = review.getEditText().getText().toString();
            if (ratingBar2.getRating() > 0) {
                                                                                            //need product ID
              ratingSubmitPresenter.onRatingSubmitResponse(sessionHandler.getUserDetails().getUsername(), productView.getSerial(),String.valueOf(ratingBar2.getRating()),reviewTxt);
            } else {
                Toast.makeText(ProductDetailsActivity_old.this, "Rating Shouldn't be empty", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(view12 -> {
            alertDialog.dismiss();
        });
        if (user.getSession().isLoggedIn()) {
            userName.setText(user.getUsername());
            userNumber.setText(user.getMobile());
            mobileNum.setVisibility(View.GONE);
            userLayout.setVisibility(View.VISIBLE);


        } else {
            mobileNum.setVisibility(View.VISIBLE);
            userLayout.setVisibility(View.GONE);
        }

        alertDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    private void expandLayout(View view) {
        switch (view.getId()) {
            case R.id.customerReview:
                if (cusExpand) {
                    cusExpand = false;
                    binding.reviewRecView.setVisibility(View.GONE);
                    binding.customerReviewTxt.setTextColor(Color.parseColor("#000000"));
                    binding.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
                } else {
                    cusExpand = true;
                    binding.reviewRecView.setVisibility(View.VISIBLE);
                    binding.customerReviewTxt.setTextColor(Color.parseColor("#FE0000"));
                    binding.infExpandIcon.setImageResource(R.drawable.ic_arrow_down_red);
                }
                break;
            case R.id.disExpandButton:
                if (disExpand) {
                    disExpand = false;
                    binding.disExpandLayout.setVisibility(View.GONE);
                    binding.descriptionTxt.setTextColor(Color.parseColor("#000000"));
                    binding.disExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
                } else {
                    disExpand = true;
                    binding.disExpandLayout.setVisibility(View.VISIBLE);
                    binding.descriptionTxt.setTextColor(Color.parseColor("#FE0000"));
                    binding.disExpandIcon.setImageResource(R.drawable.ic_arrow_down_red);
                }
                break;
            case R.id.venExpandButton:
                if (venExpand) {
                    venExpand = false;
                    binding.venExpandLayout.setVisibility(View.GONE);
                    binding.vendorTxt.setTextColor(Color.parseColor("#000000"));
                    binding.venExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
                } else {
                    venExpand = true;
                    binding.venExpandLayout.setVisibility(View.VISIBLE);
                    binding.vendorTxt.setTextColor(Color.parseColor("#FE0000"));
                    binding.venExpandIcon.setImageResource(R.drawable.ic_arrow_down_red);
                }
                break;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onProductReviewListDataLoad(List<ProductReviewModel> productReviewModels) {
        productReviewModelArrayList.addAll(productReviewModels);
        productReviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onProductReviewListStartLoading() {
        progressDialog.show();
    }

    @Override
    public void onProductReviewListStopLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onProductReviewListShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRatingSubmitDataLoad(String responseMsg) {
        Toast.makeText(this, responseMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRatingSubmitStartLoading() {
        progressDialog.show();
    }

    @Override
    public void onRatingSubmitStopLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onRatingSubmitShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }




    @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
    private void getProductDetails() {

        if (!colors.equals("")) {
            binding.colorsLayout.setVisibility(View.VISIBLE);
            col = colors.split(",");
            colorList.addAll(Arrays.asList(col));

            if (!colorList.isEmpty()) {
                colorsRadioGroup = new RadioGroup(ProductDetailsActivity_old.this);
                colorsRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
                colorsRadioGroup.setPadding(10, 0, 0, 0);

                RadioGroup.LayoutParams layoutParams;
                for (int i = 0; i < colorList.size(); i++) {
                    colorsRadioButton = new RadioButton(ProductDetailsActivity_old.this);
                    colorsRadioButton.setText(colorList.get(i));
                    colorsRadioButton.setBackground(getResources().getDrawable(R.drawable.radio_selector));
                    colorsRadioButton.setButtonDrawable(getResources().getDrawable(R.color.transparent));
                    colorsRadioButton.setTextColor(getResources().getColor(R.drawable.radio_text_color_pd));
                    colorsRadioButton.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.radio_text_color_pd));
                    colorsRadioButton.setPadding(20, 12, 12, 20);
                    layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(15, 0, 0, 0);
                    colorsRadioGroup.addView(colorsRadioButton, layoutParams);
               /* binding.multiLineRadioGroupForColors.checkAt(0);
                binding.multiLineRadioGroupForColors.setMaxInRow(4);
                binding.multiLineRadioGroupForColors.addView(colorsRadioButton);*/

                    if (i == 0) {
                        colorsRadioButton.setChecked(true);
                        selectedColor = colorsRadioButton.getText().toString();
                    }

                }
                binding.colorGroup.addView(colorsRadioGroup);
            /*binding.multiLineRadioGroupForColors.setOnCheckedChangeListener((MultiLineRadioGroup.OnCheckedChangeListener) (group, button) -> {
                selectedColor.equals(button.getText().toString());
            });*/


          /* colorsRadioButton.setOnClickListener(view -> {
                RadioButton btn=findViewById(colorsRadioGroup.getCheckedRadioButtonId());
                selectedColor=btn.getText().toString();
            });*/
                colorsRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                    RadioButton btn = findViewById(i);
                    selectedColor = btn.getText().toString();
                });
            }
        } else {
            binding.colorsLayout.setVisibility(View.GONE);


        }

        if (!sizes.isEmpty()) {
            binding.sizeLayout.setVisibility(View.VISIBLE);
            String[] siz = sizes.split(",");
            sizeList.addAll(Arrays.asList(siz));
            if (!sizes.isEmpty()) {
                sizeRadioGroup = new RadioGroup(ProductDetailsActivity_old.this);
                sizeRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
                sizeRadioGroup.setPadding(10, 0, 0, 0);

                RadioGroup.LayoutParams layoutParams;
                for (int i = 0; i < sizeList.size(); i++) {
                    sizeRadioButton = new RadioButton(ProductDetailsActivity_old.this);
                    sizeRadioButton.setText(sizeList.get(i));
                    sizeRadioButton.setBackground(getResources().getDrawable(R.drawable.radio_selector));
                    sizeRadioButton.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.radio_text_color_pd));
                    sizeRadioButton.setButtonDrawable(getResources().getDrawable(R.color.transparent));
                    sizeRadioButton.setPadding(20, 12, 12, 20);
                    layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(15, 0, 0, 0);
                    sizeRadioGroup.addView(sizeRadioButton, layoutParams);
                /*binding.multiLineRadioGroupForSizes.checkAt(0);
                binding.multiLineRadioGroupForSizes.setMaxInRow(4);
                binding.multiLineRadioGroupForSizes.addView(sizeRadioButton, layoutParams);*/

                    if (i == 0) {
                        sizeRadioButton.setChecked(true);
                        selectedSize = sizeRadioButton.getText().toString();
                    }
                }
                binding.sizeGroup.addView(sizeRadioGroup);
          /*  binding.multiLineRadioGroupForSizes.setOnCheckedChangeListener((MultiLineRadioGroup.OnCheckedChangeListener) (group, button) -> {
                selectedSize.equals(button.getText().toString());
            });*/
                sizeRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                    RadioButton btn = findViewById(i);
                    selectedSize = btn.getText().toString();
                });


            }

        } else {
            binding.sizeLayout.setVisibility(View.GONE);

        }

        progressDialog.dismiss();


    }

    private void vendorProducts(String venId) {
        vendorProductModelsList=new ArrayList<>();
        if (!venId.isEmpty()) {
           productAdapter= new ProductAdapter(getApplicationContext(), vendorProductModelsList, R.layout.layout_item_product_for_horizontal).addOnClickListener((View, position2) -> {
                ProductModel productView = vendorProductModelsList.get(position2);
                startActivity(new Intent(ProductDetailsActivity_old.this, ProductDetailsActivity_old.class)
                        .putExtra(Constant.Product.PARCEL, productView));
            });
            binding.vendorProductRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.vendorProductRecyclerView.setAdapter(productAdapter);
            vendorProductListPresenter.VendorProductDataLoad(venId,"8");
        }
    }

   private void relatedProducts() {
       relatedProductsList=new ArrayList<>();
       relatedPrAdepter=  new ProductAdapter(this, relatedProductsList, R.layout.layout_item_product_for_horizontal).addOnClickListener((View, position2) -> {
           ProductModel productView = relatedProductsList.get(position2);
           startActivity(new Intent(ProductDetailsActivity_old.this, ProductDetailsActivity_old.class)
                   .putExtra(Constant.Product.PARCEL, productView));
       });
       binding.relatedProductRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
       binding.relatedProductRecyclerView.setAdapter(relatedPrAdepter);
        if (!scat.isEmpty()) {
            relatedProductListPresenter.RelatedProductDataLoad("8","0", scat, "0");

        }

    }


    @SuppressLint("HardwareIds")
    private void addToCart(View view) {
        cartView=view;
        progressDialog.show();
        String val = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        uId = val.replaceAll("[\\D]", "");
        int qty = Integer.parseInt(binding.quantity.getText().toString());
        String serial = String.valueOf(productView.getSerial());
        addToCartPresenter.AddToCartDataLoad(serial,uId,selectedColor,selectedSize,qty);


    }



    @SuppressLint({"UseCompatLoadingForDrawables", "NonConstantResourceId"})
    private void socialMedia(View view) {
        switch (view.getId()){
            case R.id.facebookId:
                binding.facebookId.setImageDrawable(getDrawable(R.drawable.facebook));
                binding.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw));
                binding.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw));
                binding.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw));
                binding.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw));
                binding.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw));
                break;
            case R.id.twitterId:
                binding.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw));
                binding.twitterId.setImageDrawable(getDrawable(R.drawable.twitter));
                binding.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw));
                binding.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw));
                binding.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw));
                binding.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw));
                break;
            case R.id.pinterestId:
                binding.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw));
                binding.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw));
                binding.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest));
                binding.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw));
                binding.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw));
                binding.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw));
                break;
            case R.id.whatsappId:
                binding.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw));
                binding.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw));
                binding.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw));
                binding.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp));
                binding.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw));
                binding.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw));
                break;
            case R.id.linkedInId:
                binding.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw));
                binding.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw));
                binding.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw));
                binding.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw));
                binding.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin));
                binding.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw));
                break;
            case R.id.favouriteId:
                binding.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw));
                binding.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw));
                binding.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw));
                binding.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw));
                binding.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw));
                binding.favouriteId.setImageDrawable(getDrawable(R.drawable.heart));
                break;
        }
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




    @Override
    public void onProductDetailsDataLoad(ProductDetails productDetails) {

        binding.productCategory.setText(productDetails.getCat_id());

        binding.rpPoint.setText(productDetails.getPoint());
        colors = productDetails.getColor();
        venId = productDetails.getUpload_by();
        sizes =productDetails.getSize();
        scat = productDetails.getScat_id();
        binding.prInfo.setText(productDetails.getInfo().isEmpty() ? "" : productDetails.getInfo());
        relatedProducts();
        getProductDetails();
        vendorProducts(venId);
        vendorDetailsPresenter.getVendorDetailsDataLoad(venId);

    }

    @Override
    public void onProductDetailsStartLoading() {
        progressDialog.show();
    }

    @Override
    public void onProductDetailsStopLoading() {
        progressDialog.dismiss();

    }

    @Override
    public void onProductDetailsShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onVendorProductListDataLoad(List<ProductModel> vendorProductModels) {
        vendorProductModelsList.addAll(vendorProductModels);
        productAdapter.notifyDataSetChanged();

    }

    @Override
    public void onVendorProductListStartLoading() {
        progressDialog.show();
        binding.vendorProductRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onVendorProductListStopLoading() {
        binding.shimmerVendorProduct.setVisibility(View.GONE);
        binding.vendorProductRecyclerView.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
    }

    @Override
    public void onVendorProductListShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onRelatedProductListDataLoad(List<ProductModel> relatedProductModelHeads) {
        relatedProductsList.addAll(relatedProductModelHeads);
        relatedPrAdepter.notifyDataSetChanged();
    }

    @Override
    public void onRelatedProductListStartLoading() {
        binding.relatedProductRecyclerView.setVisibility(View.GONE);
        progressDialog.show();
    }

    @Override
    public void onRelatedProductListStopLoading() {
        binding.shimmerRelatedProduct.setVisibility(View.GONE);
        binding.relatedProductRecyclerView.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
    }

    @Override
    public void onRelatedProductListShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVendorDetailsDataLoad(VendorDetailsModel vendorDetailsModels) {
        binding.venType.setText(vendorDetailsModels.getTypeName().equals("") ? "Unavailable" : vendorDetailsModels.getTypeName());
        binding.venName.setText(vendorDetailsModels.getName().equals("") ? "Unavailable" : vendorDetailsModels.getName());
        binding.venAddress.setText(vendorDetailsModels.getAddress().equals("") ? "Unavailable" : vendorDetailsModels.getAddress());
        binding.venNumber.setText(vendorDetailsModels.getNumber().equals("") ? "Unavailable" : vendorDetailsModels.getNumber());

    }

    @Override
    public void onVendorDetailsStartLoading() {
        progressDialog.show();
    }

    @Override
    public void onVendorDetailsStopLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onVendorDetailsShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddToCartDataLoad(AddToCartModel addToCartModel) {

        String status = addToCartModel.getData();
        String error = String.valueOf(addToCartModel.getError());
        if (error.equals("0") || error.equals("2")) {
            progressDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            ViewGroup viewGroup = cartView.findViewById(android.R.id.content);
            View dialogView = this.getLayoutInflater().inflate(R.layout.layout_add_to_cart_item, viewGroup, false);
            RelativeLayout ok = dialogView.findViewById(R.id.okBtnId);
            RelativeLayout goToCart = dialogView.findViewById(R.id.goBtnId);
            TextView msg = dialogView.findViewById(R.id.textMsg);
            msg.setText(status);
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            ok.setOnClickListener(view1 -> {
                alertDialog.dismiss();
            });
            goToCart.setOnClickListener(view1 -> {
                Intent intent = new Intent(ProductDetailsActivity_old.this, MainActivity.class);
                intent.putExtra("fromCart", "cart");
                startActivity(intent);
            });
            API api = ConstantValues.getAPI();
            String uId;
            @SuppressLint("HardwareIds")
            String val1 = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            uId = val1.replaceAll("[\\D]", "");
            cartItemListPresenter.CartItemDataLoad(uId);

        }
        Snackbar.make(findViewById(R.id.pr_details), status, Snackbar.LENGTH_SHORT).show();


    }

    @Override
    public void onAddToCartStartLoading() {
        progressDialog.show();
    }

    @Override
    public void onAddToCartStopLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onAddToCartShowMessage(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartItemListDataLoad(CartItemsHead cartItems) {
        totalSelected2 = cartItems.getData().size();
    }

    @Override
    public void onCartItemListStartLoading() {

    }

    @Override
    public void onCartItemListStopLoading() {

    }

    @Override
    public void onCartItemListShowMessage(String errmsg) {
        //Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }
}