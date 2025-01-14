package co.wm21.https.view.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.R
import co.wm21.https.utils.Constant
import co.wm21.https.view.adapters.SliderAdapter.SliderAdapterVH
import com.example.example.SlideImage
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter(private val context: Context, mSliderItems: MutableList<SlideImage>) :
    SliderViewAdapter<SliderAdapterVH>() {
    private var mSliderItems: MutableList<SlideImage> = ArrayList()

    init {
        this.mSliderItems = mSliderItems
    }

    fun renewItems(sliderItems: MutableList<SlideImage>) {
        this.mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: SlideImage) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val slideImage = mSliderItems[position]

        if (slideImage.info != null) {
            viewHolder.textViewDescription.text =
                Constant.getRealStringEscape(slideImage.info)
            viewHolder.textViewDescription.textSize = 16f
            viewHolder.textViewDescription.setTextColor(Color.WHITE)
        } else {
            viewHolder.textViewDescription.text = ""
        }
        // viewHolder.imageViewBackground.setImageDrawable(Constant.getDrawableFromUrl("image", "slide", sliderItem.getImageUrl()));
        Picasso.get().load(ConstantValues.URL + "image/slide/" + slideImage.img1)
            .into(viewHolder.imageViewBackground)
        /* Glide.with(viewHolder.itemView)
                .load(sliderItem.getImageUrl())
                .fitCenter()
                .into(viewHolder.imageViewBackground);*/
//
//        viewHolder.itemView.setOnClickListener(v -> Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show());
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
        var textViewDescription: TextView = itemView.findViewById(R.id.tv_auto_image_slider)
    }

}