package co.wm21.https.view.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.PictureDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import co.wm21.https.FHelper.API
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.AddToCartModel
import co.wm21.https.FHelper.networks.Models.CartItemsHead
import co.wm21.https.FHelper.networks.Models.ProductDetails
import co.wm21.https.FHelper.networks.Models.ProductModel
import co.wm21.https.FHelper.networks.Models.ProductReviewModel
import co.wm21.https.FHelper.networks.Models.VendorDetailsModel
import co.wm21.https.R
import co.wm21.https.databinding.ActivityProductDetailsBinding
import co.wm21.https.helpers.SessionHandler
import co.wm21.https.helpers.User
import co.wm21.https.presenter.AddToCartPresenter
import co.wm21.https.presenter.CartItemListPresenter
import co.wm21.https.presenter.ProductDetailsPresenter
import co.wm21.https.presenter.ProductReviewListPresenter
import co.wm21.https.presenter.RatingSubmitPresenter
import co.wm21.https.presenter.RelatedProductListPresenter
import co.wm21.https.presenter.VendorDetailsPresenter
import co.wm21.https.presenter.VendorProductListPresenter
import co.wm21.https.presenter.interfaces.OnAddToCartView
import co.wm21.https.presenter.interfaces.OnCartItemListView
import co.wm21.https.presenter.interfaces.OnProductDetailsView
import co.wm21.https.presenter.interfaces.OnProductReviewListView
import co.wm21.https.presenter.interfaces.OnRatingSubmitView
import co.wm21.https.presenter.interfaces.OnRelatedProductListView
import co.wm21.https.presenter.interfaces.OnVendorDetailsView
import co.wm21.https.presenter.interfaces.OnVendorProductListView
import co.wm21.https.utils.Constant
import co.wm21.https.utils.SvgSoftwareLayerSetter
import co.wm21.https.view.adapters.ProductReviewAdapter
import co.wm21.https.view.adapters.product.ProductAdapter
import coil.decode.SvgDecoder
import coil.load
import com.bumptech.glide.Glide
import com.google.android.gms.analytics.ecommerce.Product
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.util.Arrays

