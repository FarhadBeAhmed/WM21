package co.wm21.https.view.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.AddToCartModel
import co.wm21.https.FHelper.networks.Models.PremierShopData
import co.wm21.https.FHelper.networks.Models.PremierShopViewResponse
import co.wm21.https.FHelper.networks.Models.ProductModel
import co.wm21.https.FHelper.networks.Models.ShopInfo
import co.wm21.https.R
import co.wm21.https.databinding.ActivityPremierShopViewBinding
import co.wm21.https.helpers.SessionHandler
import co.wm21.https.helpers.User
import co.wm21.https.presenter.AddToCartPresenter
import co.wm21.https.presenter.PremierShopViewPresenter
import co.wm21.https.presenter.interfaces.OnAddToCartView
import co.wm21.https.presenter.interfaces.OnPremierShopDetailView
import co.wm21.https.utils.CheckInternetConnection
import co.wm21.https.utils.Constant
import co.wm21.https.utils.dialog.LoadingDialog
import co.wm21.https.view.adapters.product.ProductAdapterNew
import co.wm21.https.view.fragments.CartFragment
import coil.decode.SvgDecoder
import coil.load
import com.github.aakira.expandablelayout.ExpandableLayout
import com.google.android.material.snackbar.Snackbar

class PremierShopViewActivity : AppCompatActivity(), OnPremierShopDetailView ,ProductAdapterNew.OnProductClickListener, OnAddToCartView{

    private lateinit var binding: ActivityPremierShopViewBinding
    private lateinit var presenter: PremierShopViewPresenter
    private lateinit var appSessionManager: SessionHandler
    private lateinit var checkInternetConnection: CheckInternetConnection
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var user: User
    private var premierShopViewResponse: PremierShopViewResponse? = null
    private var shopInfo: ShopInfo? = null
    private var products: ArrayList<ProductModel> = ArrayList()
    private lateinit var shopModel: PremierShopData
    private var addToCartPresenter: AddToCartPresenter? = null
    private lateinit var adapter:ProductAdapterNew
    private val selectedColor = ""
    private val selectedSize = ""

    var clickView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up View Binding
        binding = ActivityPremierShopViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Components
        appSessionManager = SessionHandler(this)
        user=User(this)
        checkInternetConnection = CheckInternetConnection()
        loadingDialog = LoadingDialog(this)
        // Initialize Presenter
        presenter = PremierShopViewPresenter(this)
        shopModel= intent.getParcelableExtra("shopModel")!!


        setupFooterExpandableLayouts()


        // Load data
        loadData()

        // Handle item click

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        // Show shimmer and hide the RecyclerView
        binding.productTelRecyclerView.isNestedScrollingEnabled = false
        binding.productTelRecyclerView.visibility = View.GONE
        binding.shimmerProduct.visibility = View.VISIBLE

