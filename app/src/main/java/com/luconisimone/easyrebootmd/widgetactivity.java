package com.luconisimone.easyrebootmd;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

public class widgetactivity extends AppWidgetProvider {

    @Override
    public void onUpdate (Context context, AppWidgetManager appWidgetManager, int [] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_widgetactivity);

            Intent intent0 = new Intent (context, MainActivity.class);
            PendingIntent pending0 = PendingIntent.getActivity(context , 0 , intent0, 0);
            views.setOnClickPendingIntent(R.id.widgetopenapp, pending0);

            Intent intent1 = new Intent (context, SoftReboot.class);
            PendingIntent pending1 = PendingIntent.getActivity(context , 0 , intent1, 0);
            views.setOnClickPendingIntent(R.id.widgetfastreboot, pending1);

            Intent intent2 = new Intent (context, NormalReboot.class);
            PendingIntent pending2 = PendingIntent.getActivity(context , 0 , intent2, 0);
            views.setOnClickPendingIntent(R.id.widgetnormalreboot, pending2);

            Intent intent3 = new Intent (context, BootloaderReboot.class);
            PendingIntent pending3 = PendingIntent.getActivity(context , 0 , intent3, 0);
            views.setOnClickPendingIntent(R.id.widgetbootloaderreboot, pending3);

            Intent intent4 = new Intent (context, RecoveryReboot.class);
            PendingIntent pending4 = PendingIntent.getActivity(context , 0 , intent4, 0);
            views.setOnClickPendingIntent(R.id.widgetrecoveryreboot, pending4);

            Intent intent5 = new Intent (context, RebootDownload.class);
            PendingIntent pending5 = PendingIntent.getActivity(context , 0 , intent5, 0);
            views.setOnClickPendingIntent(R.id.widgetdownloadreboot, pending5);

            Intent intent6 = new Intent (context, PowerOff.class);
            PendingIntent pending6 = PendingIntent.getActivity(context , 0 , intent6, 0);
            views.setOnClickPendingIntent(R.id.widgetpoweroff, pending6);

            if (!android.os.Build.BRAND.toUpperCase().equals("SAMSUNG")) {
               views.setViewVisibility(R.id.widgetdownloadreboot,View.GONE);
            }

            Intent intent7 = new Intent (context, rebootsystemui.class);
            PendingIntent pending7 = PendingIntent.getActivity(context , 0 , intent7, 0);
            views.setOnClickPendingIntent(R.id.widgetrebootui1, pending7);


            appWidgetManager.updateAppWidget(appWidgetId, views);




        }


    }
}