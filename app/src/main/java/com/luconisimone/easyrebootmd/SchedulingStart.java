package com.luconisimone.easyrebootmd;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;
import com.stericson.RootTools.RootTools;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeoutException;


public class SchedulingStart extends BroadcastReceiver {

    String azione = "niente";
    String testonotifica = "";
    Handler mHandler = new Handler();
    NotificationManagerCompat notificationManager;
    int riavvio;
    int spegni;
    Intent intent;
    Intent intent2;
    Intent intent3;
    BroadcastReceiver receiver;
    Handler m_handler;
    Runnable m_handlerTask;
    int _count = 59;
    NotificationCompat.Builder mBuilder;
    int notcount = 1;


    @Override
    public void onReceive(final Context context, Intent intent) {

        SharedPreferences sharePref = context.getSharedPreferences("Dati", Context.MODE_PRIVATE);
        azione = sharePref.getString("azionesched", "niente");
        riavvio = sharePref.getInt("riavvioautom", 0);
        spegni = sharePref.getInt("spegniautom", 0);
        notcount = sharePref.getInt("notificationcount", 1);

        if (azione.equals("riavvia")) {
            testonotifica = context.getString(R.string.scheduleaction) + " (" + context.getString(R.string.widgetnormalreboot) + ")";
        } else {
            testonotifica = context.getString(R.string.scheduleaction) + " (" + context.getString(R.string.spegnitesto) + ")";
        }

        Intent intent1 = new Intent("stop");
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);

        intent2 = new Intent("now");
        PendingIntent pIntent2 = PendingIntent.getBroadcast(context, 0, intent2, 0);

        IntentFilter filter = new IntentFilter();
        filter.addAction("stop");
        filter.addAction("now");

        if (notcount == 1) {

            mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_notif)
                            .setContentTitle("")
                            .setContentText("")
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setLights(Color.BLUE, 3000, 3000)
                            .setVibrate(new long[]{500, 500, 500, 500, 500})
                            .setContentIntent(pIntent)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(testonotifica))
                            .setAutoCancel(true);

            bluecolor(context);

            notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(5, mBuilder.build());

        }

        m_handler = new Handler();
        m_handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (notcount == 1) {
                    notificationManager.cancel(5);
                }
            }
        }, 2000);

        //Bitmap backgroundwear = BitmapFactory.decodeResource(getResources(), R.drawable.wearback);


        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_clear_white, context.getString(R.string.dismiss2), pIntent)
                        .build();

        NotificationCompat.Action action2 =
                new NotificationCompat.Action.Builder(R.drawable.ic_power_settings_48dp, context.getString(R.string.donow), pIntent2)
                        .build();


        notificationManager = NotificationManagerCompat.from(context);


        mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notif)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(testonotifica)
                        .setLights(Color.BLUE, 3000, 3000)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_clear_white_18dp, context.getString(R.string.dismiss2), pIntent)
                        .addAction(R.drawable.ic_power_settings_18dp, context.getString(R.string.donow), pIntent2)
                        .extend(new WearableExtender().addAction(action).addAction(action2))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(testonotifica));


        m_handler = new Handler();
        bluecolor(context);
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                if (_count <= 59 & !(_count <= 0)) {
                    mBuilder.setProgress(60, _count, false);
                    notificationManager.notify(1, mBuilder.build());
                    _count--;
                } else if (_count == 0) {
                    _count = 60;
                    m_handler.removeCallbacks(m_handlerTask);
                    mBuilder.setProgress(0, 0, false);
                    notificationManager.cancel(1);
                    doafterwait(context);
                }
                m_handler.postDelayed(m_handlerTask, 1000);
            }
        };
        m_handlerTask.run();


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, Intent intent) {
                if (intent.getAction().equals("stop")) {
                    azione = "niente1";
                    notificationManager.cancel(1);
                    _count = 0;
                    Toast.makeText(context, R.string.dismiss, Toast.LENGTH_LONG).show();
                }

                if (intent2.getAction().equals("now")) {
                    _count = 1;
                }

            }
        };

        context.getApplicationContext().registerReceiver(receiver, filter);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void bluecolor(Context context) {
        int api = android.os.Build.VERSION.SDK_INT;
        if (api >= 21) {
            mBuilder.setColor(ContextCompat.getColor(context, R.color.bluematerial));
        }
    }

    public void doafterwait(Context context) {

        if (azione.equals("riavvia")) {

            SharedPreferences sharedPref = context.getSharedPreferences("Dati", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("azioneeff", "riavvioboot");
            editor.apply();

            Command command = new Command(0, "su", "svc power reboot");

            try {
                riavvio = riavvio + 1;
                editor.putInt("riavvioautom", riavvio);
                editor.apply();

                RootTools.getShell(true).add(command);


            } catch (IOException | TimeoutException | RootDeniedException ex) {
                notificationerror(context);
                riavvio = riavvio - 1;
                editor.putInt("riavvioautom", riavvio);
                editor.apply();
            }
        } else if (azione.equals("spegni")) {

            SharedPreferences sharedPref = context.getSharedPreferences("Dati", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("azioneeff", "accendiboot");
            editor.apply();

            Command command = new Command(0, "su", "reboot -p");

            try {
                spegni = spegni + 1;
                editor.putInt("spegniautom", spegni);
                editor.apply();

                RootTools.getShell(true).add(command);


            } catch (IOException | TimeoutException | RootDeniedException ex) {
                notificationerror(context);
                spegni = spegni - 1;
                editor.putInt("spegniautom", spegni);
                editor.apply();
            }
        }


    }


    public void notificationerror(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, Intent.FILL_IN_ACTION);
        notificationManager.cancel(1);

        SharedPreferences sharedPref = context.getSharedPreferences("Dati", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("azioneeff", "niente");
        editor.apply();

        mBuilder =
                new NotificationCompat.Builder(context.getApplicationContext())
                        .setSmallIcon(R.drawable.ic_notif)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.noroot))
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setLights(Color.BLUE, 3000, 3000)
                        .setVibrate(new long[]{500, 500, 500, 500, 500})
                        .setContentIntent(pIntent)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(context.getString(R.string.noroot)))
                        .setAutoCancel(true);

        bluecolor(context);

        notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(2, mBuilder.build());
    }


}
