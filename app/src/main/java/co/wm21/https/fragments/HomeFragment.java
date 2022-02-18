package co.wm21.https.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.wm21.https.ProductRecyclerViewAdapter;
import co.wm21.https.R;
import co.wm21.https.SliderItem;
import co.wm21.https.adapters.SliderAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuAdapter;
import co.wm21.https.adapters.item_menu.ItemMenuView;
import co.wm21.https.databinding.FragmentMainHomeBinding;
import co.wm21.https.helpers.*;
import co.wm21.https.placeholder.PlaceholderContent;

public class HomeFragment extends Fragment {

//    private static final String ARG_COLUMN_COUNT = "column-count";
//    private int mColumnCount = 1;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
//    }

    List<SliderItem> sliderItemList;
//    ArrayList<ProductView> productViews;
    static API API;
    static User user;
    SliderAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMainHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_home, container, false);
        View view = binding.getRoot();

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new ProductRecyclerViewAdapter(PlaceholderContent.ITEMS));
//        }
        if (!user.getSession().isLoggedIn())
            binding.ecommerceList.setVisibility(View.GONE);
        else {
            ArrayList<ItemMenuView> itemMenuViews = new ArrayList<>(Arrays.asList(
                    new ItemMenuView("Facebook", R.drawable.ic_facebook),
                    new ItemMenuView("Youtube", R.drawable.ic_youtube_dark),
                    new ItemMenuView("Order", R.drawable.ic_orders),
                    new ItemMenuView("Applied", R.drawable.ic_heart_outline),
                    new ItemMenuView("Delivered", R.drawable.ic_location_solid),
                    new ItemMenuView("Received", R.drawable.ic_shirt_outline)));

            binding.ecommerceList.setAdapter(new ItemMenuAdapter(getContext(), itemMenuViews, R.layout.item_category_layout_2));
        }

        sliderItemList = new ArrayList<>();
//        productViews = new ArrayList<>();

        adapter = new SliderAdapter(getContext());
        return view;
    }
}