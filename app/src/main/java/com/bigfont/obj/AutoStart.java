package com.bigfont.obj;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bigfont.demo.R;
import com.bigfont.mvp.main.Config;
import com.bigfont.mvp.main.MainActivity;
import com.bigfont.mvp.main.MyLog;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.bigfont.mvp.main.Config.createNotification;

/**
 * Created by d on 10/25/2017.
 */

public class AutoStart extends BroadcastReceiver {

    public AutoStart() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            for (ItemFont itemFont : Config.getItemFonts(context)) {
//                if (MyLog.getFloatValueByName(context, Config.LOG_APP, Config.FONT_SCALE) == itemFont.getSize()
//                        ) {
            createNotification(context);
//                    createNotification(context, itemFont.getTitle());
//                    Toast.makeText(context, "ACTION_BOOT_COMPLETED", Toast.LENGTH_SHORT).show();
//                    return;
//                }

//            }
        }
//        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
//            Toast.makeText(context, "  đang kiểm tra xem có gì hót ^.^", Toast.LENGTH_LONG).show();
//        }
    }



}