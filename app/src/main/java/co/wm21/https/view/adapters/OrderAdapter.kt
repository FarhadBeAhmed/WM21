package co.wm21.https.view.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.API
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.DeleteItem
import co.wm21.https.FHelper.networks.Models.OrderItemModel
import co.wm21.https.FHelper.networks.Models.UpdateQty
import co.wm21.https.R
import co.wm21.https.databinding.ItemOrderProductListSingleRowBinding
import co.wm21.https.helpers.User
import co.wm21.https.presenter.DeleteItemPresenter
import co.wm21.https.presenter.UpdateQtyPresenter
import co.wm21.https.presenter.interfaces.OnDeleteItemView
import co.wm21.https.presenter.interfaces.OnUpdateQtyView
import co.wm21.https.utils.dialog.LoadingDialog
import co.wm21.https.view.activities.MainActivity
import co.wm21.https.view.fragments.manageOrder.OrderFragment
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class OrderAdapter(
    @field:LayoutRes var layout: Int,
    var context: Context,
    var cartList: ArrayList<OrderItemModel>,
    var totalPrice: TextView,
    var fragmentLayout: LinearLayout,
    var totalRP: TextView,
    var adjRP: TextView,
    var adjTotalPrice: TextView,
    var payable: TextView,
    var usingRP: LinearLayout
) : RecyclerView.Adapter<OrderAdapter.viewHolder>(), OnUpdateQtyView, OnDeleteItemView {
    var quan: Int = 0
    var totalSelected: Int = 0
    var api: API = ConstantValues.getAPI()

    // MainActivity mainActivity;
    var user: User? = null
    var updateQtyPresenter: UpdateQtyPresenter? = null
    var deleteItemPresenter: DeleteItemPresenter? = null
    var loadingDialog: LoadingDialog? = null
    var delBtnView: View? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = DataBindingUtil.inflate<ItemOrderProductListSingleRowBinding>(
            LayoutInflater.from(
                context
            ), layout, parent, false
        )
        return viewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged", "DefaultLocale", "SetTextI18n")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        updateQtyPresenter = UpdateQtyPresenter(this)
        deleteItemPresenter = DeleteItemPresenter(this)
        user = User(context)
        val cartModel = cartList[position]

        holder.prImg.load(ConstantValues.imageURL + "image/product/small/" + cartModel.img){
            decoderFactory(SvgDecoder.Factory())
        }
        val price = cartModel.price.toString()
        val quantity = cartModel.qty.toString()
        val tPrice = cartModel.total.toString()
        holder.price.text = price
        holder.qty.text = quantity
        holder.prName.text = cartModel.name
        holder.prTotalPrice.text = tPrice
        holder.sPoint.text = cartModel.spoint + ""
        val pId = cartList[position].getpId()
        val color = cartList[position].color
        val size = cartList[position].size
        val uId = user!!.username
        holder.qtyAdd.setOnClickListener { view: View? ->
            loadingDialog = LoadingDialog(context as Activity)
            quan = holder.qty.text.toString().toInt()
            quan += 1
            updateQtyPresenter!!.UpdateQtyDataLoad(uId, pId, color, size, quan, 2)

            cartList[position].total = (cartList[position].price.toDouble() * quan).toString()
            cartList[position].point = (cartList[position].spoint.toDouble() * quan).toString()
            cartList[position].qty = quan.toString()
            holder.prTotalPrice.text = String.format("%.2s", cartList[position].total)
            notifyDataSetChanged()
            update()
        }
        holder.qtyMinus.setOnClickListener { view: View ->
            loadingDialog = LoadingDialog(context as Activity)
            if (cartModel.qty.toDouble() > 1) {
                quan = holder.qty.text.toString().toInt()
                quan -= 1
                updateQtyPresenter!!.UpdateQtyDataLoad(uId, pId, color, size, quan, 2)
                cartList[position].total = (cartList[position].price.toDouble() * quan).toString()
                cartList[position].point = (cartList[position].spoint.toDouble() * quan).toString()
                cartList[position].qty = quan.toString()
                holder.prTotalPrice.text = String.format("%.2s", cartList[position].total)
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
                    deleteItemPresenter!!.deleteItemDataLoad(cartModel.serial, 2)
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
                deleteItemPresenter!!.deleteItemDataLoad(cartModel.serial, 2)
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

    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun update() {
        var sum = 0.0
        var rp = 0.0
        for (i in cartList.indices) {
            val price = cartList[i].total.toDouble()
            sum += price
            rp += cartList[i].point.toDouble()
        }
        OrderFragment.adjustedRP = rp
        OrderFragment.adjustedPrice = rp * 60
        OrderFragment.payablePrice = sum - OrderFragment.adjustedPrice
        totalRP.text = String.format("%.02f", rp)
        adjRP.text = "RP " + String.format("%.02f", OrderFragment.adjustedRP)
        (context as MainActivity).getCartItems()
        totalPrice.text = String.format("%.02f", sum)
        adjTotalPrice.text = String.format("%.02f", OrderFragment.adjustedPrice)
        payable.text = String.format("%.02f", OrderFragment.payablePrice)
        if (OrderFragment.myPoint < OrderFragment.adjustedRP) {
            usingRP.visibility = View.GONE
            OrderFragment.ifAdjust = 0
        } else {
            usingRP.visibility = View.VISIBLE
            //OrderFragment.ifAdjust = 1;
        }
    }

    fun switchFragment(fragment: Fragment?, tag: String?) {
        val fm = (context as FragmentActivity).supportFragmentManager
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment!!, tag).addToBackStack(tag)
            .commit()
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

    inner class viewHolder(itemView: ItemOrderProductListSingleRowBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var prImg: ImageView = itemView.productImage
        var qtyAdd: ImageView = itemView.qtyIncrement
        var qtyMinus: ImageView = itemView.qtyDecrement
        var deleteItem: ImageView = itemView.deleteItem
        var prName: TextView = itemView.productName
        var qty: TextView = itemView.productQuantity
        var prTotalPrice: TextView = itemView.singleProductTotalPrice
        var price: TextView = itemView.productPrice
        var sPoint: TextView = itemView.productRP
    }

    private fun showSnackBar(msg: String) {
        val snackbar = Snackbar.make(fragmentLayout, msg, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}
