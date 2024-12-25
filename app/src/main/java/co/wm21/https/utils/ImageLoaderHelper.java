package co.wm21.https.utils;

import android.content.Context;
import android.widget.ImageView;
import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;
import androidx.annotation.DrawableRes;

public class ImageLoaderHelper {

    private ImageLoaderHelper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Loads an image into an ImageView using Coil.
     *
     * @param context           The context (usually an Activity or Application).
     * @param imageView         The ImageView where the image will be loaded.
     * @param imageUrl          The URL of the image to load.
     * @param placeholderResId  Resource ID of the placeholder image.
     * @param errorResId        Resource ID of the error image.
     */
    public static void loadImage(
            Context context,
            ImageView imageView,
            String imageUrl,
            @DrawableRes int placeholderResId,
            @DrawableRes int errorResId
    ) {
        // Create an ImageLoader instance
        ImageLoader imageLoader = Coil.imageLoader(context);

        // Create an ImageRequest to load the image
        ImageRequest request = new ImageRequest.Builder(context)
                .data(imageUrl)                      // Image URL
                .placeholder(placeholderResId)        // Placeholder while loading
                .error(errorResId)                   // Fallback if image fails to load
                .target(imageView)                   // Directly set the ImageView as the target
                .build();

        // Enqueue the request to load the image asynchronously
        imageLoader.enqueue(request);
    }
}