class ProductDetailsActivity : AppCompatActivity(), OnProductReviewListView, OnRatingSubmitView,
    OnProductDetailsView, OnVendorProductListView, OnRelatedProductListView, OnVendorDetailsView,
    OnAddToCartView, OnCartItemListView {
    private var binding: ActivityProductDetailsBinding? = null
    private var productView: ProductModel? = null
    var api: API? = null
    private lateinit var col: Array<String>
    var mainHandler: Handler = Handler()
    private val colorList: MutableList<String> = ArrayList()
    private val sizeList: MutableList<String> = ArrayList()
    private var vendorProductModelsList: ArrayList<ProductModel>? = null
    private var colors = ""
    private var sizes = ""
    private val point = ""
    private var user: User? = null
    private var colorsRadioGroup: RadioGroup? = null
    private var sizeRadioGroup: RadioGroup? = null
    private var colorsRadioButton: RadioButton? = null
    private var sizeRadioButton: RadioButton? = null
    private var venId = ""
    private var scat = ""
    private var cartView: View? = null

    // public ProductModel product;
    //private ProgressDialog progressBar;
    private var progressDialog: ProgressDialog? = null
    private var uId = ""
    private var selectedColor = ""
    private var selectedSize = ""
    private var review: TextInputLayout? = null
    var cusExpand: Boolean = false
    var disExpand: Boolean = false
    var venExpand: Boolean = false

    private var sessionHandler: SessionHandler? = null
    private var productReviewModelArrayList: ArrayList<ProductReviewModel>? = null
    private var relatedProductsList: ArrayList<ProductModel>? = null


    private var productReviewAdapter: ProductReviewAdapter? = null
    private var productAdapter: ProductAdapter? = null
    private var relatedPrAdepter: ProductAdapter? = null
    private var productReviewListPresenter: ProductReviewListPresenter? = null
    private var ratingSubmitPresenter: RatingSubmitPresenter? = null
    private var vendorProductListPresenter: VendorProductListPresenter? = null
    private var relatedProductListPresenter: RelatedProductListPresenter? = null
    private var productDetailsPresenter: ProductDetailsPresenter? = null
    private var vendorDetailsPresenter: VendorDetailsPresenter? = null
    private var addToCartPresenter: AddToCartPresenter? = null
    var cartItemListPresenter: CartItemListPresenter? = null


    @RequiresApi(api = Build.VERSION_CODES.M)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        user = User(applicationContext)


        sessionHandler = SessionHandler(this)
        progressDialog = ProgressDialog(this)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setContentView(R.layout.home_progress_dialog)
        progressDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        if (intent.getParcelableExtra<Parcelable?>("parcel") != null) {
            productView = intent.getParcelableExtra<ProductModel>("parcel")
            binding!!.productName.text = productView!!.name
            binding!!.productPrice.text = String.format("৳ %s", productView!!.sprice)
            binding!!.productOldPrice.text = String.format("৳ %s", productView!!.price)
        }




        binding?.productDisplayPhoto?.load(ConstantValues.imageURL + "image/product/small/" + productView?.img){
            decoderFactory(SvgDecoder.Factory())
        }


        binding!!.productOldPrice.paintFlags =
            binding!!.productOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        binding!!.addToCardButton.setOnClickListener { view: View -> this.addToCart(view) }
        binding!!.plusQty.setOnClickListener { view: View? ->
            val q = binding!!.quantity.text.toString()
            var qty = q.toInt()
            qty += 1
            binding!!.quantity.setText(qty.toString())
        }
        binding!!.minusQty.setOnClickListener { view: View? ->
            val q = binding!!.quantity.text.toString()
            var qty = q.toInt()
            if (qty > 1) qty -= 1
            binding!!.quantity.setText(qty.toString())
        }
        binding!!.reviewDialogBtn.setOnClickListener { view: View -> this.reviewSubmit(view) }
        binding!!.reviewRecView.visibility = View.GONE
        binding!!.customerReview.setOnClickListener { view: View -> this.expandLayout(view) }
        binding!!.disExpandLayout.visibility = View.GONE
        binding!!.disExpandButton.setOnClickListener { view: View -> this.expandLayout(view) }
        binding!!.venExpandLayout.visibility = View.GONE
        binding!!.venExpandButton.setOnClickListener { view: View -> this.expandLayout(view) }
        binding!!.backBtn.setOnClickListener { view: View? -> onBackPressed() }
        binding!!.footerId.MyAccExpandableLayout.collapse()
        binding!!.footerId.CustomerExpandableLayout.collapse()
        binding!!.footerId.infoExpandableLayout.collapse()
        binding!!.footerId.footerCompany.setOnClickListener { v: View? ->
            if (binding!!.footerId.infoExpandableLayout.isExpanded) {
                binding!!.footerId.infoExpandableLayout.collapse()
                binding!!.footerId.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24)
            } else {
                binding!!.footerId.infoExpandableLayout.expand()
                binding!!.footerId.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
        }
        binding!!.footerId.footerMyAccount.setOnClickListener { v: View? ->
            if (binding!!.footerId.MyAccExpandableLayout.isExpanded) {
                binding!!.footerId.MyAccExpandableLayout.collapse()
                binding!!.footerId.MyAccExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24)
            } else {
                binding!!.footerId.MyAccExpandableLayout.expand()
                binding!!.footerId.MyAccExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
        }
        binding!!.footerId.footerCustomer.setOnClickListener { v: View? ->
            if (binding!!.footerId.CustomerExpandableLayout.isExpanded) {
                binding!!.footerId.CustomerExpandableLayout.collapse()
                binding!!.footerId.CustomerExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24)
            } else {
                binding!!.footerId.CustomerExpandableLayout.expand()
                binding!!.footerId.CustomerExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
        }
        binding!!.footerId.aboutUs.setOnClickListener { v: View? -> }
        binding!!.footerId.contactUs.setOnClickListener { v: View? -> }

        binding!!.facebookId.setOnClickListener { view: View -> this.socialMedia(view) }
        binding!!.twitterId.setOnClickListener { view: View -> this.socialMedia(view) }
        binding!!.pinterestId.setOnClickListener { view: View -> this.socialMedia(view) }
        binding!!.whatsappId.setOnClickListener { view: View -> this.socialMedia(view) }
        binding!!.linkedInId.setOnClickListener { view: View -> this.socialMedia(view) }
        binding!!.favouriteId.setOnClickListener { view: View -> this.socialMedia(view) }
        binding!!.visitStore.setOnClickListener { view: View? -> }


        ratingSubmitPresenter = RatingSubmitPresenter(this)
        productReviewListPresenter = ProductReviewListPresenter(this)
        vendorProductListPresenter = VendorProductListPresenter(this)
        relatedProductListPresenter = RelatedProductListPresenter(this)
        productDetailsPresenter = ProductDetailsPresenter(this)
        vendorDetailsPresenter = VendorDetailsPresenter(this)
        addToCartPresenter = AddToCartPresenter(this)
        cartItemListPresenter = CartItemListPresenter(this)

        productDetails()
        customerReview()
    }

    private fun productDetails() {
        if (productView!!.serial != null) {
            productDetailsPresenter!!.onProductDetailsRequestData(productView!!.serial)
        }
    }


    private fun customerReview() {
        productReviewModelArrayList = ArrayList()
        productReviewAdapter =
            ProductReviewAdapter(this, productReviewModelArrayList, R.layout.item_product_review)
        binding!!.reviewRecView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        binding!!.reviewRecView.adapter = productReviewAdapter
        val id = productView!!.serial
        productReviewListPresenter!!.ProductReviewDataLoad(id)
    }

    private fun reviewSubmit(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.submit_review_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setView(dialogView)
        val alertDialog = dialogBuilder.create()
        val ratingBar2 = dialogView.findViewById<RatingBar>(R.id.ratingBar)
        val ratingText = dialogView.findViewById<TextView>(R.id.ratingText)
        val userName = dialogView.findViewById<TextView>(R.id.username)
        val userNumber = dialogView.findViewById<TextView>(R.id.userNumber)
        review = dialogView.findViewById(R.id.review)
        val mobileNum = dialogView.findViewById<TextInputLayout>(R.id.mobileNumber)
        val cancel = dialogView.findViewById<AppCompatButton>(R.id.cancelButton)
        val submitBtn = dialogView.findViewById<AppCompatButton>(R.id.reviewSubmitButton)
        val userLayout = dialogView.findViewById<LinearLayout>(R.id.userLayout)

        ratingBar2.rating = 5f
        ratingText.text = "5.0"


        ratingBar2.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar: RatingBar?, rating: Float, fromUser: Boolean ->
                ratingText.text = rating.toString()
            }
        submitBtn.setOnClickListener { view1: View? ->
            val reviewTxt = review?.getEditText()!!.text.toString()
            if (ratingBar2.rating > 0) {
                //need product ID
                ratingSubmitPresenter!!.onRatingSubmitResponse(
                    sessionHandler!!.userDetails.username,
                    productView!!.serial,
                    ratingBar2.rating.toString(),
                    reviewTxt
                )
            } else {
                Toast.makeText(
                    this@ProductDetailsActivity,
                    "Rating Shouldn't be empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        cancel.setOnClickListener { view12: View? ->
            alertDialog.dismiss()
        }
        if (user!!.session.isLoggedIn) {
            userName.text = user!!.username
            userNumber.text = user!!.mobile
            mobileNum.visibility = View.GONE
            userLayout.visibility = View.VISIBLE
        } else {
            mobileNum.visibility = View.VISIBLE
            userLayout.visibility = View.GONE
        }

        alertDialog.show()
    }

    @SuppressLint("NonConstantResourceId")
    private fun expandLayout(view: View) {
        when (view.id) {
            R.id.customerReview -> if (cusExpand) {
                cusExpand = false
                binding!!.reviewRecView.visibility = View.GONE
                binding!!.customerReviewTxt.setTextColor(Color.parseColor("#000000"))
                binding!!.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24)
            } else {
                cusExpand = true
                binding!!.reviewRecView.visibility = View.VISIBLE
                binding!!.customerReviewTxt.setTextColor(Color.parseColor("#FE0000"))
                binding!!.infExpandIcon.setImageResource(R.drawable.ic_arrow_down_red)
            }

            R.id.disExpandButton -> if (disExpand) {
                disExpand = false
                binding!!.disExpandLayout.visibility = View.GONE
                binding!!.descriptionTxt.setTextColor(Color.parseColor("#000000"))
                binding!!.disExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24)
            } else {
                disExpand = true
                binding!!.disExpandLayout.visibility = View.VISIBLE
                binding!!.descriptionTxt.setTextColor(Color.parseColor("#FE0000"))
                binding!!.disExpandIcon.setImageResource(R.drawable.ic_arrow_down_red)
            }

            R.id.venExpandButton -> if (venExpand) {
                venExpand = false
                binding!!.venExpandLayout.visibility = View.GONE
                binding!!.vendorTxt.setTextColor(Color.parseColor("#000000"))
                binding!!.venExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24)
            } else {
                venExpand = true
                binding!!.venExpandLayout.visibility = View.VISIBLE
                binding!!.vendorTxt.setTextColor(Color.parseColor("#FE0000"))
                binding!!.venExpandIcon.setImageResource(R.drawable.ic_arrow_down_red)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onProductReviewListDataLoad(productReviewModels: List<ProductReviewModel>) {
        productReviewModelArrayList!!.addAll(productReviewModels)
        productReviewAdapter!!.notifyDataSetChanged()
    }

    override fun onProductReviewListStartLoading() {
        progressDialog!!.show()
    }

    override fun onProductReviewListStopLoading() {
        progressDialog!!.dismiss()
    }

    override fun onProductReviewListShowMessage(errmsg: String) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onRatingSubmitDataLoad(responseMsg: String) {
        Toast.makeText(this, responseMsg, Toast.LENGTH_SHORT).show()
    }

    override fun onRatingSubmitStartLoading() {
        progressDialog!!.show()
    }

    override fun onRatingSubmitStopLoading() {
        progressDialog!!.dismiss()
    }

    override fun onRatingSubmitShowMessage(errmsg: String) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show()
    }


    @get:SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    private val productDetails: Unit
        get() {
            if (colors != "") {
                binding!!.colorsLayout.visibility = View.VISIBLE
                col = colors.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                colorList.addAll(Arrays.asList(*col))

                if (!colorList.isEmpty()) {
                    colorsRadioGroup = RadioGroup(this@ProductDetailsActivity)
                    colorsRadioGroup!!.orientation = LinearLayout.HORIZONTAL
                    colorsRadioGroup!!.setPadding(10, 0, 0, 0)

                    var layoutParams: RadioGroup.LayoutParams
                    for (i in colorList.indices) {
                        colorsRadioButton = RadioButton(this@ProductDetailsActivity)
                        colorsRadioButton!!.text = colorList[i]
                        colorsRadioButton!!.background =
                            resources.getDrawable(R.drawable.radio_selector)
                        colorsRadioButton!!.buttonDrawable =
                            resources.getDrawable(R.color.transparent)
                        colorsRadioButton!!.setTextColor(resources.getColor(R.drawable.radio_text_color_pd))
                        colorsRadioButton!!.setTextColor(
                            ContextCompat.getColorStateList(
                                applicationContext, R.drawable.radio_text_color_pd
                            )
                        )
                        colorsRadioButton!!.setPadding(20, 12, 12, 20)
                        layoutParams = RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.WRAP_CONTENT,
                            RadioGroup.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(15, 0, 0, 0)
                        colorsRadioGroup!!.addView(colorsRadioButton, layoutParams)

                        /* binding.multiLineRadioGroupForColors.checkAt(0);
                binding.multiLineRadioGroupForColors.setMaxInRow(4);
                binding.multiLineRadioGroupForColors.addView(colorsRadioButton);*/
                        if (i == 0) {
                            colorsRadioButton!!.isChecked = true
                            selectedColor = colorsRadioButton!!.text.toString()
                        }
                    }
                    binding!!.colorGroup.addView(colorsRadioGroup)


                    /*binding.multiLineRadioGroupForColors.setOnCheckedChangeListener((MultiLineRadioGroup.OnCheckedChangeListener) (group, button) -> {
                selectedColor.equals(button.getText().toString());
            });*/


                    /* colorsRadioButton.setOnClickListener(view -> {
                RadioButton btn=findViewById(colorsRadioGroup.getCheckedRadioButtonId());
                selectedColor=btn.getText().toString();
            });*/
                    colorsRadioGroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
                        val btn = findViewById<RadioButton>(i)
                        selectedColor = btn.text.toString()
                    }
                }
            } else {
                binding!!.colorsLayout.visibility = View.GONE
            }

            if (!sizes.isEmpty()) {
                binding!!.sizeLayout.visibility = View.VISIBLE
                val siz = sizes.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                sizeList.addAll(Arrays.asList(*siz))
                if (!sizes.isEmpty()) {
                    sizeRadioGroup = RadioGroup(this@ProductDetailsActivity)
                    sizeRadioGroup!!.orientation = LinearLayout.HORIZONTAL
                    sizeRadioGroup!!.setPadding(10, 0, 0, 0)

                    var layoutParams: RadioGroup.LayoutParams
                    for (i in sizeList.indices) {
                        sizeRadioButton = RadioButton(this@ProductDetailsActivity)
                        sizeRadioButton!!.text = sizeList[i]
                        sizeRadioButton!!.background =
                            resources.getDrawable(R.drawable.radio_selector)
                        sizeRadioButton!!.setTextColor(
                            ContextCompat.getColorStateList(
                                applicationContext, R.drawable.radio_text_color_pd
                            )
                        )
                        sizeRadioButton!!.buttonDrawable =
                            resources.getDrawable(R.color.transparent)
                        sizeRadioButton!!.setPadding(20, 12, 12, 20)
                        layoutParams = RadioGroup.LayoutParams(
                            RadioGroup.LayoutParams.WRAP_CONTENT,
                            RadioGroup.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(15, 0, 0, 0)
                        sizeRadioGroup!!.addView(sizeRadioButton, layoutParams)

                        /*binding.multiLineRadioGroupForSizes.checkAt(0);
                binding.multiLineRadioGroupForSizes.setMaxInRow(4);
                binding.multiLineRadioGroupForSizes.addView(sizeRadioButton, layoutParams);*/
                        if (i == 0) {
                            sizeRadioButton!!.isChecked = true
                            selectedSize = sizeRadioButton!!.text.toString()
                        }
                    }
                    binding!!.sizeGroup.addView(sizeRadioGroup)
                    /*  binding.multiLineRadioGroupForSizes.setOnCheckedChangeListener((MultiLineRadioGroup.OnCheckedChangeListener) (group, button) -> {
                selectedSize.equals(button.getText().toString());
            });*/
                    sizeRadioGroup!!.setOnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
                        val btn = findViewById<RadioButton>(i)
                        selectedSize = btn.text.toString()
                    }
                }
            } else {
                binding!!.sizeLayout.visibility = View.GONE
            }

            progressDialog!!.dismiss()
        }

    private fun vendorProducts(venId: String) {
        vendorProductModelsList = ArrayList()
        if (!venId.isEmpty()) {
            productAdapter = ProductAdapter(
                applicationContext,
                vendorProductModelsList!!,
                R.layout.layout_item_product_for_horizontal
            ).addOnClickListener { View: View?, position2: Int? ->
                val productView = vendorProductModelsList!![position2!!]
                startActivity(
                    Intent(this@ProductDetailsActivity, ProductDetailsActivity::class.java)
                        .putExtra("parcel", productView)
                )
            }
            binding!!.vendorProductRecyclerView.layoutManager = LinearLayoutManager(
                applicationContext, LinearLayoutManager.HORIZONTAL, false
            )
            binding!!.vendorProductRecyclerView.adapter = productAdapter
            vendorProductListPresenter!!.VendorProductDataLoad(venId, "8")
        }
    }

    private fun relatedProducts() {
        relatedProductsList = ArrayList()
        relatedPrAdepter = ProductAdapter(
            this,
            relatedProductsList!!,
            R.layout.layout_item_product_for_horizontal
        ).addOnClickListener { View: View?, position2: Int? ->
            val productView = relatedProductsList!![position2!!]
            startActivity(
                Intent(this@ProductDetailsActivity, ProductDetailsActivity::class.java)
                    .putExtra("parcel", productView)
            )
        }
        binding!!.relatedProductRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding!!.relatedProductRecyclerView.adapter = relatedPrAdepter
        if (!scat.isEmpty()) {
            relatedProductListPresenter!!.RelatedProductDataLoad("8", "0", scat, "0")
        }
    }


    @SuppressLint("HardwareIds")
    private fun addToCart(view: View) {
        cartView = view
        progressDialog!!.show()
        val `val` = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        uId = `val`.replace("[\\D]".toRegex(), "")
        val qty = binding!!.quantity.text.toString().toInt()
        val serial = productView!!.serial.toString()
        addToCartPresenter!!.AddToCartDataLoad(serial, uId, selectedColor, selectedSize, qty)
    }


    @SuppressLint("UseCompatLoadingForDrawables", "NonConstantResourceId")
    private fun socialMedia(view: View) {
        when (view.id) {
            R.id.facebookId -> {
                binding!!.facebookId.setImageDrawable(getDrawable(R.drawable.facebook))
                binding!!.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw))
                binding!!.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw))
                binding!!.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw))
                binding!!.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw))
                binding!!.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw))
            }

            R.id.twitterId -> {
                binding!!.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw))
                binding!!.twitterId.setImageDrawable(getDrawable(R.drawable.twitter))
                binding!!.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw))
                binding!!.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw))
                binding!!.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw))
                binding!!.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw))
            }

            R.id.pinterestId -> {
                binding!!.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw))
                binding!!.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw))
                binding!!.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest))
                binding!!.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw))
                binding!!.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw))
                binding!!.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw))
            }

            R.id.whatsappId -> {
                binding!!.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw))
                binding!!.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw))
                binding!!.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw))
                binding!!.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp))
                binding!!.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw))
                binding!!.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw))
            }

            R.id.linkedInId -> {
                binding!!.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw))
                binding!!.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw))
                binding!!.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw))
                binding!!.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw))
                binding!!.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin))
                binding!!.favouriteId.setImageDrawable(getDrawable(R.drawable.heart_bw))
            }

            R.id.favouriteId -> {
                binding!!.facebookId.setImageDrawable(getDrawable(R.drawable.facebook_bw))
                binding!!.twitterId.setImageDrawable(getDrawable(R.drawable.twitter_bw))
                binding!!.pinterestId.setImageDrawable(getDrawable(R.drawable.pinterest_bw))
                binding!!.whatsappId.setImageDrawable(getDrawable(R.drawable.whatsapp_bw))
                binding!!.linkedInId.setImageDrawable(getDrawable(R.drawable.linkedin_bw))
                binding!!.favouriteId.setImageDrawable(getDrawable(R.drawable.heart))
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


    override fun onProductDetailsDataLoad(productDetails: ProductDetails) {
        binding!!.productCategory.text = productDetails.cat_id
        binding!!.pModel.text = productDetails.model

        binding!!.rpPoint.text = productDetails.point
        colors = productDetails.color
        venId = productDetails.upload_by
        sizes = productDetails.size
        scat = productDetails.scat_id
        binding!!.prInfo.text = if (productDetails.info.isEmpty()) "" else productDetails.info
        relatedProducts()
        this.productDetails
        vendorProducts(venId)
        vendorDetailsPresenter!!.getVendorDetailsDataLoad(venId)
    }

    override fun onProductDetailsStartLoading() {
        progressDialog!!.show()
    }

    override fun onProductDetailsStopLoading() {
        progressDialog!!.dismiss()
    }

    override fun onProductDetailsShowMessage(errmsg: String) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onVendorProductListDataLoad(vendorProductModels: List<ProductModel>) {
        vendorProductModelsList!!.addAll(vendorProductModels)
        productAdapter!!.notifyDataSetChanged()
    }

    override fun onVendorProductListStartLoading() {
        progressDialog!!.show()
        binding!!.vendorProductRecyclerView.visibility = View.GONE
    }

    override fun onVendorProductListStopLoading() {
        binding!!.shimmerVendorProduct.visibility = View.GONE
        binding!!.vendorProductRecyclerView.visibility = View.VISIBLE
        progressDialog!!.dismiss()
    }

    override fun onVendorProductListShowMessage(errmsg: String) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRelatedProductListDataLoad(relatedProductModelHeads: List<ProductModel>) {
        relatedProductsList!!.addAll(relatedProductModelHeads)
        relatedPrAdepter!!.notifyDataSetChanged()
    }

    override fun onRelatedProductListStartLoading() {
        binding!!.relatedProductRecyclerView.visibility = View.GONE
        progressDialog!!.show()
    }

    override fun onRelatedProductListStopLoading() {
        binding!!.shimmerRelatedProduct.visibility = View.GONE
        binding!!.relatedProductRecyclerView.visibility = View.VISIBLE
        progressDialog!!.dismiss()
    }

    override fun onRelatedProductListShowMessage(errmsg: String) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onVendorDetailsDataLoad(vendorDetailsModels: VendorDetailsModel) {
        binding!!.venType.text =
            if (vendorDetailsModels.typeName == "") "Unavailable" else vendorDetailsModels.typeName
        binding!!.venName.text =
            if (vendorDetailsModels.name == "") "Unavailable" else vendorDetailsModels.name
        binding!!.venAddress.text =
            if (vendorDetailsModels.address == "") "Unavailable" else vendorDetailsModels.address
        binding!!.venNumber.text =
            if (vendorDetailsModels.number == "") "Unavailable" else vendorDetailsModels.number
    }

    override fun onVendorDetailsStartLoading() {
        progressDialog!!.show()
    }

    override fun onVendorDetailsStopLoading() {
        progressDialog!!.dismiss()
    }

    override fun onVendorDetailsShowMessage(errmsg: String) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onAddToCartDataLoad(addToCartModel: AddToCartModel) {
        val status = addToCartModel.data
        val error = addToCartModel.error.toString()
        if (error == "0" || error == "2") {
            progressDialog!!.dismiss()
            val builder = AlertDialog.Builder(this)
            val viewGroup = cartView!!.findViewById<ViewGroup>(android.R.id.content)
            val dialogView =
                this.layoutInflater.inflate(R.layout.layout_add_to_cart_item, viewGroup, false)
            val ok = dialogView.findViewById<RelativeLayout>(R.id.okBtnId)
            val goToCart = dialogView.findViewById<RelativeLayout>(R.id.goBtnId)
            val msg = dialogView.findViewById<TextView>(R.id.textMsg)
            msg.text = status
            builder.setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.show()
            ok.setOnClickListener { view1: View? ->
                alertDialog.dismiss()
            }
            goToCart.setOnClickListener { view1: View? ->
                val intent = Intent(this@ProductDetailsActivity, MainActivity::class.java)
                intent.putExtra("fromCart", "cart")
                startActivity(intent)
            }
            val api = ConstantValues.getAPI()
            val uId: String
            @SuppressLint("HardwareIds") val val1 = Settings.Secure.getString(
                contentResolver, Settings.Secure.ANDROID_ID
            )
            uId = val1.replace("[\\D]".toRegex(), "")
            cartItemListPresenter!!.CartItemDataLoad(uId)
        }
        Snackbar.make(findViewById(R.id.pr_details), status, Snackbar.LENGTH_SHORT).show()
    }

    override fun onAddToCartStartLoading() {
        progressDialog!!.show()
    }

    override fun onAddToCartStopLoading() {
        progressDialog!!.dismiss()
    }

    override fun onAddToCartShowMessage(errmsg: String) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onCartItemListDataLoad(cartItems: CartItemsHead) {
        totalSelected2 = cartItems.data.size
    }

    override fun onCartItemListStartLoading() {
    }

    override fun onCartItemListStopLoading() {
    }

    override fun onCartItemListShowMessage(errmsg: String) {
        //Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmField
        var totalSelected2: Int = 0
    }
}