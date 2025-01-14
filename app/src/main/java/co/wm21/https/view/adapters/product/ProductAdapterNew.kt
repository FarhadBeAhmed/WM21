package co.wm21.https.view.adapters.product

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.AddToCartModel
import co.wm21.https.FHelper.networks.Models.ProductModel
import co.wm21.https.R
import co.wm21.https.databinding.LayoutItemProductBinding
import co.wm21.https.helpers.SessionHandler
import co.wm21.https.view.activities.MainActivity
import co.wm21.https.view.fragments.CartFragment
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.snackbar.Snackbar

class ProductAdapterNew(
    private val context: Context,
    private val productList: ArrayList<ProductModel>,
    @LayoutRes private val layout: Int
) : RecyclerView.Adapter<ProductAdapterNew.ProductViewHolder>() {

    // Listener for handling clicks
    private var listener: OnProductClickListener? = null
    private lateinit var binding: LayoutItemProductBinding
    var sessionHandler: SessionHandler? = null


    var clickView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = LayoutItemProductBinding.inflate(LayoutInflater.from(context), parent, false
        )
        return ProductViewHolder(binding)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        // Bind product data to the views
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
        val discount = product.discount?.toDouble() ?: 0.0
        val sPrice = product.sprice?.toDouble() ?: 0.0

        if (Math.round(discount / sPrice * 100) > .49) {
            holder.disLayout.visibility = View.VISIBLE
            holder.discountText.text = Math.round(discount / sPrice * 100).toString()
        }

        holder.shopName.text = "(" + product.uploadBy + ")"
        holder.productName.text = product.name
        holder.previousPrice.text = String.format("৳ %s", product.sprice)
        holder.productPrice.text = String.format("৳ %s", product.price)
        holder.previousPrice.paintFlags = holder.previousPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


        holder.productImage.load(ConstantValues.imageURL + "image/product/small/" + product.img) {
            decoderFactory(SvgDecoder.Factory())
        }


        // Handle product item click
        holder.itemView.setOnClickListener {
            listener?.onProductClick(product, position)
        }

        // Handle Add to Cart button click
        holder.btnAddToCart.setOnClickListener {
            listener?.onAddToCartClick(product, position)
        }
    }

    override fun getItemCount(): Int = productList.size

    // Set the listener for handling click events
    fun setOnProductClickListener(listener: OnProductClickListener) {
        this.listener = listener
    }

    // ViewHolder class to hold item views
    inner class ProductViewHolder(binding: LayoutItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var productImage: ImageView = binding.productImage
        var productName: TextView = binding.productName
        var productPrice: TextView = binding.productPrice
        var previousPrice: TextView = binding.previousPrice
        var btnAddToCart: TextView = binding.addToCart
        var productRPTV: TextView = binding.productRP
        var shopName: TextView = binding.shopNameTV
        var eshopTV: TextView = binding.eshopTV
        var productRPLayout: LinearLayout = binding.productRPLayout
        var rpLayout: LinearLayout = binding.rpLayout
        var discountText: TextView = binding.offerPercent
        var disLayout: LinearLayout = binding.offerLayout

        fun bind(product: ProductModel) {
            // Set product name and price
            productName.text = product.name
            productPrice.text = String.format("৳ %s", product.price)

            // Set the previous price with a strike-through effect
            previousPrice.text = String.format("৳ %s", product.price)
            previousPrice.paintFlags = previousPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            // Load product image using Coil
            productImage.load(ConstantValues.imageURL + "image/product/small/" + product.img) {
                decoderFactory(SvgDecoder.Factory())
            }
        }
    }

    // Click listener interface
    interface OnProductClickListener {
        fun onProductClick(product: ProductModel, position: Int)
        fun onAddToCartClick(product: ProductModel, position: Int)
    }



}
