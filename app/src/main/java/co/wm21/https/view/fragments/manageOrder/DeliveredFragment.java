package co.wm21.https.view.fragments.manageOrder;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.DeliveryItemsModel;
import co.wm21.https.R;
import co.wm21.https.view.adapters.DeliveryAdapter;
import co.wm21.https.databinding.FragmentDeliveredBinding;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.helpers.User;
import co.wm21.https.presenter.interfaces.OnDeliveryItemsView;
import co.wm21.https.presenter.DeliveryItemsPresenter;

public class DeliveredFragment extends Fragment implements OnDeliveryItemsView {
    FragmentDeliveredBinding binding;

    private API api;
    private User user;
    private DeliveryAdapter deliveryAdapter;
    double subtotal=0,myPoint=0;
    private ArrayList<DeliveryItemsModel> deliveryList;

    DeliveryItemsPresenter deliveryItemsPresenter;
    LoadingDialog loadingDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_delivered, container, false);
        api=ConstantValues.getAPI();
        user=new User(getContext());
        deliveryItemsPresenter=new DeliveryItemsPresenter(this);
        loadingDialog=new LoadingDialog(getActivity());
        deliveryProduct();
        return binding.getRoot();
    }

    @SuppressLint("DefaultLocale")
    private void deliveryProduct() {
        deliveryList = new ArrayList<>();
        binding.deliveryProductRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        deliveryAdapter = new DeliveryAdapter(R.layout.item_delivery_product_single_row, getContext(), deliveryList).addOnClickListener((View, position) -> {
            showSnackBar(deliveryList.get(position).getSerial());
        });

        binding.deliveryProductRecView.setAdapter(deliveryAdapter);

        deliveryItemsPresenter.deliveryItemsDataLoad(user.getUsername());

       /* MySingleton.getInstance(getContext()).addToRequestQueue(api.deliveryProduct(user.getUsername(), user.getPassword(), response -> {
            try {
                JSONArray categories = response.getJSONArray("allItems");
                for (int i = 0; i < categories.length(); i++) {
                    JSONObject jsonObject = categories.getJSONObject(i);
                    deliveryList.add(new DeliveryProductModel(
                            jsonObject.getString(ConstantValues.Cart.SERIAL),
                            jsonObject.getString(ConstantValues.Product.NAME),
                            jsonObject.getString(ConstantValues.Product.IMAGE),
                            jsonObject.getDouble(ConstantValues.Cart.PRICE),
                            jsonObject.getDouble(ConstantValues.Cart.TOTAL),
                            jsonObject.getDouble(ConstantValues.Cart.POINT),
                            jsonObject.getInt(ConstantValues.Cart.QUANTITY),
                            jsonObject.getString(ConstantValues.Cart.PRODUCT_ID),
                            jsonObject.getString(ConstantValues.applied.INVOICE),
                            jsonObject.getDouble(ConstantValues.applied.ADJUST),
                            jsonObject.getString(ConstantValues.applied.SHOP_NAME),
                            jsonObject.getString(ConstantValues.applied.SHOP_MOBILE),
                            jsonObject.getString(ConstantValues.applied.PAID),
                            jsonObject.getString(ConstantValues.applied.RECEIVED)
                    ));
                }

                binding.deliveryProductRecView.setVisibility(View.VISIBLE);
                binding.deliveryProductRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                deliveryAdapter = new DeliveryAdapter(R.layout.item_delivery_product_single_row, getContext(), deliveryList).addOnClickListener((View, position) -> {
                    showSnackBar(deliveryList.get(position).getSerial());
                });
                if (!deliveryList.isEmpty()) {
                    binding.deliveryProductRecView.setAdapter(deliveryAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));*/
    }

    private void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.parentLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getParentFragmentManager();
       /* for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();*/
        fm.beginTransaction().replace(R.id.fragmentContainer, fragment, tag).addToBackStack(tag).commit();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.manage_order, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_orders:
                switchFragment(new OrderFragment(),"OrderFragment");
                break;

            case R.id.menu_applied:
                switchFragment(new AppliedFragment(),"AppliedFragment");
                break;
            case R.id.menu_delivered:
                switchFragment(new DeliveredFragment(),"DeliveredFragment");
                break;
            case R.id.menu_received:
                switchFragment(new ReceivedFragment(),"ReceivedFragment");
                break;
        }
        return true;

    }

    @Override
    public void onDeliveryItemsDataLoad(List<DeliveryItemsModel> deliveryItemsModels) {
        if (deliveryItemsModels.size()>0)
        binding.deliveryProductRecView.setVisibility(View.VISIBLE);
        deliveryList.addAll(deliveryItemsModels);
        deliveryAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDeliveryItemsStartLoading() {
       loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onDeliveryItemsStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onDeliveryItemsShowMessage(String errmsg) {
        Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
    }
}