package co.wm21.https.view.fragments.manageOrder

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.wm21.https.FHelper.API
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.EshopListModel
import co.wm21.https.FHelper.networks.Models.LocationModel
import co.wm21.https.FHelper.networks.Models.OrderConfirmModel
import co.wm21.https.FHelper.networks.Models.OrderItemModel
import co.wm21.https.FHelper.networks.Models.OrderItemModelHead
import co.wm21.https.FHelper.networks.Models.ProfileDetailsHead
import co.wm21.https.FHelper.networks.Models.ShopTypeModel
import co.wm21.https.R
import co.wm21.https.databinding.FragmentOrderBinding
import co.wm21.https.helpers.User
import co.wm21.https.presenter.EshopListPresenter
import co.wm21.https.presenter.LocationListPresenter
import co.wm21.https.presenter.OrderConfirmPresenter
import co.wm21.https.presenter.OrderItemListPresenter
import co.wm21.https.presenter.ProfileDetailsPresenter
import co.wm21.https.presenter.ShopTypePresenter
import co.wm21.https.presenter.interfaces.OnEshopListView
import co.wm21.https.presenter.interfaces.OnLocationListView
import co.wm21.https.presenter.interfaces.OnOrderConfirmView
import co.wm21.https.presenter.interfaces.OnOrderItemListView
import co.wm21.https.presenter.interfaces.OnProfileDetailsView
import co.wm21.https.presenter.interfaces.OnShopTypeView
import co.wm21.https.utils.dialog.DialogHelper
import co.wm21.https.utils.dialog.LoadingDialog
import co.wm21.https.view.activities.SearchShopActivity
import co.wm21.https.view.adapters.OrderAdapter
import co.wm21.https.view.adapters.OrderEshopListAdapter
import co.wm21.https.view.fragments.HomeFragment
import co.wm21.https.view.fragments.LoginFragment
import com.google.android.material.snackbar.Snackbar

