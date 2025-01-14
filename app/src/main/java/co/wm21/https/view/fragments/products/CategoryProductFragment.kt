package co.wm21.https.view.fragments.products

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.DrawerCatModel
import co.wm21.https.FHelper.networks.Models.ProductModel
import co.wm21.https.R
import co.wm21.https.databinding.FragmentCategoryProductBinding
import co.wm21.https.presenter.DrawerCatListPresenter
import co.wm21.https.presenter.RelatedProductListPresenter
import co.wm21.https.presenter.interfaces.OnDrawerCatListView
import co.wm21.https.presenter.interfaces.OnRelatedProductListView
import co.wm21.https.view.activities.ProductDetailsActivity
import co.wm21.https.view.adapters.PopularProductAdapter
import co.wm21.https.view.adapters.category.SubCatAdapter
import co.wm21.https.view.adapters.product.ProductAdapter
import com.afollestad.materialdialogs.MaterialDialog

class CategoryProductFragment : Fragment(), OnDrawerCatListView, OnRelatedProductListView {

    private lateinit var binding: FragmentCategoryProductBinding
    private var catId: String? = null
    private var subCat_Id: String? = null
    private var brandId: String? = null
    private var productViews: ArrayList<ProductModel> = ArrayList()
    private var CategoryList: ArrayList<DrawerCatModel> = ArrayList()
    private var adapter: SubCatAdapter? = null
    private var productAdapter: PopularProductAdapter? = null
    private var drawerCatListPresenter: DrawerCatListPresenter? = null
    private var relatedProductListPresenter: RelatedProductListPresenter? = null
    private var dialog: MaterialDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize the non-nullable binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_product, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = activity?.let {
            MaterialDialog.Builder(it).title(resources.getString(R.string.loading))
                .content(resources.getString(R.string.pleaseWait))
                .progress(true, 0)
                .cancelable(false)
                .build()
        }

        drawerCatListPresenter = DrawerCatListPresenter(this)
        relatedProductListPresenter = RelatedProductListPresenter(this)

        // Set up RecyclerViews
        binding.scatRecView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = SubCatAdapter(context, CategoryList, R.layout.item_category_layout_2)
        binding.scatRecView.adapter = adapter

        binding.catProductsRecView.layoutManager = GridLayoutManager(context, 2)
        productAdapter = PopularProductAdapter(productViews,requireContext()).addOnClickListener { view, position2 ->
            val productView = productViews[position2!!]
            startActivity(
                Intent(context, ProductDetailsActivity::class.java)
                    .putExtra(ConstantValues.Product.PARCEL, productView)
            )
        }
        binding.catProductsRecView.adapter = productAdapter

        // Handle arguments
        val thisBundle = arguments
        if (!thisBundle!!.getString(ConstantValues.SUB_CAT_ID, "").isEmpty()) {
            subCat_Id = thisBundle.getString(ConstantValues.SUB_CAT_ID, "")
            binding.name.text = thisBundle.getString(ConstantValues.NAME, "")
            if (subCat_Id != null) {
                drawerCatListPresenter!!.onDrawerCatDataLoad(2, catId)
                relatedProductListPresenter!!.RelatedProductDataLoad("200", "0", subCat_Id, "0")
            }
        } else if (!thisBundle.getString(ConstantValues.ARGUMENT1, "").isEmpty()) {
            catId = thisBundle.getString(ConstantValues.ARGUMENT1, "")
            binding.name.text = thisBundle.getString(ConstantValues.NAME, "")
            if (catId != null) {
                drawerCatListPresenter!!.onDrawerCatDataLoad(1, catId)
                relatedProductListPresenter!!.RelatedProductDataLoad("200", catId, "0", "0")
            }
        } else if (!thisBundle.getString(ConstantValues.BRAND_ID, "").isEmpty()) {
            brandId = thisBundle.getString(ConstantValues.BRAND_ID, "")
            binding.name.text = thisBundle.getString(ConstantValues.NAME, "")
            if (brandId != null) {
                binding.scatRecView.visibility = View.GONE
                binding.shimmerScat.visibility = View.GONE
                drawerCatListPresenter!!.onDrawerCatDataLoad(1, catId)
                relatedProductListPresenter!!.RelatedProductDataLoad("200", "0", "0", brandId)
            }
        }
    }

    override fun onDrawerCatListDataLoad(drawerCatModels: MutableList<DrawerCatModel>) {
        drawerCatModels.addAll(drawerCatModels)
        adapter!!.notifyDataSetChanged()
    }

    override fun onDrawerCatListStartLoading() {
        binding.shimmerScat.visibility = View.VISIBLE
        binding.scatRecView.visibility = View.GONE
    }

    override fun onDrawerCatListStopLoading() {
        binding.shimmerScat.visibility = View.GONE
        binding.scatRecView.visibility = View.VISIBLE
    }

    override fun onDrawerCatListShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onRelatedProductListDataLoad(relatedProductModelHeads: List<ProductModel>) {
        productViews.addAll(relatedProductModelHeads)
        productAdapter!!.notifyDataSetChanged()
    }

    override fun onRelatedProductListStartLoading() {
        binding.shimmerProduct.visibility = View.VISIBLE
        binding.noDataFound.visibility = View.GONE
        binding.catProductsRecView.visibility = View.GONE
    }

    override fun onRelatedProductListStopLoading() {
        binding.shimmerProduct.visibility = View.GONE
        binding.catProductsRecView.visibility = View.VISIBLE
        binding.noDataFound.visibility = View.GONE
    }

    override fun onRelatedProductListShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }
}
