package core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static core.Config.createNotification;

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