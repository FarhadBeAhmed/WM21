package co.wm21.https.utils;


import android.graphics.drawable.PictureDrawable;
import android.view.View;

import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * Listener that disables hardware acceleration for SVG rendering.
 */
public class SvgSoftwareLayerSetter implements RequestListener<PictureDrawable> {

    @Override
    public boolean onLoadFailed(GlideException e, Object model, Target<PictureDrawable> target, boolean isFirstResource) {
        // Log or handle the exception if needed
        e.printStackTrace(); // Optionally log the error
        return false; // Let Glide handle the failure
    }

    @Override
    public boolean onResourceReady(PictureDrawable resource, Object model, Target<PictureDrawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
        // Check if the target is an ImageViewTarget to access the ImageView
        if (target instanceof ImageViewTarget) {
            View view = ((ImageViewTarget<?>) target).getView();
            if (view != null) {
                view.setLayerType(View.LAYER_TYPE_SOFTWARE, null); // Disable hardware acceleration
            }
        }
        return false; // Let Glide handle the drawable
    }
}
