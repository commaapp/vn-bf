package richadx;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.duong.mylibrary.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import inter.OnErrorLoadAd;

import static com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE;

/**
 * Created by d on 1/24/2018.
 */

public class RichInterstialAd {
    PublisherInterstitialAd mPublisherInterstitialAd;
    OnErrorLoadAd onErrorLoadAd;
    Context mContext;

    public RichInterstialAd(Context mContext, String idAd) {
        this.mContext = mContext;
        this.idAd = idAd;
    }

    public void setOnErrorLoadAd(OnErrorLoadAd onErrorLoadAd) {
        this.onErrorLoadAd = onErrorLoadAd;
    }

//    public void show() {
//        if (mPublisherInterstitialAd.isLoaded())
//            mPublisherInterstitialAd.show();
//        else requestNewInterstitial();
//    }

    public void setIdAd(String idAd) {
        this.idAd = idAd;
    }

    private String idAd;

    public void show() {
        mPublisherInterstitialAd = new PublisherInterstitialAd(mContext);
        mPublisherInterstitialAd.setAdUnitId(idAd);
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                if (onErrorLoadAd != null)
                    onErrorLoadAd.onMyError();
            }

            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                try {
                    ((Activity) mContext).finish();
                } catch (Exception e) {
                }
            }

            @Override
            public void onAdLoaded() {
                mPublisherInterstitialAd.show();
            }
        });
        mPublisherInterstitialAd.loadAd(adRequest);
    }
}