class OrderFragment : Fragment(), OnOrderItemListView, OnLocationListView, OnShopTypeView,
    OnEshopListView, OnOrderConfirmView, OnProfileDetailsView {
    private lateinit var binding: FragmentOrderBinding
    private var api: API? = null
    private var user: User? = null
    private var countryId = ""
    private var divId = ""
    private var disId = ""
    private var thanaId = ""
    private var unionId = ""
    private var orderedList: ArrayList<OrderItemModel>? = null
    private val total = 0.0
    private var orderAdapter: OrderAdapter? = null
    private var addressID = ""


    private var eshopListAdapter: OrderEshopListAdapter? = null

    private var countryList: ArrayList<String>? = null
    private var divList: ArrayList<String>? = null
    private var disList: ArrayList<String>? = null
    private var thanaList: ArrayList<String>? = null
    private var unionList: ArrayList<String>? = null
    private var countryListId: ArrayList<String>? = null
    private var divListId: ArrayList<String>? = null
    private var disListId: ArrayList<String>? = null
    private var thanaListId: ArrayList<String>? = null
    private var unionListId: ArrayList<String>? = null
    private var shipping = ""
    private var eshopType = ""
    private var address = ""
    var loadingDialog: LoadingDialog? = null
    private var orderItemListPresenter: OrderItemListPresenter? = null
    private var locationListPresenter: LocationListPresenter? = null
    private var shopTypePresenter: ShopTypePresenter? = null
    private var eshopListPresenter: EshopListPresenter? = null
    private var orderConfirmPresenter: OrderConfirmPresenter? = null
    private var profileDetailsPresenter: ProfileDetailsPresenter? = null
    private var stringAdapter: ArrayAdapter<String>? = null


    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)

        api = ConstantValues.getAPI()
        user = User(context)

        ifAdjust = 0
        binding.orderConfirmButton.setOnClickListener { view: View -> this.orderConfirm(view) }
        binding.searchShopButton.setOnClickListener { view: View? ->
            startActivity(
                Intent(
                    context, SearchShopActivity::class.java
                )
            )
        }
      /*  if (orderedList?.isNotEmpty() == true)
        if (myPoint < adjustedRP && myPoint <= 0) {
            binding.userRPLayout.visibility = View.GONE
        } else if (myPoint >= adjustedRP) {
            binding.userRPLayout.visibility = View.VISIBLE
        }*/

        binding.countryTxt.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->
                binding.division.visibility = View.VISIBLE
                binding.district.visibility = View.GONE
                //divListId =null
                binding.thana.visibility = View.GONE
                binding.union.visibility = View.GONE

                val `val` = adapterView.adapter.getItem(i).toString()
                countryId = countryListId!![i]

                binding.divisionTxt.setText("")
                getLocation(ConstantValues.profile.DIVISION, `val`)
                getEshop(countryId, ConstantValues.location.COUNTRY)


            }
        binding.divisionTxt.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->
                binding.district.visibility = View.VISIBLE
                binding.thana.visibility = View.GONE
                binding.union.visibility = View.GONE
                val `val` = adapterView.adapter.getItem(i).toString()
                divId = divListId!![i]
                binding.districtTxt.setText("")
                getLocation(ConstantValues.profile.DISTRICT, `val`)
                getEshop(divId, ConstantValues.location.DIVISION)
            }
        binding.districtTxt.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->
                binding.thana.visibility = View.VISIBLE
                binding.union.visibility = View.GONE
                val `val` = adapterView.adapter.getItem(i).toString()
                disId = disListId!![i]
                binding.thanaTxt.setText("")
                getLocation(ConstantValues.profile.THANA, `val`)
                getEshop(disId, ConstantValues.location.DISTRICT)
            }
        binding.thanaTxt.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->
                binding.union.visibility = View.VISIBLE
                val `val` = adapterView.adapter.getItem(i).toString()
                thanaId = thanaListId!![i]
                binding.unionTxt.setText("")
                getLocation(ConstantValues.profile.UNION, `val`)
                getEshop(thanaId, ConstantValues.location.THANA)
            }
        binding.unionTxt.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->
                val `val` = adapterView.adapter.getItem(i).toString()
                unionId = unionListId!![i]
                getEshop(unionId, ConstantValues.location.UNION)
            }
        binding.shippingRGroupId.setOnCheckedChangeListener { radioGroup: RadioGroup?, checkedId: Int ->
            if (checkedId == binding.receiveEshopBtn.id) {
                shipping = "Eshop Delivery"
                binding.shippingAddressET.editText!!.setText("")
                binding.shippingAddressLayout.visibility = View.GONE
            } else if (checkedId == binding.homeDeliveryBtn.id) {
                shipping = "Home Delivery"
                if (user!!.username != null && user!!.password != null) {
                    profileDetailsPresenter!!.profileDetailsDataLoad(user!!.username)
                }
                binding.shippingAddressLayout.visibility = View.VISIBLE
            }
        }
        binding.useRp.setOnCheckedChangeListener { compoundButton: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                binding.adjustedTotal.text = String.format("%.02f", adjustedPrice)
                binding.adjustedRP.text = String.format("%.02f", adjustedRP)
                binding.payableTotal.text = String.format("%.02f", payablePrice)
                binding.adjustedLayout.visibility = View.VISIBLE
                binding.payableLayout.visibility = View.VISIBLE
                ifAdjust = 1
            } else {
                binding.adjustedLayout.visibility = View.GONE
                binding.payableLayout.visibility = View.GONE
                ifAdjust = 0
            }
        }

        orderItemListPresenter = OrderItemListPresenter(this)
        locationListPresenter = LocationListPresenter(this)
        shopTypePresenter = ShopTypePresenter(this)
        eshopListPresenter = EshopListPresenter(this)
        orderConfirmPresenter = OrderConfirmPresenter(this)
        profileDetailsPresenter = ProfileDetailsPresenter(this)
        loadingDialog = LoadingDialog(context as Activity?)


        allOrderedProduct()

        return binding.getRoot();
    }


    private fun orderConfirm(view: View) {
        if (user!!.username != null && user!!.password != null) {
            if (eshopType == "teleShop") {
                if (selectHoise) {
                    //eshopID = eshopListAdapter.getSelected().getId();
                    orderPush()
                } else {
                    showSnackBar("Please Select E-Shop")
                }
            } else if (eshopType == "preShop") {
                orderPush()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (sName != "" || sNumber != "" || sAddress != "") {
            binding.locationLayout.visibility = View.GONE
            binding.searchedShopCard.visibility = View.VISIBLE
            binding.sName.text = sName
            binding.sShopNumber.text = sNumber
            binding.sAddress.text = sAddress
        } else {
            binding.locationLayout.visibility = View.VISIBLE
            binding.searchedShopCard.visibility = View.GONE
        }
    }

    private fun orderPush() {
        if (shipping != "") {
            if (binding.agreeTermBtnId.isChecked) {
                orderConfirmPresenter!!.orderConfirmDataLoad(
                    user!!.username,
                    eshopID,
                    shipping,
                    address,
                    ifAdjust
                )
            } else {
                binding.agreeTermBtnId.error = "should be agreed"
            }
        } else {
            showSnackBar("Please Confirm your shipping")
        }
    }

    @SuppressLint("DefaultLocale")
    private fun allOrderedProduct() {
        orderedList = ArrayList()
        binding.checkoutRecId.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        orderAdapter = OrderAdapter(
            R.layout.item_order_product_list_single_row, requireContext(), orderedList!!,
            binding.totalPriceTV,
            binding.contextView,
            binding.totalRPTV,
            binding.adjustedRP,
            binding.adjustedTotal,
            binding.payableTotal,
            binding.userRPLayout
        )

        orderItemListPresenter!!.orderItemDataLoad(user!!.username)
    }

    private fun eshopType() {
        shopTypePresenter!!.shopTypeDataLoad(user!!.username)
    }

    @SuppressLint("SetTextI18n")
    private fun getLocation(id: String, `val`: String) {
        if (id == ConstantValues.location.COUNTRY) {
            countryList = ArrayList()
            countryListId = ArrayList()
            stringAdapter =
                ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, countryList!!)
            binding.countryTxt.setAdapter(stringAdapter)
            addressID = id
            locationListPresenter!!.locationDataLoad(id, `val`)

        } else if (id == ConstantValues.profile.DIVISION) {
            divList = ArrayList()
            divListId = ArrayList()
            stringAdapter =
                ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, divList!!)
            binding.divisionTxt.setAdapter(stringAdapter)
            addressID = id
            locationListPresenter!!.locationDataLoad(id, `val`)
        } else if (id == ConstantValues.profile.DISTRICT) {
            disList = ArrayList()
            disListId = ArrayList()
            stringAdapter =
                ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, disList!!)
            binding.districtTxt.setAdapter(stringAdapter)
            addressID = id
            locationListPresenter!!.locationDataLoad(id, `val`)
        } else if (id == ConstantValues.profile.THANA) {
            thanaList = ArrayList()
            thanaListId = ArrayList()

            stringAdapter =
                ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, thanaList!!)

            binding.thanaTxt.setAdapter(stringAdapter)
            addressID = id
            locationListPresenter!!.locationDataLoad(id, `val`)
        } else if (id == ConstantValues.profile.UNION) {
            unionList = ArrayList()
            unionListId = ArrayList()
            stringAdapter =
                ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, unionList!!)
            binding.unionTxt.setAdapter(stringAdapter)
            addressID = id
            locationListPresenter!!.locationDataLoad(id, `val`)
            //getEshop(thanaId, ConstantValues.location.THANA)
        }
    }

    private fun getEshop(location: String, locType: String) {
        eshopList = ArrayList()
        binding.shopRecView.layoutManager = LinearLayoutManager(context)
        binding.shopRecView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        eshopListAdapter = OrderEshopListAdapter(context, eshopList)
        binding.shopRecView.adapter = eshopListAdapter
        eshopListPresenter!!.eshopDataLoad(user!!.username, location, locType, "")
    }

    fun switchFragment(fragment: Fragment?, tag: String?) {
        val fm = parentFragmentManager
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment!!, tag).addToBackStack(tag)
            .commit()
    }


    private fun showSnackBar(msg: String) {
        val snackbar = Snackbar.make(binding.parentLayout, msg, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // TODO Add your menu entries here

        inflater.inflate(R.menu.manage_order, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_orders -> switchFragment(OrderFragment(), "OrderFragment")
            R.id.menu_applied -> switchFragment(AppliedFragment(), "AppliedFragment")
            R.id.menu_delivered -> switchFragment(DeliveredFragment(), "DeliveredFragment")
            R.id.menu_received -> switchFragment(ReceivedFragment(), "ReceivedFragment")
        }
        return true
    }


    @SuppressLint("DefaultLocale")
    override fun onOrderItemListDataLoad(orderItemModelHeads: OrderItemModelHead) {
        orderedList!!.addAll(orderItemModelHeads.data)
        orderAdapter!!.notifyDataSetChanged()

        myPoint = orderItemModelHeads.myPoint.toDouble()
        binding.myPointTv.text = String.format("%.02f", myPoint)

        if (!orderItemModelHeads.data.isEmpty()) {
            binding.calculationLayout.visibility = View.VISIBLE
            totalPrice = orderItemModelHeads.totalAmount.toDouble()
            totalRP = orderItemModelHeads.totalRp.toDouble()
            adjustedPrice = orderItemModelHeads.adjAmount.toDouble()
            adjustedRP = orderItemModelHeads.adjRp.toDouble()
            payablePrice = orderItemModelHeads.payable.toDouble()
            binding.totalPriceTV.text = String.format("%.02f", totalPrice)
            binding.totalRPTV.text = String.format("%.02f", totalRP)
            if (myPoint < adjustedRP || myPoint <= 0) {
                binding.userRPLayout.visibility = View.GONE
            } else if (myPoint >= adjustedRP) {
                binding.userRPLayout.visibility = View.VISIBLE
            }

            binding.checkoutRecId.visibility = View.VISIBLE

            if (orderedList!!.isNotEmpty()) {
                binding.myRPLayout.visibility = View.VISIBLE
                binding.checkoutRecId.adapter = orderAdapter
                eshopType()
            }
        }
    }

    override fun onOrderItemListStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
        binding.calculationLayout.visibility = View.GONE
    }

    override fun onOrderItemListStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onOrderItemListShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onLocationListDataLoad(locationModels: List<LocationModel>) {
        if (addressID == ConstantValues.profile.COUNTRY) {
            loadingDialog!!.dismissDialog()
            for (i in locationModels.indices) {
                countryList!!.add(locationModels[i].value)
                countryListId!!.add(locationModels[i].id)
                binding.division.hint = "Select Division"
                divListId=null
            }
            if (countryList!!.isEmpty()) {
                countryList!!.add("Not Applicable")
            }
        }
        if (addressID == ConstantValues.profile.DIVISION) {
            //binding.division.hint = "Select Division"
            for (i in locationModels.indices) {
                divList!!.add(locationModels[i].value)
                divListId!!.add(locationModels[i].id)
            }


            if (divList!!.isEmpty()) {
                disList!!.add("Not Applicable")
            }
        }
        if (addressID == ConstantValues.profile.DISTRICT) {
            for (i in locationModels.indices) {
                disList!!.add(locationModels[i].value)
                disListId!!.add(locationModels[i].id)
            }
            if (disList!!.isEmpty()) {
                disList!!.add("Not Applicable")
            }
        }
        if (addressID == ConstantValues.profile.THANA) {
            for (i in locationModels.indices) {
                thanaList!!.add(locationModels[i].value)
                thanaListId!!.add(locationModels[i].id)
            }
            if (thanaList!!.isEmpty()) {
                thanaList!!.add("Not Applicable")
            }
        }
        if (addressID == ConstantValues.profile.UNION) {
            for (i in locationModels.indices) {
                unionList!!.add(locationModels[i].value)
                unionListId!!.add(locationModels[i].id)
            }
            if (unionList!!.isEmpty()) {
                unionList!!.add("Not Applicable")
            }
        }
        stringAdapter!!.notifyDataSetChanged()
    }

    override fun onLocationListStartLoading() {
        if (addressID != ConstantValues.profile.COUNTRY) {
            loadingDialog!!.startLoadingAlertDialog()
        }
    }

    override fun onLocationListStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onLocationListShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onShopTypeDataLoad(shopTypeModel: ShopTypeModel) {
        if (shopTypeModel.sid == "0") {
            eshopType = "teleShop"
            binding.otherShopLayout.visibility = View.GONE
            binding.country.visibility = View.VISIBLE
            binding.searchShopButton.visibility = View.VISIBLE
            getLocation(ConstantValues.location.COUNTRY, "")
        } else {
            eshopType = "preShop"
            binding.country.visibility = View.GONE
            eshopID = shopTypeModel.id
            binding.otherShopLayout.visibility = View.VISIBLE
            binding.shopName.text = shopTypeModel.name
            binding.shopNumber.text = shopTypeModel.mobile
            binding.shopAddress.text = shopTypeModel.address
        }
    }

    override fun onShopTypeStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onShopTypeStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onShopTypeShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onEshopListDataLoad(eshopListModels: List<EshopListModel>) {
        if (eshopListModels.isNotEmpty()) {
            eshopList!!.clear()
            binding.shopRecView.visibility = View.VISIBLE
            eshopList!!.addAll(eshopListModels)
            eshopListAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onEshopListStartLoading() {
        //  loadingDialog.startLoadingAlertDialog();
    }

    override fun onEshopListStopLoading() {
        //loadingDialog.dismissDialog();
    }

    override fun onEshopListShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onOrderConfirmDataLoad(orderConfirmModel: OrderConfirmModel) {
        if (orderConfirmModel.error == 0) {
           // switchFragment(HomeFragment(), "HomeFragment")
            DialogHelper.showAlertDialog(
                context,
                "Order Confirmation!",
                orderConfirmModel.data.toString(),
                "OK",
                "Home",
                { dialog: DialogInterface, which: Int ->
                    switchFragment(HomeFragment(), "HomeFragment")
                    dialog.dismiss()
                },
                { dialog: DialogInterface, which: Int ->
                    // Handle the negative button click
                    dialog.dismiss()
                    switchFragment(HomeFragment(), "HomeFragment")
                }
            )
            //showSnackBar(orderConfirmModel.data.toString())
            sName = ""
            sNumber = ""
            sAddress = ""
        }
    }

    override fun onOrderConfirmStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onOrderConfirmStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onOrderConfirmShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    override fun onProfileDetailsDataLoad(profileDetailsHead: ProfileDetailsHead) {
        address = profileDetailsHead.data.address
        val word = profileDetailsHead.data.word
        val union = profileDetailsHead.data.union
        val thana = profileDetailsHead.data.thana
        val district = profileDetailsHead.data.district
        val division = profileDetailsHead.data.division
        binding!!.shippingAddressET.editText!!.setText(address)
    }

    override fun onProfileDetailsStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onProfileDetailsStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onProfileDetailsShowMessage(errmsg: String) {
        Toast.makeText(context, errmsg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmField
        var sName: String = ""

        @JvmField
        var sNumber: String = ""

        @JvmField
        var sAddress: String = ""

        @JvmField
        var selectHoise: Boolean = false

        @JvmField
        var eshopID: String = ""
        var eshopList: ArrayList<EshopListModel>? = null

        var totalPrice: Double = 0.0
        var totalRP: Double = 0.0
        var adjustedPrice: Double = 0.0
        var adjustedRP: Double = 0.0
        var payablePrice: Double = 0.0
        var myPoint: Double = 0.0
        var ifAdjust: Int = 0
    }
}