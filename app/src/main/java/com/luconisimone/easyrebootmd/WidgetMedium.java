package com.luconisimone.easyrebootmd;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;


public class WidgetMedium extends AppWidgetProvider {

    @Override
    public void onUpdate (Context context, AppWidgetManager appWidgetManager, int [] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_widget_medium);

            Intent intent0 = new Intent (context, MainActivity.class);
            PendingIntent pending0 = PendingIntent.getActivity(context , 0 , intent0, 0);
            views.setOnClickPendingIntent(R.id.widgetmediumopenapp, pending0);


            Intent intent2 = new Intent (context, NormalReboot.class);
            PendingIntent pending2 = PendingIntent.getActivity(context , 0 , intent2, 0);
            views.setOnClickPendingIntent(R.id.widgetmediumnormalreboot, pending2);


            Intent intent3 = new Intent (context, PowerOff.class);
            PendingIntent pending3 = PendingIntent.getActivity(context , 0 , intent3, 0);
            views.setOnClickPendingIntent(R.id.widgetmediumpoweroff, pending3);

            Intent intent4 = new Intent (context, rebootsystemui.class);
            PendingIntent pending4 = PendingIntent.getActivity(context , 0 , intent4, 0);
            views.setOnClickPendingIntent(R.id.widgetmediumrebootui, pending4);

            appWidgetManager.updateAppWidget(appWidgetId, views);




        }


    }
}

