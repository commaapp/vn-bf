package core;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigfont.demo.BuildConfig;
import com.bigfont.demo.R;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.FacebookSdk;
import com.facebook.ads.AdSettings;
import com.facebook.appevents.AppEventsLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import facebook.FacebookBannerFragment;
import inter.OnErrorLoadAd;
import io.fabric.sdk.android.Fabric;
import richadx.RichBannerAdFragment;

public class MainActivity extends AppCompatActivity {
    LinearLayout frameNotiRestart;
    ListView listView;
    public AppEventsLogger logger;
    @BindView(R.id.cta_reset_custem_font_size)
    TextView ctaResetCustemFontSize;
    private View convertView;
    private float size;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up Crashlytics, disabled for debug builds
        Crashlytics crashlyticKit = new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build();
        Fabric.with(this, crashlyticKit, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        logger = AppEventsLogger.newLogger(this);
        Config.createNotification(this);
        getSupportActionBar().hide();
        AdSettings.addTestDevice("4525217d5fe36225baaad440be5f3062");
        ImageView imRate = findViewById(R.id.ic_rate);
        startActivity(new Intent(this, SplashActivity.class));
        imRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logger.logEvent("MainScreen_IconHeart_Clicked");
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                Toast.makeText(MainActivity.this, getResources().getString(R.string.txt_rate), Toast.LENGTH_SHORT).show();
            }
        });
        if (isRooted()) logger.logEvent("MainScreen_DeviceRooted");
        else logger.logEvent("MainScreen_DeviceNotRoot");

        frameNotiRestart = findViewById(R.id.frame_noti_reset);
        frameNotiRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameNotiRestart.setVisibility(View.GONE);
                logger.logEvent("MainScreen_IconCloseResetNoti_Clicked");
            }
        });
        listView = findViewById(R.id.main_lv_list_item);
        if (isSettingPermission()) {
            loadStateItem();
        } else {
            loadStateItem();
            listView.setAdapter(new AdaptorFontScale(this, android.R.layout.simple_list_item_1, Config.getItemFonts(this)));
        }

        float floatFont = getResources().getConfiguration().fontScale;
        initAdBanner();
    }

    FacebookBannerFragment facebookBannerFragment;
    RichBannerAdFragment richBannerAdFragment;

    private void initAdBanner() {
        try {
            richBannerAdFragment = new RichBannerAdFragment();
            facebookBannerFragment = new FacebookBannerFragment();
            facebookBannerFragment.setOnErrorLoadAd(new OnErrorLoadAd() {
                @Override
                public void onError() {
                    richBannerAdFragment.setOnErrorLoadAd(new OnErrorLoadAd() {
                        @Override
                        public void onError() {
                            findViewById(R.id.ad_banner).setVisibility(View.GONE);
                        }
                    });
                    getSupportFragmentManager().beginTransaction().replace(R.id.ad_banner, richBannerAdFragment).commitAllowingStateLoss();
                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.ad_banner, facebookBannerFragment).commitAllowingStateLoss();

        } catch (Exception e) {
        }

    }

    private void showNotiRestart() {
        Snackbar.make(listView, getString(R.string.txt_noti_restart_device), Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logger.logEvent("MainScreen_IconCloseResetNoti_Clicked");
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
        try {
            loadStateItem();
        } catch (Exception e) {
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }


    public void loadStateItem() {
        float fontScale = getResources().getConfiguration().fontScale;
        float fontScaleSave = MyLog.getFloatValueByName(this, Config.LOG_APP, Config.FONT_SCALE);
//        if (fontScaleSave != 1f) {
//            ctaResetCustemFontSize.setVisibility(View.GONE);
//        } else {
//            ctaResetCustemFontSize.setVisibility(View.VISIBLE);
//        }
        if (fontScale != fontScaleSave) {
//            showNotiRestart();
            frameNotiRestart.setVisibility(View.VISIBLE);
        } else {
            frameNotiRestart.setVisibility(View.GONE);
        }

        listView.setAdapter(new AdaptorFontScale(this, android.R.layout.simple_list_item_1, Config.getItemFonts(this)));
    }


    public void requestPermissionAndApplyFontSize(ItemFont itemFontSize) {
        if (!isSettingPermission()) {
            Toast.makeText(this, getResources().getString(R.string.requestPermission), Toast.LENGTH_LONG).show();
            settingPermission(itemFontSize.getSize());
        } else {
            Intent intent = new Intent(this, SuccActivity.class);
            if (itemFontSize.getSize() > 1f)
                intent.putExtra(Config.FONT_SCALE, itemFontSize.getTitle());
//            this.startActivity(intent);
            MyLog.putFloatValueByName(this, Config.LOG_APP, Config.FONT_SCALE, itemFontSize.getSize());
            Config.createNotification(this);
            Settings.System.putFloat(this.getContentResolver(), Settings.System.FONT_SCALE, itemFontSize.getSize());
            loadStateItem();
        }
    }

    public void settingPermission(float size) {
        this.size = size;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 200);
            }
        }
    }

    public boolean isSettingPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(getApplicationContext());
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200)
            if (isSettingPermission()) {
//                startActivity(new Intent(this, SuccActivity.class));
                MyLog.putFloatValueByName(this, Config.LOG_APP, Config.FONT_SCALE, size);
//                createNotification(this,""+((int) ((MyLog.getFloatValueByName(this, Config.LOG_APP, Config.FONT_SCALE) * 100))));
                Config.createNotification(this);
                Settings.System.putFloat(getContentResolver(), Settings.System.FONT_SCALE, size);
                loadStateItem();
                logger.logEvent("MainScreen_ButtonAplly" + size + "_Clicked");
            }
        if (Config.REQUEST_CODE_CUSTOM_FONT_SIZE == requestCode) {

        }
    }

    public static boolean isRooted() {
        // get from build info
        String buildTags = Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }
        // try executing commands
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }

    // executes a command on the system
    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }

    private void showDialogRateApp() {
        final Dialog dialog1 = new Dialog(MainActivity.this);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.custom_dialog_rate);
        dialog1.setCancelable(true);
        final TextView btnRate = dialog1.findViewById(R.id.btnRate);
        TextView btnLater = dialog1.findViewById(R.id.btnLater);


        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyCache.getBooleanValueByName(MainActivity.this, Config.LOG_APP, "change")) {
                    MyCache.putBooleanValueByName(MainActivity.this, Config.LOG_APP, "rate", true);
                    dialog1.dismiss();
                    finish();
                }

            }
        });
        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCache.putBooleanValueByName(MainActivity.this, Config.LOG_APP, "rate", false);
                dialog1.dismiss();
                finish();
            }
        });
        RatingBar mRatingBar = dialog1.findViewById(R.id.mRatingBar);
        LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.colorLater), PorterDuff.Mode.SRC_ATOP);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating > 3) {
                    Toast.makeText(MainActivity.this, getString(R.string.sms_thank_you_rate), Toast.LENGTH_SHORT).show();
                    MyCache.putBooleanValueByName(MainActivity.this, Config.LOG_APP, "rate", true);
                    Intent i = new Intent("android.intent.action.VIEW");
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(i);
                    finish();
                } else if (rating <= 3) {
                    MyCache.putBooleanValueByName(MainActivity.this, Config.LOG_APP, "change", true);
                } else {
                }
            }
        });

        dialog1.show();
    }


    @Override
    public void onBackPressed() {

        boolean action = MyCache.getBooleanValueByName(MainActivity.this, Config.LOG_APP, "action");
        if (!action) {
            if (!MyCache.getBooleanValueByName(MainActivity.this, Config.LOG_APP, "rate")) {
                showDialogRateApp();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }


    @OnClick({R.id.cta_create_custem_font_size, R.id.cta_reset_custem_font_size})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cta_create_custem_font_size:
                logger.logEvent("MainScreen_CustomFontButton_Clicked");
//                Intent intent = new Intent(this, ActivityCreateFontSize.class);
//                startActivityForResult(intent, Config.REQUEST_CODE_CUSTOM_FONT_SIZE);
                break;
            case R.id.cta_reset_custem_font_size:
                logger.logEvent("MainScreen_ReturnDefaultButton_Clicked");
                requestPermissionAndApplyFontSize(Config.getItemFonts(this).get(0));
                break;
        }
    }

    @OnClick(R.id.ic_settings)
    public void onViewClicked() {
        startActivity(new Intent(this, ActivitySettings.class));
    }
}
