package co.wm21.https.view.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import co.wm21.https.FHelper.API
import co.wm21.https.FHelper.ConstantValues
import co.wm21.https.FHelper.networks.Models.CartItems
import co.wm21.https.FHelper.networks.Models.CartItemsHead
import co.wm21.https.FHelper.networks.Models.CheckoutModel
import co.wm21.https.R
import co.wm21.https.databinding.FragmentMainCartBinding
import co.wm21.https.helpers.User
import co.wm21.https.presenter.CartItemListPresenter
import co.wm21.https.presenter.CheckoutPresenter
import co.wm21.https.presenter.interfaces.OnCartItemListView
import co.wm21.https.presenter.interfaces.OnCheckoutView
import co.wm21.https.utils.dialog.DialogHelper
import co.wm21.https.utils.dialog.LoadingDialog
import co.wm21.https.view.activities.MainActivity
import co.wm21.https.view.adapters.cart.CartAdapter
import co.wm21.https.view.fragments.manageOrder.OrderFragment
import com.google.android.material.snackbar.Snackbar

class CartFragment : Fragment(), OnCartItemListView, OnCheckoutView {
    //MainActivity mainActivity;
    var binding: FragmentMainCartBinding? = null
    var cartAdapter: CartAdapter? = null

    var api: API? = null
    var uId: String = ""
    var user: User? = null
    var cartViewsModels: CartItemsHead? = null
    var cartItemList: ArrayList<CartItems>? = null
    var cartItemListPresenter: CartItemListPresenter? = null

    //ProgressDialog progressBar;
    var loadingDialog: LoadingDialog? = null
    var clickView: View? = null
    var checkoutPresenter: CheckoutPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_cart, container, false)
        api = ConstantValues.getAPI()
        user = User(context)
        @SuppressLint("HardwareIds") val `val` =
            Settings.Secure.getString(requireActivity().contentResolver, Settings.Secure.ANDROID_ID)
        uId = `val`.replace("[\\D]".toRegex(), "")
        loadingDialog = LoadingDialog(activity)
        //getSelectedProducts();
        binding!!.checkOutBtn.setOnClickListener { view: View -> this.checkout(view) }
        binding!!.keepShopping.setOnClickListener { view: View -> this.keepShopping(view) }
        cartItemListPresenter = CartItemListPresenter(this)
        checkoutPresenter = CheckoutPresenter(this)
        selectedProducts
        return binding!!.root
    }

    private fun keepShopping(view: View) {
        //switchFragment(new HomeFragment(), "home");
        startActivity(Intent(activity, MainActivity::class.java))
    }

    private fun checkout(view: View) {
        clickView = view
        if (!cartItemList!!.isEmpty()) {
            if (user!!.username.isEmpty()) {
                DialogHelper.showAlertDialog(
                    context,
                    "Login Only!!",
                    "Sorry, You need to be logged in To Checkout.",
                    "OK",
                    "LOGIN",
                    { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    },
                    { dialog: DialogInterface, which: Int ->
                        // Handle the negative button click
                        dialog.dismiss()
                        switchFragment(LoginFragment(), "LoginFragment")
                    }
                )
            } else {
                checkoutPresenter!!.checkoutDataLoad(uId, user!!.username)
            }
        } else {
// Example usage of the reusable alert dialog
            DialogHelper.showAlertDialog(
                context,
                "Empty Card!!",
                "Sorry, You Have No Item To Checkout.",
                "OK",
                "SHOP",
                { dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                },
                { dialog: DialogInterface, which: Int ->
                    // Handle the negative button click
                    dialog.dismiss()
                    switchFragment(HomeFragment(), "HomeFragment")
                }
            )
        }
    }

    @get:SuppressLint("HardwareIds", "SetTextI18n", "DefaultLocale")
    val selectedProducts: Unit
        get() {
            cartItemList = ArrayList()
            binding!!.cartProducts.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            cartAdapter = CartAdapter(
                R.layout.layout_item_cart,
                requireContext(),
                cartItemList!!,
                binding!!.totalPrice,
                binding!!.totalRP,
                binding!!.cartFragmentLayout
            )
            binding!!.cartProducts.adapter = cartAdapter

            cartItemListPresenter!!.CartItemDataLoad(uId)
        }

    fun switchFragment(fragment: Fragment?, tag: String?) {
        val fm = parentFragmentManager
        for (i in 0 until fm.backStackEntryCount) fm.popBackStack()
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment!!, tag).addToBackStack(tag)
            .commit()
    }

    private fun showSnackBar(msg: String) {
        val snackbar = Snackbar.make(binding!!.cartFragmentLayout, msg, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }


    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCartItemListDataLoad(cartItems: CartItemsHead) {
        cartItemList!!.addAll(cartItems.data)
        cartAdapter!!.notifyDataSetChanged()
        MainActivity.badge.clearNumber()
        MainActivity.badge.number = cartItems.data.size
        if (!cartItems.data.isEmpty()) {
            binding!!.totalPrice.text = "TK " + cartItems.total_price
            binding!!.totalRP.text = "RP " + cartItems.total_point
            binding!!.cartProducts.visibility = View.VISIBLE
            binding!!.emptyCart.visibility = View.GONE
        } else {
            binding!!.cartProducts.visibility = View.GONE
            binding!!.emptyCart.visibility = View.VISIBLE
        }
    }

    override fun onCartItemListStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onCartItemListStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onCartItemListShowMessage(errmsg: String) {
        // Toast.makeText(getActivity(), errmsg, Toast.LENGTH_SHORT).show();
    }

    override fun onCheckoutDataLoad(checkoutModel: CheckoutModel) {
        if (checkoutModel.status == 200) {
            switchFragment(OrderFragment(), "OrderFragment")
        }
    }

    override fun onCheckoutStartLoading() {
        loadingDialog!!.startLoadingAlertDialog()
    }

    override fun onCheckoutStopLoading() {
        loadingDialog!!.dismissDialog()
    }

    override fun onCheckoutShowMessage(errmsg: String) {
        val builder = AlertDialog.Builder(context)
        val viewGroup = clickView!!.findViewById<ViewGroup>(android.R.id.content)
        val dialogView = LayoutInflater.from(context)
            .inflate(R.layout.layout_worning_cart_item, viewGroup, false)
        val ok = dialogView.findViewById<RelativeLayout>(R.id.okBtnId)
        val msg = dialogView.findViewById<TextView>(R.id.textMsg)
        msg.text = errmsg
        builder.setView(dialogView)
        val alertDialog = builder.create()
        alertDialog.show()
        ok.setOnClickListener { view1: View? ->
            alertDialog.dismiss()
            switchFragment(CartFragment(), "CartFragment")
        }
    }
}