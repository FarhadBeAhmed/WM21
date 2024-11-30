package co.wm21.https.view.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.view.activities.MainActivity;
import co.wm21.https.databinding.LayoutItemBlogBinding;
import co.wm21.https.view.fragments.ReadBlogFragment;

public class BlogsAdapter extends RecyclerView.Adapter<BlogsAdapter.viewHolder> {

    Context context;
    ArrayList<BlogsModel> blogsArrayList;
    @LayoutRes
    int layout;
    LayoutItemBlogBinding binding;

    public BlogsAdapter(Context context, ArrayList<BlogsModel> blogsArrayList, @LayoutRes int layout) {
        this.context = context;
        this.blogsArrayList = blogsArrayList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public BlogsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = LayoutItemBlogBinding.inflate(LayoutInflater.from(context), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogsAdapter.viewHolder holder, int position) {
        BlogsModel blogs = blogsArrayList.get(position);
        //Picasso.get().load(ConstantValues.URL + "new/sys/stores/" + blogs.getImage()).into(holder.bloggerImage);
        holder.bloggerName.setText(blogs.getName());
        holder.blogSubject.setText(blogs.getSubject());
        holder.readMoreBtn.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ReadBlogFragment readBlogFragment = new ReadBlogFragment();
            bundle.putString(ConstantValues.ARGUMENT1, blogs.getSerial());
            readBlogFragment.setArguments(bundle);
            switchFragment(readBlogFragment,"ReadBlogFragment");
        });


    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        fm.beginTransaction().replace(((MainActivity) context).binding.fragmentContainer.getId(), fragment, tag).addToBackStack(tag).commit();
    }


    @Override
    public int getItemCount() {
        return blogsArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView readMoreBtn, bloggerName, blogSubject;
        ImageView bloggerImage;

        public viewHolder(@NonNull LayoutItemBlogBinding itemView) {
            super(itemView.getRoot());
            readMoreBtn = itemView.readMoreBtn;
            bloggerName = itemView.bloggerName;
            blogSubject = itemView.subject;
            bloggerImage = itemView.bloggerImage;

        }
    }
}
