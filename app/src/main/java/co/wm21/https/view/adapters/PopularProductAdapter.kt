package co.wm21.https.view.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Paint
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.AddToCartModel
import co.wm21.https.FHelper.networks.Models.ProductModel
import co.wm21.https.R
import co.wm21.https.helpers.SessionHandler
import co.wm21.https.presenter.AddToCartPresenter
import co.wm21.https.presenter.interfaces.OnAddToCartView
import co.wm21.https.utils.dialog.LoadingDialog
import co.wm21.https.view.activities.MainActivity
import co.wm21.https.view.fragments.CartFragment
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.snackbar.Snackbar

class PopularProductAdapter(
    private val productList: ArrayList<ProductModel>,
    private val context: Context
) : RecyclerView.Adapter<PopularProductAdapter.ProductViewHolder>(), OnAddToCartView {
    var listener: ItemClickListener? = null
    var sessionHandler: SessionHandler? = null
    private var addToCartPresenter: AddToCartPresenter? = null
    var clickView: View? = null

    //private ProgressDialog progressDialog;
    var loadingDialog: LoadingDialog? = null
    var progressBar: ProgressDialog? = null
    private val selectedColor = ""
    private val selectedSize = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.layout_item_product, parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        sessionHandler = SessionHandler(context)
        holder.disLayout.visibility = View.GONE
        if (sessionHandler!!.isLoggedIn) {
            holder.rpLayout.visibility = View.VISIBLE
            holder.eshopTV.visibility = View.GONE
            holder.productRPTV.text = String.format("%s", product.point)
        } else {
            holder.rpLayout.visibility = View.GONE
            holder.eshopTV.visibility = View.VISIBLE
        }
        val discount = product.discount?.toDouble()?:0.0
        val sPrice = product.sprice?.toDouble()?:0.0

        if (Math.round(discount / sPrice * 100) > .49) {
            holder.disLayout.visibility = View.VISIBLE
            holder.discountText.text = Math.round(discount / sPrice * 100).toString()
        }

        holder.shopName.text = "(" + product.uploadBy + ")"
        holder.productName.text = product.name
        holder.previousPrice.text = String.format("৳ %s", product.sprice)
        holder.productPrice.text = String.format("৳ %s", product.price)

        holder.productImage.load(ConstantValues.imageURL + "image/product/small/" + product.img){
            decoderFactory(SvgDecoder.Factory())
        }

        holder.btnAddToCart.setOnClickListener { view: View? ->
            loadingDialog = LoadingDialog(context as Activity)
            addToCartPresenter = AddToCartPresenter(this)
            @SuppressLint("HardwareIds") val `val` = Settings.Secure.getString(
                (context as FragmentActivity).contentResolver,
                Settings.Secure.ANDROID_ID
            )
            val uId = `val`.replace("[\\D]".toRegex(), "")
            val qty = 1
            clickView = view
            addToCartPresenter!!.AddToCartDataLoad(
                product.serial.toString(),
                uId,
                selectedColor,
                selectedSize,
                qty
            )
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setOnClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    fun addOnClickListener(listener: ItemClickListener?): PopularProductAdapter {
        this.listener = listener
        return this
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImage: ImageView = itemView.findViewById(R.id.productImage)
        var productName: TextView = itemView.findViewById(R.id.productName)
        var productPrice: TextView = itemView.findViewById(R.id.productPrice)
        var previousPrice: TextView = itemView.findViewById(R.id.previousPrice)
        var btnAddToCart: TextView = itemView.findViewById(R.id.addToCart)
        var productRPTV: TextView = itemView.findViewById(R.id.productRP)
        var shopName: TextView = itemView.findViewById(R.id.shopNameTV)
        var eshopTV: TextView = itemView.findViewById(R.id.eshopTV)
        var productRPLayout: LinearLayout = itemView.findViewById(R.id.productRPLayout)
        var rpLayout: LinearLayout = itemView.findViewById(R.id.rpLayout)
        var discountText: TextView = itemView.findViewById(R.id.offerPercent)
        var disLayout: LinearLayout = itemView.findViewById(R.id.offerLayout)

        init {
            previousPrice.paintFlags = previousPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            // to remove "previousPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)"
            if (listener != null) itemView.setOnClickListener { v: View? ->
                listener!!.OnClick(
                    v,
                    adapterPosition
                )
            }
        }
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
        //Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }
}
