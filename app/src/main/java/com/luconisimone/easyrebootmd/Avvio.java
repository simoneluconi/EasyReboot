package com.luconisimone.easyrebootmd;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import java.util.Calendar;


public class Avvio extends BroadcastReceiver {

    int accensioni = 0;
    int notifiche;
    String azioneeff;
    String testonotifica;
    NotificationManager notificationManager;
    Notification.Builder mBuilder;


    @Override
    public void onReceive(Context context, Intent intent) {


        SharedPreferences sharePref = context.getSharedPreferences("Dati", Context.MODE_PRIVATE);
        accensioni = sharePref.getInt("accensioni", 0);
        accensioni = accensioni + 1;
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putInt("accensioni", accensioni);
        editor.apply();

        notifiche = sharePref.getInt("notificationonboot", 1);
        azioneeff = sharePref.getString("azioneeff", "niente");

        if (azioneeff.equals("riavvioboot")) {
            testonotifica = context.getString(R.string.notifreboot);
        } else if (azioneeff.equals("riavvioboot")) {
            testonotifica = context.getString(R.string.notifpoweroff);
        }

        editor.putString("azioneeff", "niente");
        editor.apply();

        if (!sharePref.getString("azionesched", "niente").equals("niente")) {

            AlarmManager mAlarmManger = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intentac = new Intent(context, SchedulingStart.class);
            intentac.putExtra("uur", "1e");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentac, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, sharePref.getInt("orasched", 0));
            calendar.set(Calendar.MINUTE, sharePref.getInt("minsched", 0));
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.DAY_OF_MONTH , 1);

            mAlarmManger.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }


        Intent intent1 = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, Intent.FILL_IN_ACTION);

        if (notifiche == 1 & !azioneeff.equals("niente")) {
            mBuilder =
                    new Notification.Builder(context)
                            .setSmallIcon(R.drawable.ic_notif)
                            .setContentTitle(context.getString(R.string.app_name))
                            .setContentText(testonotifica)
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setLights(Color.BLUE, 3000, 3000)
                            .setVibrate(new long[]{500, 500, 500, 500, 500})
                            .setContentIntent(pIntent)
                            .setStyle(new Notification.BigTextStyle()
                                    .bigText(testonotifica))
                            .setAutoCancel(true);

            bluecolor(context);

            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(3, mBuilder.build());
        }


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void bluecolor(Context context) {
        int api = android.os.Build.VERSION.SDK_INT;
        if (api >= 21) {
            mBuilder.setColor(context.getResources().getColor(R.color.bluematerial));
        }
    }

}

