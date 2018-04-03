package cross;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duong.mylibrary.R;
import com.facebook.ads.NativeAd;

import inter.OnErrorLoadAd;

/**
 * Created by D on 2/24/2018.
 */

public class CrossFullscreenAd extends Activity {

    public static void show(Context context) {
        context.startActivity(new Intent(context, CrossFullscreenAd.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cross_fullscreen_ad);
        getActionBar().hide();
        loadAdx();
    }

    CrossAd.VNCross vnCross;

    private void loadAdx() {
        try {
            vnCross = CrossAd.getACrossAd(this);
            ((TextView) findViewById(R.id.cross_des)).setText(vnCross.des_tag);
            ((TextView) findViewById(R.id.cross_title)).setText(vnCross.title_tag);
            Glide.with(this).load(vnCross.link_icon_tag).into((ImageView) findViewById(R.id.cross_icon));
            findViewById(R.id.cross_ad).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + vnCross.id_app_android_tag)));
                    } catch (ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + vnCross.id_app_android_tag)));
                    }
                }
            });

            ((ImageView) findViewById(R.id.cross_close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } catch (Exception exx) {
            CrossAd.init(this);
            finish();
        }
    }

    private OnErrorLoadAd onErrorLoadAd;

    public void setOnErrorLoadAd(OnErrorLoadAd onErrorLoadAd) {
        this.onErrorLoadAd = onErrorLoadAd;

    }


}
