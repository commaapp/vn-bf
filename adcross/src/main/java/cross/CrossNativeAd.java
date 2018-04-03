package cross;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duong.mylibrary.R;

import inter.OnErrorLoadAd;

/**
 * Created by D on 2/23/2018.
 */

public class CrossNativeAd {
    private LinearLayout viewContainer;
    private View adView;
    private Context mContext;

    public CrossNativeAd(Context mContext, LinearLayout viewContainer) {
        this.viewContainer = viewContainer;
        this.mContext = mContext;
    }

    CrossAd.VNCross vnCross;

    public void show() {

        try {
            adView = LayoutInflater.from(mContext).inflate(R.layout.cross_native_ad, null);
            vnCross = CrossAd.getACrossAd(mContext);
            ((TextView) adView.findViewById(R.id.cross_des)).setText(vnCross.des_tag);
            ((TextView) adView.findViewById(R.id.cross_title)).setText(vnCross.title_tag);
            Log.e("anh icon", vnCross.link_icon_tag);
            Log.e("anh icon", "done");
            Glide.with(mContext).load(vnCross.link_icon_tag).into((ImageView) adView.findViewById(R.id.cross_icon));
            adView.findViewById(R.id.cross_ad).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + vnCross.id_app_android_tag)));
                    } catch (ActivityNotFoundException anfe) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + vnCross.id_app_android_tag)));
                    }
                }
            });
//            viewContainer.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 300));
            viewContainer.addView(adView);
        } catch (Exception exx) {
            CrossAd.init(mContext);
            return;
        }


    }


    private OnErrorLoadAd onErrorLoadAd;

    public void setOnErrorLoadAd(OnErrorLoadAd onErrorLoadAd) {
        this.onErrorLoadAd = onErrorLoadAd;
    }
}
