package core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bigfont.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import facebook.FacebookNativeAdFragment;
import inter.OnErrorLoadAd;
import richadx.RichNativeAdFragment;

/**
 * Created by d on 1/9/2018.
 */

public class ActivitySettings extends AppCompatActivity {
    @BindView(R.id.cb_show_noti)
    CheckBox cbShowNoti;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    RichNativeAdFragment richNativeAdFragment;
    FacebookNativeAdFragment facebookNativeAdFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        cbShowNoti.setChecked(MyCache.getBooleanValueByName(this, Config.LOG_APP, Config.SHOW_NOTI, true));
        cbShowNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyCache.putBooleanValueByName(ActivitySettings.this, Config.LOG_APP, Config.SHOW_NOTI, isChecked);
                Config.createNotification(ActivitySettings.this);
            }
        });

        try {
            richNativeAdFragment = new RichNativeAdFragment();
            facebookNativeAdFragment = new FacebookNativeAdFragment();


            facebookNativeAdFragment.setOnErrorLoadAd(new OnErrorLoadAd() {
                @Override
                public void onError() {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_ad, richNativeAdFragment).commitAllowingStateLoss();
                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_ad, facebookNativeAdFragment).commitAllowingStateLoss();
        } catch (Exception e) {
        }


    }
}
