package com.corebyte.mob.bakingapp.ui.widget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.ui.StepsActivity;
import com.corebyte.mob.bakingapp.utils.RecipeUtil;

public class RecipeWidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            Intent intent = new Intent(context, AppWidgetRemoteViewService.class);
            views.setRemoteAdapter(R.id.widgetListView, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, RecipeWidgetProvider.class);
            int[] ids = appWidgetManager.getAppWidgetIds(componentName);

            if (intent.hasExtra(StepsActivity.RECIPE_KEY)) {
                Recipe recipe = intent.getParcelableExtra(StepsActivity.RECIPE_KEY);

                //store recipe
                RecipeUtil.storeRecipe(context, recipe);
                for (int id : ids) {
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
                    views.setTextViewText(R.id.widget_recipe_name, recipe.getName());

                    Intent appWidgetServiceIntent = new Intent(context, AppWidgetRemoteViewService.class);
                    appWidgetServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
                    views.setRemoteAdapter(R.id.widgetListView, appWidgetServiceIntent);

                    appWidgetManager.updateAppWidget(id, views);
                    appWidgetManager.notifyAppWidgetViewDataChanged(id, R.id.widgetListView);
                }

            }

        }

        super.onReceive(context, intent);
    }
}
