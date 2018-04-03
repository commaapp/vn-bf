/*
 * Copyright (c) 2016-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to
 * use, copy, modify, and distribute this software in source code or binary
 * form for use in connection with the web services and APIs provided by
 * Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package facebook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.duong.mylibrary.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import inter.OnErrorLoadAd;


public class FacebookInterstitial {
    private InterstitialAd interstitialAd;
    private OnErrorLoadAd onErrorLoadAd;
    private Context mContext;

    public void setOnErrorLoadAd(OnErrorLoadAd onErrorLoadAd) {
        this.onErrorLoadAd = onErrorLoadAd;
    }

    public FacebookInterstitial(Context mContext, String idAd) {
        this.mContext = mContext;
        this.idAd = idAd;
    }

    private String idAd;

    public void show() {
        interstitialAd = new InterstitialAd(mContext, idAd);
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                try {
                    ((Activity) mContext).finish();
                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                if (onErrorLoadAd != null)
                    onErrorLoadAd.onMyError();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        interstitialAd.loadAd();
    }


}
