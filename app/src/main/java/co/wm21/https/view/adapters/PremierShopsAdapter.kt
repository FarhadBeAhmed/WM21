package co.wm21.https.view.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import com.squareup.picasso.Picasso
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.PremierShopData
import co.wm21.https.databinding.LayoutItemPremiumShopBinding
import co.wm21.https.view.activities.MainActivity
import co.wm21.https.view.fragments.shops.ShopsProductsFragment
import coil.decode.SvgDecoder
import coil.load

class PremierShopsAdapter(
    private var shopsModelsList: ArrayList<PremierShopData>,
    private val context: Context,
    @LayoutRes private val layout: Int
) : RecyclerView.Adapter<PremierShopsAdapter.ViewHolder>(), Filterable {

    private var allShops: List<PremierShopData> = ArrayList(shopsModelsList)
    private lateinit var binding: LayoutItemPremiumShopBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layout, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shopModel = shopsModelsList[position]
        holder.imageView.load(ConstantValues.imageURL + "shop/img/shop/" + shopModel.shopImage){
            decoderFactory(SvgDecoder.Factory())
        }
        holder.shopCoverPhoto.load(ConstantValues.imageURL + "shop/img/shop/" + shopModel.smallImg){
            decoderFactory(SvgDecoder.Factory())
        }
        //.get().load(ConstantValues.web_url + "shop/img/shop/" + shopModel.shopImage).into(holder.imageView)
        ///Picasso.get().load(ConstantValues.web_url + "shop/img/shop/" + shopModel.smallImg).into(holder.shopCoverPhoto)
        holder.shopName.text = shopModel.shopName
        holder.shopType.text = shopModel.shopTypeName
        val address = shopModel.address
        holder.address.text = address
        holder.mobile.text = shopModel.phone

        holder.productsButton.setOnClickListener {
            val shopsProductsFragment = ShopsProductsFragment()
            val bundle = Bundle().apply {
                shopModel.shopId?.let { it1 -> putInt(ConstantValues.admin_login.SHOP_ID, it1.toInt()) }
                shopModel.shopTypeId?.let { it1 -> putInt(ConstantValues.admin_login.TYPE_ID, it1.toInt()) }
                putString(ConstantValues.admin_login.MOBILE, shopModel.phone)
                putString(ConstantValues.admin_login.SHOP_TYPE, shopModel.shopTypeName)
                putString(ConstantValues.admin_login.IMAGE, shopModel.shopImage)
                putString(ConstantValues.admin_login.PHOTO, shopModel.smallImg)
                putString(ConstantValues.admin_login.ADDRESS, address)
            }
            shopsProductsFragment.arguments = bundle
            switchFragment(shopsProductsFragment, "ShopsProductsFragment")
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
}
