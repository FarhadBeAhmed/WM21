package co.wm21.https.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.example.SlideImage;

import java.util.ArrayList;
import java.util.List;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.ProductModel;
import co.wm21.https.R;

import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.view.adapters.AllHotDealAdapter;
import co.wm21.https.view.adapters.ShopsAdapter;
import co.wm21.https.view.adapters.SliderAdapter;
import co.wm21.https.view.adapters.product.ProductView;
import co.wm21.https.databinding.FragmentAllHotDealBinding;
import co.wm21.https.view.activities.mlm.company.company_fragments.AboutUsFragment;
import co.wm21.https.view.activities.mlm.company.company_fragments.ContactUsFragment;
import co.wm21.https.utils.dialog.LoadingDialog;
import co.wm21.https.presenter.interfaces.OnHotProductView;
import co.wm21.https.presenter.HotProductPresenter;

public class AllHotDealFragment extends Fragment implements OnHotProductView {

    FragmentAllHotDealBinding binding;
    ArrayList<ProductView> productList;
    co.wm21.https.FHelper.API api;
    ShopsAdapter adapter;
    List<SlideImage> sliderItemList;
    SliderAdapter sliderAdapter;
    AllHotDealAdapter productAdapter;
    HotProductPresenter hotProductPresenter;
    ArrayList<ProductModel> hotProductViews;
    LoadingDialog loadingDialog;
    boolean isGrid = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentAllHotDealBinding.inflate(getLayoutInflater());
        return binding.getRoot();}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog=new LoadingDialog(getActivity());
        api = ConstantValues.getAPI();
        sliderItemList = new ArrayList<>();
        sliderAdapter = new SliderAdapter(getContext(),sliderItemList);
        binding.shimmerImageSlider.setVisibility(View.VISIBLE);
        binding.imageSlider.setVisibility(View.GONE);


        hotProductPresenter=new HotProductPresenter(this);


        binding.footerId.MyAccExpandableLayout.collapse();
        binding.footerId.CustomerExpandableLayout.collapse();
        binding.footerId.infoExpandableLayout.collapse();
        binding.footerId.footerCompany.setOnClickListener(v -> {
            if (binding.footerId.infoExpandableLayout.isExpanded()) {
                binding.footerId.infoExpandableLayout.collapse();
                binding.footerId.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.infoExpandableLayout.expand();
                binding.footerId.infExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.footerId.footerMyAccount.setOnClickListener(v -> {
            if (binding.footerId.MyAccExpandableLayout.isExpanded()) {
                binding.footerId.MyAccExpandableLayout.collapse();
                binding.footerId.MyAccExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.MyAccExpandableLayout.expand();
                binding.footerId.MyAccExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.footerId.footerCustomer.setOnClickListener(v -> {
            if (binding.footerId.CustomerExpandableLayout.isExpanded()) {
                binding.footerId.CustomerExpandableLayout.collapse();
                binding.footerId.CustomerExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_24);
            } else {
                binding.footerId.CustomerExpandableLayout.expand();
                binding.footerId.CustomerExpandIcon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            }
        });
        binding.footerId.aboutUs.setOnClickListener(v -> {
            switchFragment(new AboutUsFragment(), "AboutUsFragment");
        });
        binding.footerId.contactUs.setOnClickListener(v -> {
            switchFragment(new ContactUsFragment(), "ContactUsFragment");
        });


        hotProduct1();

    }



    private void hotProduct1() {
        hotProductViews = new ArrayList<>();
        binding.productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        productAdapter=new AllHotDealAdapter( getContext(),hotProductViews, R.layout.layout_item_product_hot_deal);
        binding.productRecyclerView.setAdapter(productAdapter);
        hotProductPresenter.getHotProduct(30);


    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = ((MainActivity) getActivity()).getSupportFragmentManager();
        /*for (int i = 0; i < fm.getBackStackEntryCount(); ++i)
            fm.popBackStack();*/
        FragmentTransaction childFragTrans = fm.beginTransaction();
        childFragTrans.replace(R.id.fragmentContainer, fragment, tag);
        childFragTrans.addToBackStack(tag);
        childFragTrans.commit();
    }

    @Override
    public void onHotProductDataLoaded(List<ProductModel> items) {
        loadingDialog.dismissDialog();
        binding.shimmerProduct.setVisibility(View.GONE);
        binding.productRecyclerView.setVisibility(View.VISIBLE);
        hotProductViews.addAll(items);
        productAdapter.notifyDataSetChanged();

    }

    @Override
    public void onHotProductStartLoading() {
        loadingDialog.startLoadingAlertDialog();
    }

    @Override
    public void onHotProductStopLoading() {
        loadingDialog.dismissDialog();
    }

    @Override
    public void onHotProductShowMessage(String errMsg) {
        loadingDialog.dismissDialog();
    }
}