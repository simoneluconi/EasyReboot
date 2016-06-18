package com.luconisimone.easyrebootmd;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WidgetSmall extends AppWidgetProvider {

    @Override
    public void onUpdate (Context context, AppWidgetManager appWidgetManager, int [] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_widget_small);

            Intent intent0 = new Intent (context, widgetactivityfast.class);
            PendingIntent pending0 = PendingIntent.getActivity(context , 0 , intent0, 0);
            views.setOnClickPendingIntent(R.id.ButtonWidgetSmall, pending0);




            appWidgetManager.updateAppWidget(appWidgetId, views);




        }


    }
}