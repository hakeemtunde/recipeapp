package com.corebyte.mob.bakingapp.ui.widget;


import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.ui.IngredientActivity;
import com.corebyte.mob.bakingapp.ui.StepsActivity;

public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for(int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            Intent intent = new Intent(context, AppWidgetRemoteViewService.class);
            views.setRemoteAdapter(R.id.widgetListView, intent);

            //handle item click
            Intent clickIntent = new Intent(context, IngredientActivity.class);
            PendingIntent clickPendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntent)
                    .getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widgetListView, clickPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
