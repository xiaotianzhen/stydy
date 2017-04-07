package com.qianwang.stydy;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lqr.emoji.IImageLoader;
import com.lqr.emoji.LQREmotionKit;


/**
 * Created by sky on 2017/3/29.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LQREmotionKit.init(this, new IImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {

                Glide.with(context).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

            }
        });
    }
}
