package com.bigfont.mvp.suc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bigfont.demo.R;
import com.bigfont.mvp.main.Config;
import com.bigfont.mvp.main.MainActivity;
import com.bigfont.mvp.main.MyLog;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by d on 9/6/2017.
 */

public class SuccActivity extends AppCompatActivity {
    private AppEventsLogger logger;

    public void createNotification() {
        String title = "";
        title = getIntent().getStringExtra(Config.FONT_SCALE);

        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.title_noti) + " " + ((int) ((MyLog.getFloatValueByName(this, Config.LOG_APP, Config.FONT_SCALE) * 100)) + "%"))
                .setContentText(getResources().getString(R.string.content_noti))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_NO_CLEAR;
//        if (title == null){
//            notificationManager.cancelAll();
//            return;
//        }
        notificationManager.notify(0, noti);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.changed_font_scale);
        Button btnRestartLater = (Button) findViewById(R.id.btn_restart_later);
        logger = AppEventsLogger.newLogger(this);
        createNotification();
        btnRestartLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logger.logEvent("RestartScreen_ButtonRestart_Clicked");
                finish();
            }
        });
        ImageView imIconRestart = findViewById(R.id.ic_restart);
        imIconRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logger.logEvent("RestartScreen_IconRestart_Clicked");
            }
        });
    }


}
