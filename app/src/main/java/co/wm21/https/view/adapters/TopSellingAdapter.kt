package co.wm21.https.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.TopSellingProModel
import co.wm21.https.R
import co.wm21.https.databinding.LayoutItemTopSelBinding
import coil.decode.SvgDecoder
import coil.load
import com.squareup.picasso.Picasso

class TopSellingAdapter(
    var context: Context,
    var topSellingProducts: ArrayList<TopSellingProModel>,
    var layout: Int
) : RecyclerView.Adapter<TopSellingAdapter.viewHolder>() {
    var binding: LayoutItemTopSelBinding? = null
    var listener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        binding = LayoutItemTopSelBinding.inflate(
            LayoutInflater.from(
                context
            ), parent, false
        )
        return viewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val product = topSellingProducts[position]
        Picasso.get()
            .load(ConstantValues.web_url + "image/product/small/" + product.img)
            .placeholder(R.drawable.ic_image_temp) // Replace with your placeholder image resource
            .into(holder.prImage)

        holder.prImage.load(ConstantValues.imageURL + "image/product/small/" + product.img) {
            decoderFactory(SvgDecoder.Factory()) // Corrected method
        }
    }

    override fun getItemCount(): Int {
        return topSellingProducts.size
    }

    fun setOnClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    fun addOnClickListener(listener: ItemClickListener?): TopSellingAdapter {
        this.listener = listener
        return this
    }


    inner class viewHolder(itemView: LayoutItemTopSelBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var prImage: ImageView = itemView.productImage

        init {
            if (listener != null) itemView.root.setOnClickListener { v: View? ->
                listener!!.OnClick(
                    v,
                    absoluteAdapterPosition
                )
            }
        }
    }
}
