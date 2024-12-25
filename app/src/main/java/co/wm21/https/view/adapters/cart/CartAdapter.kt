package co.wm21.https.view.adapters.cart

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.API
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.CartItems
import co.wm21.https.FHelper.networks.Models.DeleteItem
import co.wm21.https.FHelper.networks.Models.UpdateQty
import co.wm21.https.R
import co.wm21.https.databinding.LayoutItemCartBinding
import co.wm21.https.presenter.DeleteItemPresenter
import co.wm21.https.presenter.UpdateQtyPresenter
import co.wm21.https.presenter.interfaces.OnDeleteItemView
import co.wm21.https.presenter.interfaces.OnUpdateQtyView
import co.wm21.https.utils.dialog.LoadingDialog
import co.wm21.https.view.activities.MainActivity
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CartAdapter //  MainActivity mainActivity;
    (
    @field:LayoutRes var layout: Int,
    var context: Context,
    var cartList: ArrayList<CartItems>,
    var totalPrice: TextView,
    var totalRp: TextView,
    var cartFragmentLayout: RelativeLayout
) : RecyclerView.Adapter<CartAdapter.ViewHolder>(), OnUpdateQtyView, OnDeleteItemView {
    var quan: Int = 0
    var totalSelected: Int = 0
    var updateQtyPresenter: UpdateQtyPresenter? = null
    var deleteItemPresenter: DeleteItemPresenter? = null
    var loadingDialog: LoadingDialog? = null
    var delBtnView: View? = null


    var api: API = ConstantValues.getAPI()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<LayoutItemCartBinding>(
            LayoutInflater.from(
                context
            ), layout, parent, false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged", "DefaultLocale", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartModel = cartList[position]
        Picasso.get().load(ConstantValues.web_url + "image/product/small/" + cartModel.img)
            .into(holder.prImg)
        val price = cartModel.price.toString()
        val quantity = cartModel.qty.toString()
        val tPrice = cartModel.tprice.toString()
        holder.price.text = price
        holder.qty.text = quantity
        holder.rp.text = String.format("%.4s", cartModel.point)
        holder.prName.text = cartModel.name
        holder.prTotalPrice.text = tPrice
        val pId = cartList[position].getpId()
        val color = cartList[position].color
        val size = cartList[position].size
        val uId = cartList[position].getuId()

        updateQtyPresenter = UpdateQtyPresenter(this)
        deleteItemPresenter = DeleteItemPresenter(this)
        holder.prImg.load(ConstantValues.imageURL + "image/product/small/" + cartModel.img){
            decoderFactory(SvgDecoder.Factory())
        }
        holder.qtyAdd.setOnClickListener { view: View? ->
            loadingDialog = LoadingDialog(context as Activity)
            quan = holder.qty.text.toString().toInt()
            quan += 1
            updateQtyPresenter!!.UpdateQtyDataLoad(uId, pId, color, size, quan, 1)

            cartList[position].tprice = (cartList[position].price.toDouble() * quan).toString()
            cartList[position].point = (cartList[position].spoint.toDouble() * quan).toString()
            cartList[position].qty = quan.toString()
            holder.prTotalPrice.text = String.format("%.2s", cartList[position].tprice)
            notifyDataSetChanged()
            update()
        }
        holder.qtyMinus.setOnClickListener { view: View ->
            loadingDialog = LoadingDialog(context as Activity)
            if (cartModel.qty.toDouble() > 1) {
                quan = holder.qty.text.toString().toInt()
                quan -= 1
                updateQtyPresenter!!.UpdateQtyDataLoad(uId, pId, color, size, quan, 1)
                cartList[position].tprice = (cartList[position].price.toDouble() * quan).toString()
                cartList[position].point = (cartList[position].spoint.toDouble() * quan).toString()
                cartList[position].qty = quan.toString()
                holder.prTotalPrice.text = String.format("%.2s", cartList[position].tprice)
                notifyDataSetChanged()
                update()
            } else {
                val builder = AlertDialog.Builder(context)
                val viewGroup = view.findViewById<ViewGroup>(android.R.id.content)
                val dialogView = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.layout_confirm_delete_cart_item, viewGroup, false)
                val closeBtn = dialogView.findViewById<RelativeLayout>(R.id.closeId)
                val deleteBtn = dialogView.findViewById<RelativeLayout>(R.id.deleteId)
                builder.setView(dialogView)
                val alertDialog = builder.create()
                alertDialog.show()
                closeBtn.setOnClickListener { view1: View? ->
                    alertDialog.dismiss()
                }
                deleteBtn.setOnClickListener { view1: View? ->
                    delBtnView = view1
                    loadingDialog = LoadingDialog(context as Activity)
                    deleteItemPresenter!!.deleteItemDataLoad(cartModel.serial, 1)
                    notifyItemRangeChanged(position, cartList.size)
                    cartList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemChanged(position)
                    alertDialog.dismiss()
                    update()
                }
            }
        }

        holder.deleteItem.setOnClickListener { view: View ->
            delBtnView = view
            loadingDialog = LoadingDialog(context as Activity)
            val builder = AlertDialog.Builder(context)
            val viewGroup = view.findViewById<ViewGroup>(android.R.id.content)
            val dialogView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.layout_confirm_delete_cart_item, viewGroup, false)
            val closeBtn = dialogView.findViewById<RelativeLayout>(R.id.closeId)
            val deleteBtn = dialogView.findViewById<RelativeLayout>(R.id.deleteId)
            builder.setView(dialogView)
            val alertDialog = builder.create()
            alertDialog.show()
            closeBtn.setOnClickListener { view1: View? ->
                alertDialog.dismiss()
            }
            deleteBtn.setOnClickListener { view1: View? ->
                deleteItemPresenter!!.deleteItemDataLoad(cartModel.serial, 1)
                notifyItemRangeChanged(position, cartList.size)
                alertDialog.dismiss()
                cartList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemChanged(position)
                notifyDataSetChanged()
                update()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun update() {
        var sum = 0.0
        var rp = 0.0
        for (i in cartList.indices) {
            val price = cartList[i].tprice.toDouble()
            sum += price

            rp += cartList[i].point.toDouble()
        }
        totalRp.text = "RP $rp"
        (context as MainActivity).getCartItems()
        totalPrice.text = "TK $sum"
    }

    private fun showSnackBar(msg: String) {
        val snackbar = Snackbar.make(cartFragmentLayout, msg, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onUpdateQtyDataLoad(updateQty: UpdateQty) {
        showSnackBar(updateQty.data)
    }

    override fun onUpdateQtyStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onUpdateQtyStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onUpdateQtyShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteItemDataLoad(deleteItem: DeleteItem) {
        notifyDataSetChanged()
        Snackbar.make(delBtnView!!, deleteItem.data, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDeleteItemStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onDeleteItemStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onDeleteItemShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    inner class ViewHolder(itemView: LayoutItemCartBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var prImg: ImageView = itemView.productImage
        var qtyAdd: ImageView = itemView.qtyIncrement
        var qtyMinus: ImageView = itemView.qtyDecrement
        var deleteItem: ImageView = itemView.deleteItem
        var prName: TextView = itemView.productName
        var qty: TextView = itemView.productQuantity
        var prTotalPrice: TextView = itemView.singleProductTotalPrice
        var price: TextView = itemView.productPrice
        var rp: TextView = itemView.singleRp
    }
}
