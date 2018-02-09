package com.bigfont.obj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.bigfont.mvp.main.Config;
import com.bigfont.mvp.main.MyLog;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by d on 11/17/2017.
 */

public class MyUtilsFont {
    private Context mContext;

    public MyUtilsFont(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isSettingPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(mContext);
        }
        return true;
    }

    public float getFontScale() {
        return mContext.getResources().getConfiguration().fontScale;
    }

    public void putSettingsFont(float size) {
        if (isSettingPermission()) {
            MyLog.putFloatValueByName(mContext, Config.LOG_APP, Config.FONT_SCALE, size);
            Settings.System.putFloat(mContext.getContentResolver(), Settings.System.FONT_SCALE, size);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.System.canWrite(getApplicationContext())) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + mContext.getPackageName()));
                    mContext.startActivity(intent);
                }
            }
        }
    }
}
