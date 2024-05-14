package co.wm21.https.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.CartItems;
import co.wm21.https.FHelper.networks.Models.CartItemsHead;
import co.wm21.https.FHelper.networks.Models.CheckoutModel;
import co.wm21.https.R;
import co.wm21.https.activities.MainActivity;
import co.wm21.https.adapters.cart.CartAdapter;
import co.wm21.https.adapters.cart.CartModel;
import co.wm21.https.databinding.FragmentMainCartBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.fragments.manageOrder.OrderFragment;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.OnCartItemListView;
import co.wm21.https.interfaces.OnCheckoutView;
import co.wm21.https.presenter.CartItemListPresenter;
import co.wm21.https.presenter.CheckoutPresenter;

public class CartFragment extends Fragment implements OnCartItemListView, OnCheckoutView {
    //MainActivity mainActivity;
    FragmentMainCartBinding binding;
    CartAdapter cartAdapter;

    API api;
    String uId = "";
    User user;
    CartItemsHead cartViewsModels;
    ArrayList<CartItems> cartItemList;
    CartItemListPresenter cartItemListPresenter;
    //ProgressDialog progressBar;
    LoadingDialog loadingDialog;
    View clickView;
    CheckoutPresenter checkoutPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_cart, container, false);
        api = ConstantValues.getAPI();
        user = new User(getContext());
        @SuppressLint("HardwareIds")
        String val = Settings.Secure.getString(requireActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        uId = val.replaceAll("[\\D]", "");
        loadingDialog = new LoadingDialog(getActivity());
        //getSelectedProducts();
        binding.checkOutBtn.setOnClickListener(this::checkout);
        cartItemListPresenter = new CartItemListPresenter(this);
        checkoutPresenter = new CheckoutPresenter(this);
        getSelectedProducts();
        return binding.getRoot();
    }

    private void checkout(View view) {
        clickView = view;
        checkoutPresenter.checkoutDataLoad(uId, user.getUsername());
    }

    @SuppressLint({"HardwareIds", "SetTextI18n", "DefaultLocale"})
    public void getSelectedProducts() {
        cartItemList = new ArrayList<>();
        binding.cartProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(R.layout.layout_item_cart, getContext(), cartItemList, binding.totalPrice, binding.totalRP, binding.cartFragmentLayout);
        binding.cartProducts.setAdapter(cartAdapter);

        cartItemListPresenter.CartItemDataLoad(uId);


    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }

    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cartFragmentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onCartItemListDataLoad(CartItemsHead cartItems) {
        cartItemList.addAll(cartItems.getData());
        cartAdapter.notifyDataSetChanged();
        MainActivity.badge.clearNumber();
        MainActivity.badge.setNumber(cartItems.getData().size());
        if (!cartItems.getData().isEmpty()) {
            binding.totalPrice.setText("TK " + cartItems.getTotal_price());
            binding.totalRP.setText("RP " + cartItems.getTotal_point());
            binding.cartProducts.setVisibility(View.VISIBLE);
            binding.emptyCart.setVisibility(View.GONE);
        } else {
            binding.cartProducts.setVisibility(View.GONE);
            binding.emptyCart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCartItemListStartLoading() {
        loadingDialog.startLoadingAlertDialog();

    }

    @Override
    public void onCartItemListStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onCartItemListShowMessage(String errmsg) {
        Toast.makeText(getActivity(), errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckoutDataLoad(CheckoutModel checkoutModel) {
        if (checkoutModel.getError() == 0) {
            switchFragment(new OrderFragment(), "OrderFragment");
        } else if (checkoutModel.getError() == 2) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            ViewGroup viewGroup = clickView.findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.layout_worning_cart_item, viewGroup, false);
            RelativeLayout ok = dialogView.findViewById(R.id.okBtnId);
            TextView msg = dialogView.findViewById(R.id.textMsg);
            msg.setText(checkoutModel.getData());
            builder.setView(dialogView);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            ok.setOnClickListener(view1 -> {
                alertDialog.dismiss();
                switchFragment(new CartFragment(), "CartFragment");

            });
        }
        showSnackBar(checkoutModel.getData());
    }

    @Override
    public void onCheckoutStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onCheckoutStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onCheckoutShowMessage(String errmsg) {
        Toast.makeText(getActivity(), errmsg, Toast.LENGTH_SHORT).show();
    }
}