        // Initialize adapter before setting it to RecyclerView
        adapter = ProductAdapterNew(this, products, R.layout.layout_item_product)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this,2)
        binding.productTelRecyclerView.layoutManager = layoutManager
        binding.productTelRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.productTelRecyclerView.adapter = adapter
        adapter.setOnProductClickListener(this)

        // Check internet connection
        if (checkInternetConnection.isInternetAvailable(this)) {
            presenter.onPremierShopViewResponseData(
                user.username,
                shopModel.shopId,
                shopModel.shopTypeId
            )
        } else {
            // Display Snackbar for no internet connection
            val snackbar = Snackbar.make(
                this.window.decorView.rootView,
                getString(R.string.no_internet_connection), // Use strings.xml
                Snackbar.LENGTH_INDEFINITE
            ).setAction(getString(R.string.retry)) { loadData() } // Use strings.xml

            snackbar.setActionTextColor(Color.RED)

            val sbView = snackbar.view
            val textView =
                sbView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.YELLOW)
            snackbar.show()
        }
    }


    private fun setupFooterExpandableLayouts() {
        // Collapse all expandable layouts initially
        binding.footerId.MyAccExpandableLayout.collapse()
        binding.footerId.CustomerExpandableLayout.collapse()
        binding.footerId.infoExpandableLayout.collapse()

        // Set up listeners for expandable sections
        setupExpandableToggle(
            binding.footerId.footerCompany,
            binding.footerId.infoExpandableLayout,
            binding.footerId.infExpandIcon
        )

        setupExpandableToggle(
            binding.footerId.footerMyAccount,
            binding.footerId.MyAccExpandableLayout,
            binding.footerId.MyAccExpandIcon
        )

        setupExpandableToggle(
            binding.footerId.footerCustomer,
            binding.footerId.CustomerExpandableLayout,
            binding.footerId.CustomerExpandIcon
        )

        // Set up listeners for static actions
        binding.footerId.aboutUs.setOnClickListener { /* Add action */ }
        binding.footerId.contactUs.setOnClickListener { /* Add action */ }
    }

    private fun setupExpandableToggle(
        clickableView: View,
        expandableLayout: ExpandableLayout,
        iconView: ImageView
    ) {
        clickableView.setOnClickListener {
            if (expandableLayout.isExpanded) {
                expandableLayout.collapse()
                iconView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24)
            } else {
                expandableLayout.expand()
                iconView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onPremierShopDetailLoadSuccess(response: PremierShopViewResponse?) {
        if (response == null) return // Early return if response is null
        Log.d("APIResponse", "Response: ${response?.data?.products}")

        // Show RecyclerView and hide shimmer
        binding.productTelRecyclerView.visibility = View.VISIBLE
        binding.shimmerProduct.visibility = View.GONE

        // Update premierShopViewResponse
        premierShopViewResponse = response


        // Update shop info
        binding.shopName.text = response.data?.shopInfo?.shopName ?: "N/A"
        binding.mobileNumber.text = response.data?.shopInfo?.phone ?: "N/A"
        binding.address.text = response.data?.shopInfo?.address ?: "N/A"
        binding.image.load(ConstantValues.imageURL + "shop/img/shop/" + premierShopViewResponse!!.data.shopInfo.smallImg) {
            decoderFactory(SvgDecoder.Factory()) // Corrected method
        }
        binding.shopCoverPhoto.load(ConstantValues.imageURL + "shop/img/shop/" + premierShopViewResponse!!.data.shopInfo.shopImage) {
            decoderFactory(SvgDecoder.Factory()) // Corrected method
        }


        // Update product list
       updateProducts(response.data.products)
    }
    fun updateProducts(newProducts: List<ProductModel>) {
        products.clear()
        products.addAll(newProducts)
        adapter.notifyDataSetChanged()
    }

    override fun onStartLoading() {
       loadingDialog.startLoadingAlertDialog()
    }

    override fun onStopLoading() {
       loadingDialog.dismissDialog()
    }

    override fun onError(errmsg: String?) {
        loadingDialog.dismissDialog()
    }

    override fun onProductClick(product: ProductModel, position: Int) {
        val productView = products[position]
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra(ConstantValues.Product.PARCEL, productView)
        startActivityForResult(intent, 2)

    }

    override fun onAddToCartClick(product: ProductModel, position: Int) {
        loadingDialog = LoadingDialog(this as Activity)
        addToCartPresenter = AddToCartPresenter(this)
        @SuppressLint("HardwareIds") val `val` = Settings.Secure.getString(
            (this as FragmentActivity).contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val uId = `val`.replace("[\\D]".toRegex(), "")
        val qty = 1
        addToCartPresenter!!.AddToCartDataLoad(
            product.serial.toString(),
            uId,
            selectedColor,
            selectedSize,
            qty
        )
    }

    override fun onAddToCartDataLoad(addToCartModel: AddToCartModel?) {
        val status = addToCartModel?.data
        val error = addToCartModel?.error.toString()

        if (error == "0" || error == "2") {
            val builder = AlertDialog.Builder(this)
            val viewGroup = findViewById<ViewGroup>(android.R.id.content)
            val dialogView = layoutInflater.inflate(
                R.layout.layout_add_to_cart_item,
                viewGroup,
                false
            )
            val ok = dialogView.findViewById<RelativeLayout>(R.id.okBtnId)
            val goToCart = dialogView.findViewById<RelativeLayout>(R.id.goBtnId)
            val msg = dialogView.findViewById<TextView>(R.id.textMsg)

            // Ensure message is only set if addToCartModel is non-null
            addToCartModel?.data?.let {
                msg.text = it
            }

            builder.setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.show()

            ok.setOnClickListener {
                alertDialog.dismiss()
            }

            goToCart.setOnClickListener {
                alertDialog.dismiss()

                // Use an Intent to start MainActivity and pass data if necessary
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("fromCart", "cart") // If you want to pass any data
                startActivity(intent)

                // You can finish the current activity if needed
                finish()
            }


            // Assuming getCartItems() is part of MainActivity, call it properly
           // (MainActivity).getCartItems()
        }

        if (status != null) {
            Snackbar.make(
                findViewById(android.R.id.content),  // Use the root view of the Activity
                status,
                Snackbar.LENGTH_SHORT
            ).show()
        };

    }



    override fun onAddToCartStartLoading() {
       loadingDialog.startLoadingAlertDialog()
    }

    override fun onAddToCartStopLoading() {
       loadingDialog.dismissDialog()
    }

    override fun onAddToCartShowMessage(errmsg: String?) {
        loadingDialog.dismissDialog()
    }
}