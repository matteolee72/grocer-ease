package com.example.grocerease;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;


/** Creating a Glide Module to handle image loading from Firebase
 *
 * Glide uses a dedicated background thread to download and decode images,
 * which allows the main UI thread to continue running smoothly without being blocked.
 *
 * This approach ensures that image loading and processing doesn't interfere with the
 * performance of the rest of the app.
 *
 * */

@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(StorageReference.class, InputStream.class,
                new FirebaseImageLoader.Factory());
    }
}

/**Additionally, Glide also uses a memory and disk cache to further improve performance
 * by reducing the need to download and decode images repeatedly.
 *
 * Memory caching: When Glide loads an image for the first time, it stores a copy of
 * the decoded bitmap in a memory cache. The next time the same image is requested, Glide
 * checks the memory cache first to see if the image is already loaded. If the image is
 * found in the memory cache, Glide returns the cached bitmap without reloading it from
 * the network or decoding it again. This significantly speeds up image loading times and
 * reduces network usage.
 *
 * Disk caching: In addition to memory caching, Glide also stores images in a disk cache.
 * When Glide loads an image for the first time, it downloads the image from the network and
 * saves it to the disk cache. The next time the same image is requested, Glide checks the
 * disk cache first to see if the image is already downloaded. If the image is found in the
 * disk cache, Glide returns the cached bitmap without downloading it from the network or
 * decoding it again.
**/