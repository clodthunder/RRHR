package com.lskj.gx.rrhr.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * Created by Home on 16/7/28.
 */
public class MGlideModule implements GlideModule {
    private Context mContext;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

        int cacheSize100MegaBytes = 104857600;
        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes)
        );
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
