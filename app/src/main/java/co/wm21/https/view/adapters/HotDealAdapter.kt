package co.wm21.https.view.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
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
import androidx.viewpager2.widget.ViewPager2
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.R
import co.wm21.https.helpers.SessionHandler
import co.wm21.https.utils.Constant
import co.wm21.https.view.activities.ProductDetailsActivity
import co.wm21.https.view.adapters.product.ProductView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class HotDealAdapter : RecyclerView.Adapter<HotDealAdapter.HotDealViewHolder> {
    var context: Context
    var productList: MutableList<ProductView> = mutableListOf()
    private val mInflater: LayoutInflater

    @LayoutRes
    var itemRes: Int
    var listener: ItemClickListener? = null
    var pos: Int = 0
    var sessionHandler: SessionHandler? = null
    var viewPager2: ViewPager2? = null

    var progressBar: ProgressDialog? = null

    constructor(context: Context, productList: MutableList<ProductView>, itemRes: Int) {
        this.context = context
        this.productList = productList
        this.itemRes = itemRes
        mInflater = LayoutInflater.from(context)
    }

    constructor(
        context: Context,
        productList: MutableList<ProductView>,
        itemRes: Int,
        viewPager2: ViewPager2?
    ) {
        this.context = context
        this.productList = productList
        this.viewPager2 = viewPager2
        this.itemRes = itemRes
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotDealViewHolder {
        return HotDealViewHolder(mInflater.inflate(itemRes, parent, false))
    }

    override fun onBindViewHolder(
        holder: HotDealViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        pos = position
        val product = productList[position]
        holder.productName.text = product.productName
        holder.previousPrice.text = String.format("৳ %s", product.price)
        holder.productPrice.text = String.format("৳ %s", product.currentPrice)


        holder.productImage.setImageDrawable(product.image)

        holder.button.setOnClickListener { view: View? ->
            context.startActivity(
                Intent(context, ProductDetailsActivity::class.java)
                    .putExtra(ConstantValues.Product.PARCEL, productList[position])
            )
        }

        if (position == productList.size - 2) {
            viewPager2!!.post(runnable)
        }

        //Calendar currentDate = Calendar.getInstance();
        val c = Calendar.getInstance().timeInMillis

        val calendar = Calendar.getInstance()

        println(calendar.time)
        @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = simpleDateFormat.format(c)
        try {
            val date1 = simpleDateFormat.parse(currentDate)
            val date2 = simpleDateFormat.parse(product.offer_date.toString())
            val today = Calendar.getInstance()
            val offerDate = Calendar.getInstance()


            if (date2 != null && date1 != null) {
                today.time = date1
                offerDate.time = date2

                val different = offerDate.timeInMillis - today.timeInMillis
                if (different > 0) {
                    holder.expiredDate.visibility = View.GONE
                    holder.hasDate.visibility = View.VISIBLE
                    object : CountDownTimer(different, 1000) {
                        override fun onTick(l: Long) {
                            (context as Activity).runOnUiThread {
                                //chang
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

    fun addOnClickListener(listener: ItemClickListener?): HotDealAdapter {
        this.listener = listener
        return this
    }

    inner class HotDealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImage: ImageView = itemView.findViewById(R.id.h_productImage)
        var productName: TextView = itemView.findViewById(R.id.h_productName)
        var productPrice: TextView = itemView.findViewById(R.id.h_productPrice)
        var previousPrice: TextView = itemView.findViewById(R.id.h_previousPrice)
        var btnAddToCart: TextView = itemView.findViewById(R.id.h_addToCart)
        var day: TextView = itemView.findViewById(R.id.daysId)
        var hour: TextView = itemView.findViewById(R.id.hourId)
        var min: TextView = itemView.findViewById(R.id.minID)
        var sec: TextView? = null
        var offerPercent: TextView = itemView.findViewById(R.id.offerPercent)
        var button: RelativeLayout = itemView.findViewById(R.id.h_prButton)

        //  sec = itemView.findViewById(R.id.secID);
        var hasDate: LinearLayout = itemView.findViewById(R.id.hasDates)
        var expiredDate: LinearLayout = itemView.findViewById(R.id.dateExpired)


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

    private val runnable = Runnable {
        productList.addAll(productList)
        notifyDataSetChanged()
    }
}
