package co.wm21.https.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.FHelper.networks.Models.EshopListModel;
import co.wm21.https.R;
import co.wm21.https.adapters.ItemClickListener;
import co.wm21.https.adapters.OrderEshopListAdapter;
import co.wm21.https.adapters.SearchOrderEshopListAdapter;
import co.wm21.https.databinding.ActivitySearchShopBinding;
import co.wm21.https.dialog.LoadingDialog;
import co.wm21.https.fragments.manageOrder.OrderFragment;
import co.wm21.https.helpers.User;
import co.wm21.https.interfaces.OnEshopListView;
import co.wm21.https.model.OrderEshopModel;
import co.wm21.https.presenter.EshopListPresenter;

public class SearchShopActivity extends AppCompatActivity implements OnEshopListView {
    ActivitySearchShopBinding binding;
    API api;
    User user;
    private SearchOrderEshopListAdapter eshopListAdapter;
    LoadingDialog loadingDialog;
    EshopListPresenter eshopListPresenter;
    public static ArrayList<EshopListModel>eshopList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySearchShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        api=ConstantValues.getAPI();
        user=new User(this);
        loadingDialog = new LoadingDialog(this);
        eshopListPresenter=new EshopListPresenter(this);

        getEshop("","all");

        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                eshopListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    private void getEshop(String location, String locType) {

        eshopList=new ArrayList<>();
        binding.shopRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.shopRecView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        eshopListAdapter = new SearchOrderEshopListAdapter(getApplicationContext(), eshopList).addOnClickListener((View, position) -> {
            OrderFragment.sName=eshopList.get(position).getName();
            OrderFragment.sNumber=eshopList.get(position).getMobile();
            OrderFragment.sAddress=eshopList.get(position).getAddress();
            OrderFragment.selectHoise=true;
            OrderFragment.eshopID=eshopList.get(position).getId();
            onBackPressed();

        });
        binding.shopRecView.setAdapter(eshopListAdapter);

        eshopListPresenter.eshopDataLoad(user.getUsername(), location, locType,"");



//        ArrayList<OrderEshopModel> eshopList = new ArrayList<>();
//
//        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(api.order_eshop(user.getUsername(), user.getPassword(), location, locType, response -> {
//            try {
//                JSONArray jsonArray = response.getJSONArray("shops");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject object = jsonArray.getJSONObject(i);
//                    eshopList.add(new OrderEshopModel(
//                            object.getString(ConstantValues.admin_login.ID),
//                            object.getString(ConstantValues.admin_login.SHOP_NAME),
//                            object.getString(ConstantValues.admin_login.MOBILE),
//                            object.getString(ConstantValues.location.ADDRESS),
//                            object.getString(ConstantValues.location.WORD),
//                            object.getString(ConstantValues.location.UNION),
//                            object.getString(ConstantValues.location.THANA),
//                            object.getString(ConstantValues.location.DISTRICT),
//                            object.getString(ConstantValues.location.DIVISION),
//                            object.getString(ConstantValues.location.COUNTRY),
//                            object.getString(ConstantValues.location.FULL_ADDRESS)
//                    ));
//                }
//
//                if (eshopList.size() > 0) {
//                    binding.shopRecView.setVisibility(View.VISIBLE);
//                    binding.shopRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    binding.shopRecView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
//                    eshopListAdapter = new SearchOrderEshopListAdapter(getApplicationContext(), eshopList).addOnClickListener((View, position) -> {
//                        OrderFragment.sName=eshopList.get(position).getName();
//                        OrderFragment.sNumber=eshopList.get(position).getMobile();
//                        OrderFragment.sAddress=eshopList.get(position).getAddress();
//                        OrderFragment.selectHoise=true;
//                        OrderFragment.eshopID=eshopList.get(position).getId();
//                        onBackPressed();
//
//                    });
//                    binding.shopRecView.setAdapter(eshopListAdapter);
//                }
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }));


    }

    @Override
    public void onEshopListDataLoad(List<EshopListModel> eshopListModels) {
        if (eshopListModels.size()>0){
            binding.shopRecView.setVisibility(View.VISIBLE);
            eshopList.addAll(eshopListModels);
            eshopListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onEshopListStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onEshopListStopLoading() {
        loadingDialog.dismissDialog();

    }

    @Override
    public void onEshopListShowMessage(String errmsg) {

    }
}