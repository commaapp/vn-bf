package core;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.bigfont.demo.R;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by d on 9/6/2017.
 */

public class Config {
    public static final String FONT_SCALE = "FONT_SCALE";
    public static final String LOG_APP = "LOG_APP";
    public static final int REQUEST_CODE_CUSTOM_FONT_SIZE = 1001;
    public static final int RESULT_CODE_FONT_SIZE = 1010;
    public static final String SHOW_NOTI = "SHOW_NOTI";

    public static ArrayList<ItemFont> getItemFonts(Context context) {
        ArrayList<ItemFont> itemFonts;
        itemFonts = new ArrayList<>();
//        itemFonts.add(new ItemFont("", 2.5f, false));
        itemFonts.add(new ItemFont(context.getResources().getString(R.string.title_item_font_1x), 1f, false));
        itemFonts.add(new ItemFont(context.getResources().getString(R.string.title_item_font_12x), 1.2f, false));
        itemFonts.add(new ItemFont(context.getResources().getString(R.string.title_item_font_14x), 1.4f, false));
        itemFonts.add(new ItemFont(context.getResources().getString(R.string.title_item_font_16x), 1.6f, false));
        itemFonts.add(new ItemFont(context.getResources().getString(R.string.title_item_font_18x), 1.8f, false));
        itemFonts.add(new ItemFont(context.getResources().getString(R.string.title_item_font_20x), 2f, false));
        itemFonts.add(new ItemFont(context.getResources().getString(R.string.title_item_font_22x), 2.2f, false));
        itemFonts.add(new ItemFont(context.getResources().getString(R.string.title_item_font_24x), 2.4f, false));
        return itemFonts;
    }

    public static void createNotification(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);
        // Build notification
        String title = ((int) ((MyLog.getFloatValueByName(context, Config.LOG_APP, Config.FONT_SCALE) * 100)) + "%");
        // Actions are just fake
        Notification noti = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            noti = new Notification.Builder(context)
                    .setContentTitle(context.getResources().getString(R.string.title_noti) + " " + title)
                    .setContentText(context.getResources().getString(R.string.content_noti))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .build();
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_NO_CLEAR;
        boolean isShow = MyCache.getBooleanValueByName(context, Config.LOG_APP, Config.SHOW_NOTI, true);
        if (!isShow) notificationManager.cancelAll();
        else notificationManager.notify(0, noti);
    }

}
