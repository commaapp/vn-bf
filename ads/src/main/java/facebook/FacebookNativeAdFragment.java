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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duong.mylibrary.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

import inter.OnErrorLoadAd;

public class FacebookNativeAdFragment extends Fragment {
    private NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;
    private String idAd;
    private View rootView;
//    private View adxView;


    public void setIdAd(String idAd) {
        this.idAd = idAd;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_native_ad, container, false);
//        showNativeAd();
        showNativeAdCty();
        return rootView;
    }

    private void showNativeAdCty() {
//        adxView = LayoutInflater.from(getContext()).inflate(R.layout.item_ads, null, false);
      nativeAd = new NativeAd(getContext(), idAd);
        nativeAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded(Ad ad) {
                LinearLayout nativeAdContainer = rootView.findViewById(R.id.native_ad_container);
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View adView = inflater.inflate(R.layout.facebook_native_ad_layout_300, nativeAdContainer, false);
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView(adView);

                // Create native UI using the ad metadata.
                ImageView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
                TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
                Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

                // Set the Text.
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdTitle.setText(nativeAd.getAdTitle());
                nativeAdBody.setText(nativeAd.getAdBody());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
                // Download and display the ad icon.
                NativeAd.Image adIcon = nativeAd.getAdIcon();
                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);
                // Download and display the cover image.
                nativeAdMedia.setNativeAd(nativeAd);
                // Add the AdChoices icon
                LinearLayout adChoicesContainer = rootView.findViewById(R.id.ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(getContext(), nativeAd, true);
                adChoicesContainer.addView(adChoicesView);
                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdIcon);
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdMedia);
                clickableViews.add(nativeAdSocialContext);
                clickableViews.add(nativeAdBody);
                clickableViews.add(nativeAdCallToAction);
                nativeAd.registerViewForInteraction(nativeAdContainer, clickableViews);
            }

            @Override
            public void onError(Ad ad, AdError error) {
                if (onErrorLoadAd != null)
                    onErrorLoadAd.onError();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        nativeAd.loadAd();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private OnErrorLoadAd onErrorLoadAd;

    public void setOnErrorLoadAd(OnErrorLoadAd onErrorLoadAd) {
        this.onErrorLoadAd = onErrorLoadAd;

    }

    private void showNativeAd() {
        nativeAd = new NativeAd(getContext(), idAd);
        nativeAd.setAdListener(new AdListener() {

            @Override
            public void onError(Ad ad, AdError error) {
                if (onErrorLoadAd != null)
                    onErrorLoadAd.onError();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                try {
                    // Add the Ad view into the ad container.
                    nativeAdContainer = (LinearLayout) rootView.findViewById(R.id.native_ad_body);
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    adView = (LinearLayout) inflater.inflate(R.layout.facebook_native_ad_layout_300, nativeAdContainer, false);
                    nativeAdContainer.addView(adView);

                    // Create native UI using the ad metadata.
                    ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
                    TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
                    MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
                    TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id
                            .native_ad_social_context);
                    TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
                    Button nativeAdCallToAction = (Button) adView.findViewById(R.id
                            .native_ad_call_to_action);

                    // Set the Text.
                    nativeAdTitle.setText(nativeAd.getAdTitle());
                    nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                    nativeAdBody.setText(nativeAd.getAdBody());
                    nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

                    // Download and display the ad icon.
                    NativeAd.Image adIcon = nativeAd.getAdIcon();
                    NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                    // Download and display the cover image.
                    nativeAdMedia.setNativeAd(nativeAd);

                    // Add the AdChoices icon
                    LinearLayout adChoicesContainer = (LinearLayout) rootView.findViewById(R.id.ad_choices_container);
                    AdChoicesView adChoicesView = new AdChoicesView(getContext(), nativeAd, true);
                    adChoicesContainer.addView(adChoicesView);

                    // Register the Title and CTA button to listen for clicks.
                    List<View> clickableViews = new ArrayList<>();
                    clickableViews.add(nativeAdTitle);
                    clickableViews.add(nativeAdCallToAction);
                    nativeAd.registerViewForInteraction(nativeAdContainer, clickableViews);
                } catch (Exception e) {
                    if (onErrorLoadAd != null)
                        onErrorLoadAd.onError();
                }

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // On logging impression callback
            }
        });

        // Request an ad
        nativeAd.loadAd();
    }
}
