package co.wm21.https.view.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.ProductModel
import co.wm21.https.R
import co.wm21.https.helpers.SessionHandler
import co.wm21.https.utils.Constant
import co.wm21.https.view.activities.ProductDetailsActivity
import coil.decode.SvgDecoder
import coil.load
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class AllHotDealAdapter(
    var context: Context,
    var productList: List<ProductModel>,
    @field:LayoutRes var itemRes: Int
) : RecyclerView.Adapter<AllHotDealAdapter.viewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    var listener: ItemClickListener? = null
    var sessionHandler: SessionHandler? = null
    var pos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(mInflater.inflate(itemRes, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewHolder, @SuppressLint("RecyclerView") position: Int) {
        sessionHandler = SessionHandler(context)
        pos = position
        val product = productList[position]
        holder.productName.text = product.name
        holder.previousPrice.text = String.format("৳ %s", product.price)
        holder.productPrice.text = String.format("৳ %s", product.price)
        if (sessionHandler!!.isLoggedIn) {
            holder.rpLayout.visibility = View.VISIBLE
            holder.eshopTV.visibility = View.GONE
            holder.productRPTV.text = String.format("%s", product.point)
        } else {
            holder.rpLayout.visibility = View.GONE
            holder.eshopTV.visibility = View.VISIBLE
        }
        holder.shopName.text = "(" + product.uploadBy + ")"
        if (product.discount.toInt() != 0) {
            holder.offerLayout.visibility = View.VISIBLE
            val dis = Math.round((product.discount.toDouble() / product.price.toDouble()) * 100)
                .toDouble()
            holder.offerPercent.text = String.format("%s", dis)
        } else {
            holder.offerLayout.visibility = View.GONE
        }
        holder.productImage.load(ConstantValues.imageURL + "image/product/small/" + product.getImg()){
            decoderFactory(SvgDecoder.Factory())
        }

        //holder.productImage.setImageDrawable(product.getImg());
        holder.button.setOnClickListener { view: View? ->
            context.startActivity(
                Intent(
                    context, ProductDetailsActivity::class.java
                )
                    .putExtra(ConstantValues.Product.PARCEL, productList[position])
            )
        }

        //Calendar currentDate = Calendar.getInstance();
        val c = Calendar.getInstance().timeInMillis

        val calendar = Calendar.getInstance()

        println(calendar.time)
        @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = simpleDateFormat.format(c)
        try {
            val date1 = simpleDateFormat.parse(currentDate)
            val date2 = simpleDateFormat.parse(product.offerDate.toString())
            val today = Calendar.getInstance()
            val offerDate = Calendar.getInstance()


            if (date2 != null && date1 != null) {
                today.time = date1
                offerDate.time = date2

                val different = offerDate.timeInMillis - today.timeInMillis
                if (different >= 0) {
                    holder.expiredDate.visibility = View.GONE
                    holder.hasDate.visibility = View.VISIBLE
                    object : CountDownTimer(different, 1000) {
                        override fun onTick(l: Long) {
                            (context as Activity).runOnUiThread {
                                //change View Data
                                val days = TimeUnit.MILLISECONDS.toDays(l)
                                val hours =
                                    (TimeUnit.MILLISECONDS.toHours(l) - TimeUnit.DAYS.toHours(days))
                                val minutes =
                                    (TimeUnit.MILLISECONDS.toMinutes(l) - TimeUnit.HOURS.toMinutes(
                                        TimeUnit.DAYS.toHours(days) + hours
                                    ))
                                val seconds =
                                    (TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(
                                        minutes + TimeUnit.DAYS.toMinutes(days) + TimeUnit.HOURS.toMinutes(
                                            hours
                                        )
                                    ))
                                @SuppressLint("DefaultLocale") val timer = String.format(
                                    Locale.getDefault(),
                                    "%02d:%02d:%02d:%02d",
                                    days,
                                    hours,
                                    minutes,
                                    seconds
                                )

                                val horMimSec =
                                    timer.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                                        .toTypedArray()
                                holder.day.text = horMimSec[0]
                                holder.hour.text = horMimSec[1]
                                holder.min.text = horMimSec[2]
                            }
                        }

                        override fun onFinish() {
                        }
                    }.start()
                } else {
                    holder.expiredDate.visibility = View.VISIBLE
                    holder.hasDate.visibility = View.GONE
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setOnClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    fun addOnClickListener(listener: ItemClickListener?): AllHotDealAdapter {
        this.listener = listener
        return this
    }

    inner class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImage: ImageView = itemView.findViewById(R.id.h_productImage)
        var productName: TextView = itemView.findViewById(R.id.h_productName)
        var productPrice: TextView = itemView.findViewById(R.id.h_productPrice)
        var previousPrice: TextView = itemView.findViewById(R.id.h_previousPrice)
        var btnAddToCart: TextView = itemView.findViewById(R.id.h_addToCart)
        var productRPTV: TextView = itemView.findViewById(R.id.productRP)
        var shopName: TextView = itemView.findViewById(R.id.shopNameTV)
        var eshopTV: TextView = itemView.findViewById(R.id.eshopTV)
        var productRPLayout: LinearLayout = itemView.findViewById(R.id.productRPLayout)
        var day: TextView = itemView.findViewById(R.id.daysId)
        var hour: TextView = itemView.findViewById(R.id.hourId)
        var min: TextView = itemView.findViewById(R.id.minID)
        var sec: TextView? = null
        var offerPercent: TextView = itemView.findViewById(R.id.offerPercent)
        var button: RelativeLayout = itemView.findViewById(R.id.h_prButton)
        var hasDate: LinearLayout = itemView.findViewById(R.id.hasDates)
        var expiredDate: LinearLayout = itemView.findViewById(R.id.dateExpired)
        var offerLayout: LinearLayout = itemView.findViewById(R.id.offerLayout)
        var rpLayout: LinearLayout = itemView.findViewById(R.id.rpLayout)

        init {
            previousPrice.paintFlags = previousPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            if (listener != null) itemView.setOnClickListener { v: View? ->
                listener!!.OnClick(
                    v,
                    pos
                )
            }
        }
    }
}
