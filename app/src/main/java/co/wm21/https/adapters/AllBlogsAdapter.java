package co.wm21.https.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.R;
import co.wm21.https.databinding.ItemAllBlogSingleRowBinding;
import co.wm21.https.databinding.LayoutItemBlogBinding;
import co.wm21.https.model.Blogs;

public class AllBlogsAdapter extends RecyclerView.Adapter<AllBlogsAdapter.viewHolder> {
    Context context;
    ArrayList<Blogs> blogsArrayList;
    @LayoutRes
    int layout;
    ItemAllBlogSingleRowBinding binding;

    public AllBlogsAdapter(Context context, ArrayList<Blogs> blogsArrayList, int layout) {
        this.context = context;
        this.blogsArrayList = blogsArrayList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemAllBlogSingleRowBinding.inflate(LayoutInflater.from(context), parent, false);
        return new viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Blogs blogs = blogsArrayList.get(position);
        Picasso.get().load(ConstantValues.web_url + "image/blog/" + blogs.getBlogImage()).error(R.drawable.ic_image_temp).into(holder.bloggerImage);
        holder.bloggerName.setText(blogs.getName());
        holder.blogSubject.setText(blogs.getSubject());
        holder.text.setText(blogs.getText());
        holder.date.setText(blogs.getBlogDate());
    }

    @Override
    public int getItemCount() {
        return blogsArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView  bloggerName, blogSubject,date,text;
        ImageView bloggerImage;
        public viewHolder(@NonNull ItemAllBlogSingleRowBinding itemView) {
            super(itemView.getRoot());
            bloggerName = itemView.bloggerName;
            blogSubject = itemView.subject;
            bloggerImage = itemView.bloggerImage;
            date = itemView.blogDate;
            text = itemView.textTv;
        }
    }
}
