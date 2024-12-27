package co.wm21.https.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.PremierShopData
import co.wm21.https.databinding.LayoutItemPremiumShopBinding
import co.wm21.https.view.activities.MainActivity
import co.wm21.https.view.activities.PremierShopViewActivity
import coil.decode.SvgDecoder
import coil.load

class PremierShopsAdapter(
    private var shopsModelsList: ArrayList<PremierShopData>,
    private val context: Context,
    @LayoutRes private val layout: Int
) : RecyclerView.Adapter<PremierShopsAdapter.ViewHolder>(), Filterable {

    private var allShops: List<PremierShopData> = ArrayList(shopsModelsList)
    private lateinit var binding: LayoutItemPremiumShopBinding

    // Listener for item clicks
    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopModel = shopsModelsList[position]

        // Bind data to the views
        holder.imageView.load(ConstantValues.imageURL + "shop/img/shop/" + shopModel.shopImage) {
            decoderFactory(SvgDecoder.Factory())
        }
        holder.shopCoverPhoto.load(ConstantValues.imageURL + "shop/img/shop/" + shopModel.smallImg) {
            decoderFactory(SvgDecoder.Factory())
        }
        holder.shopName.text = shopModel.shopName
        holder.shopType.text = shopModel.shopTypeName
        val address = shopModel.address
        holder.address.text = address
        holder.mobile.text = shopModel.phone

        // Handle button clicks for "View Products"
        holder.productsButton.setOnClickListener {
            val intent = Intent(context, PremierShopViewActivity::class.java)
            intent.putExtra("shopModel", shopModel) // Pass the PremierShopData object
            context.startActivity(intent)
        }


        // Handle item clicks using the custom listener
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(shopModel, position)
        }
    }

    private fun switchFragment(fragment: Fragment, tag: String) {
        val fragmentManager: FragmentManager = (context as FragmentActivity).supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
        fragmentManager.beginTransaction()
            .replace((context as MainActivity).binding.fragmentContainer.id, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun getItemCount(): Int = shopsModelsList.size

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterList = if (charSequence.isEmpty()) {
                ArrayList(allShops)
            } else {
                allShops.filter {
                    it.shopName?.contains(charSequence, ignoreCase = true) == true
                }
            }
            return FilterResults().apply { values = filterList }
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            shopsModelsList.clear()
            shopsModelsList.addAll(filterResults.values as Collection<PremierShopData>)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(binding: LayoutItemPremiumShopBinding) : RecyclerView.ViewHolder(binding.root) {
        val shopName: TextView = binding.shopName
        val shopType: TextView = binding.shopType
        val address: TextView = binding.address
        val mobile: TextView = binding.mobileNumber
        val imageView: ImageView = binding.image
        val shopCoverPhoto: ImageView = binding.shopCoverPhoto
        val productsButton: RelativeLayout = binding.productsButton
    }

    // Custom interface for item clicks
    interface OnItemClickListener {
        fun onItemClick(shopData: PremierShopData, position: Int)
    }

    // Method to set the item click listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
}
