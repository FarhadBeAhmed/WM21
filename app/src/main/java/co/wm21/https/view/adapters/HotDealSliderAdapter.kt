package co.wm21.https.view.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.AddToCartModel
import co.wm21.https.FHelper.networks.Models.ProductModel
import co.wm21.https.R
import co.wm21.https.helpers.SessionHandler
import co.wm21.https.presenter.AddToCartPresenter
import co.wm21.https.presenter.interfaces.OnAddToCartView
import co.wm21.https.utils.dialog.LoadingDialog
import co.wm21.https.view.activities.MainActivity
import co.wm21.https.view.activities.ProductDetailsActivity
import co.wm21.https.view.fragments.CartFragment
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.smarteist.autoimageslider.SliderViewAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

class HotDealSliderAdapter : SliderViewAdapter<HotDealSliderAdapter.HotDealViewHolder>,
    OnAddToCartView {
    var context: Context
    var productList: List<ProductModel>
    private val mInflater: LayoutInflater

    @LayoutRes
    var itemRes: Int
    var listener: ItemClickListener? = null
    var sessionHandler: SessionHandler? = null
    var pos: Int = 0
    private var viewPager2: ViewPager2? = null
    var product: ProductModel? = null
    private var addToCartPresenter: AddToCartPresenter? = null

    var loadingDialog: LoadingDialog? = null
    var temp: Int = 0
    var clickView: View? = null
    private val selectedColor = ""
    private val selectedSize = ""
    private val mHandler = Handler()
    private val lstHolders: List<HotDealViewHolder> = ArrayList()

    private val updateRemainingTimeRunnable = Runnable {
        synchronized(lstHolders) {
            val currentTime = System.currentTimeMillis()
            for (holder in lstHolders) {
                holder.updateTimeRemaining(currentTime)
            }
        }
    }

    constructor(context: Context, productList: List<ProductModel>, itemRes: Int) {
        this.context = context
        this.productList = productList
        this.itemRes = itemRes
        mInflater = LayoutInflater.from(context)
        startUpdateTimer()
    }

    constructor(
        context: Context,
        productList: List<ProductModel>,
        itemRes: Int,
        viewPager2: ViewPager2?
    ) {
        this.context = context
        this.productList = productList
        this.viewPager2 = viewPager2
        this.itemRes = itemRes
        mInflater = LayoutInflater.from(context)
    }

    private fun startUpdateTimer() {
        val tmr = Timer()
        tmr.schedule(object : TimerTask() {
            override fun run() {
                mHandler.post(updateRemainingTimeRunnable)
            }
        }, 1000, 1000)
    }

    override fun getPageWidth(position: Int): Float {
        return (0.49f)
    }

    override fun onCreateViewHolder(parent: ViewGroup): HotDealViewHolder {
        return HotDealViewHolder(mInflater.inflate(itemRes, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HotDealViewHolder, position: Int) {
        sessionHandler = SessionHandler(context)
        pos = position
        product = productList[position]
        holder.productName.text = product!!.name
        holder.previousPrice.text = String.format("৳ %s", product!!.sprice)
        holder.productPrice.text = String.format("৳ %s", product!!.price)
        if (sessionHandler!!.isLoggedIn) {
            holder.rpLayout.visibility = View.VISIBLE
            holder.eshopTV.visibility = View.GONE
            holder.productRPTV.text = String.format("%s", product!!.point)
        } else {
            holder.rpLayout.visibility = View.GONE
            holder.eshopTV.visibility = View.VISIBLE
        }
        holder.shopName.text = "(" + product!!.uploadBy + ")"

        val discount = product!!.discount?.toDouble()?:0.0
        val price = product!!.price?.toDouble()?:0.0
        if (discount != 0.0) {
            holder.offerLayout.visibility = View.VISIBLE
            val dis = Math.round((discount / price) * 100).toDouble()
            holder.offerPercent.text = String.format("%s", dis)
        } else {
            holder.offerLayout.visibility = View.GONE
        }

        //Picasso.get().load(ConstantValues.URL+"image/product/small/"+product.getImg()).into(holder.productImage);
        //Picasso.get().load(ConstantValues.URL+"image/product/small/"+product.getImg()).into(holder.productImage);

        /* Glide.with(context)
                .asDrawable()
                .load(ConstantValues.URL + "image/product/small/" + product.getImg())  // SVG URL
                .transition(DrawableTransitionOptions.withCrossFade()) // Optional: Fade transition
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // Cache strategy
                .into(holder.productImage);*/
        holder.productImage.load(ConstantValues.imageURL + "image/product/small/" + product!!.img) {
            decoderFactory(SvgDecoder.Factory()) // Corrected method
        }



        //holder.productImage.setImageDrawable(product.getImg());
        holder.layoutBtn.setOnClickListener { view: View? ->
            Log.d("pp", "product: " + product!!.name)
            context.startActivity(
                Intent(context, ProductDetailsActivity::class.java)
                    .putExtra(ConstantValues.Product.PARCEL, productList[position])
            )
        }
        holder.btnAddToCart.setOnClickListener { view: View? ->
            addToCartPresenter = AddToCartPresenter(this)
            loadingDialog = LoadingDialog(context as Activity)
            @SuppressLint("HardwareIds") val `val` = Settings.Secure.getString(
                (context as FragmentActivity).contentResolver,
                Settings.Secure.ANDROID_ID
            )
            val uId = `val`.replace("[\\D]".toRegex(), "")
            val qty = 1
            clickView = view
            addToCartPresenter!!.AddToCartDataLoad(
                productList[position].serial.toString(),
                uId,
                selectedColor,
                selectedSize,
                qty
            )
            Log.d("pp", "product: " + productList[position].name)
        }

        holder.setData(product)
    }


    override fun getCount(): Int {
        return productList.size
    }

    fun setOnClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    fun addOnClickListener(listener: ItemClickListener?): HotDealSliderAdapter {
        this.listener = listener
        return this
    }

    override fun onAddToCartDataLoad(addToCartModel: AddToCartModel) {
        val status = addToCartModel.data
        val error = addToCartModel.error.toString()
        if (error == "0" || error == "2") {
            val builder = AlertDialog.Builder(context)
            val viewGroup = clickView!!.findViewById<ViewGroup>(android.R.id.content)
            val dialogView = (context as FragmentActivity).layoutInflater.inflate(
                R.layout.layout_add_to_cart_item,
                viewGroup,
                false
            )
            val ok = dialogView.findViewById<RelativeLayout>(R.id.okBtnId)
            val goToCart = dialogView.findViewById<RelativeLayout>(R.id.goBtnId)
            val msg = dialogView.findViewById<TextView>(R.id.textMsg)
            msg.text = addToCartModel.data
            builder.setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.show()
            ok.setOnClickListener { view1: View? ->
                alertDialog.dismiss()
            }
            goToCart.setOnClickListener { view1: View? ->
                alertDialog.dismiss()
                (context as MainActivity).switchFragment(CartFragment(), "CartFragment")
            }

            (context as MainActivity).getCartItems()
        }
        Snackbar.make(
            (context as MainActivity).findViewById(R.id.fragmentContainer),
            status,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onAddToCartStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onAddToCartStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onAddToCartShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }


    inner class HotDealViewHolder(itemView: View) : ViewHolder(itemView) {
        var productImage: ImageView = itemView.findViewById(R.id.h_productImage)
        var productName: TextView = itemView.findViewById(R.id.h_productName)
        var productPrice: TextView = itemView.findViewById(R.id.h_productPrice)
        var previousPrice: TextView = itemView.findViewById(R.id.h_previousPrice)
        var btnAddToCart: TextView = itemView.findViewById(R.id.h_addToCart)
        var productRPTV: TextView = itemView.findViewById(R.id.productRP)
        var shopName: TextView = itemView.findViewById(R.id.shopNameTV)
        var eshopTV: TextView = itemView.findViewById(R.id.eshopTV)
        var productRPLayout: LinearLayout = itemView.findViewById(R.id.productRPLayout)
        var layoutBtn: LinearLayout = itemView.findViewById(R.id.LayoutBtn)
        var day: TextView = itemView.findViewById(R.id.daysId)
        var hour: TextView = itemView.findViewById(R.id.hourId)
        var min: TextView = itemView.findViewById(R.id.minID)
        var sec: TextView = itemView.findViewById(R.id.secID)
        var offerPercent: TextView = itemView.findViewById(R.id.offerPercent)
        var button: RelativeLayout = itemView.findViewById(R.id.h_prButton)
        var hasDate: LinearLayout = itemView.findViewById(R.id.hasDates)
        var expiredDate: LinearLayout = itemView.findViewById(R.id.dateExpired)
        var offerLayout: LinearLayout = itemView.findViewById(R.id.offerLayout)
        var rpLayout: LinearLayout = itemView.findViewById(R.id.rpLayout)
        var mProduct: ProductModel? = null
        var addToCartBtn: TextView? = null


        fun setData(item: ProductModel?) {
            mProduct = item
            updateTimeRemaining(System.currentTimeMillis())
        }

        @SuppressLint("SetTextI18n")
        fun updateTimeRemaining(currentTime: Long) {
            @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            try {
                var days=0
                var hours=0
                var minutes=0
                var seconds=0
                val date2 = simpleDateFormat.parse(mProduct!!.offerDate.toString())
                val offerDate = Calendar.getInstance()
                if (date2 != null) {
                    offerDate.time = date2
                }
                val timeDiff = offerDate.timeInMillis - currentTime
                if (timeDiff > 0) {
                     days = TimeUnit.MILLISECONDS.toDays(timeDiff).toInt()
                     hours =
                        ((TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(days.toLong())).toInt())
                     minutes =
                        ((TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.DAYS.toHours(days.toLong()) + hours
                        )).toInt())
                     seconds =
                        ((TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(
                            minutes + TimeUnit.DAYS.toMinutes(days.toLong()) + TimeUnit.HOURS.toMinutes(
                                hours.toLong()
                            )
                        )).toInt())
                    day.text = days.toString() + ""
                    hour.text = hours.toString() + ""
                    min.text = minutes.toString() + ""
                    sec.text = seconds.toString() + ""
                    expiredDate.visibility = View.GONE
                    hasDate.visibility = View.VISIBLE
                } else {


                    day.text = "0"
                    hour.text = "00"
                    min.text = "00"
                    sec.text = "00"
                    expiredDate.visibility = View.GONE
                    hasDate.visibility = View.VISIBLE
                    //expiredDate..setText("Expired!!");
                    //expiredDate.visibility = View.VISIBLE
                    //hasDate.visibility = View.GONE
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }


        init {
            previousPrice.paintFlags = previousPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            if (listener != null) {
                itemView.setOnClickListener { v: View? -> listener!!.OnClick(v, pos) }
            }
        }
    }
}
