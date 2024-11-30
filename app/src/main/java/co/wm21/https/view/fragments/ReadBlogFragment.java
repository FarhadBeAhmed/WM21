package co.wm21.https.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import co.wm21.https.FHelper.API;
import co.wm21.https.FHelper.ConstantValues;
import co.wm21.https.FHelper.MySingleton;
import co.wm21.https.R;
import co.wm21.https.databinding.FragmentReadBlogBinding;


public class ReadBlogFragment extends Fragment {

    FragmentReadBlogBinding binding;
    String blogId;
    API api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentReadBlogBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        api=ConstantValues.getAPI();

        Bundle bundle=this.getArguments();
        if (bundle!=null){
            blogId=bundle.getString(ConstantValues.ARGUMENT1);
            blog(blogId);
        }

    }

    private void blog(String blogId) {
        binding.shimmerBloggerImage.setVisibility(View.VISIBLE);
        binding.shimmerSubject.setVisibility(View.VISIBLE);
        binding.shimmerTextTv.setVisibility(View.VISIBLE);
        binding.bloggerImage.setVisibility(View.GONE);
        binding.textTv.setVisibility(View.GONE);
        binding.subject.setVisibility(View.GONE);
        MySingleton.getInstance(getContext()).addToRequestQueue(api.singleBlog(blogId,response -> {
            try {
                if (response.getString(ConstantValues.ERROR).equals("0")){
                    Picasso.get().load(ConstantValues.web_url + "image/blog/" + response.getString(ConstantValues.blog.BLOG_IMAGE)).error(R.drawable.ic_image_temp).into(binding.bloggerImage);
                    binding.textTv.setText(response.getString(ConstantValues.blog.TEXT));
                    binding.subject.setText(response.getString(ConstantValues.blog.SUBJECT));
                    binding.blogDate.setText(response.getString(ConstantValues.blog.DATE));
                    binding.bloggerName.setText(response.getString(ConstantValues.blog.NAME));
                    binding.shimmerBloggerImage.setVisibility(View.GONE);
                    binding.shimmerSubject.setVisibility(View.GONE);
                    binding.shimmerTextTv.setVisibility(View.GONE);
                    binding.bloggerImage.setVisibility(View.VISIBLE);
                    binding.textTv.setVisibility(View.VISIBLE);
                    binding.subject.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }));


    }

}