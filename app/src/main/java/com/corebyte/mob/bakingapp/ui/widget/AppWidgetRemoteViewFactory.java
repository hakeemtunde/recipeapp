package com.corebyte.mob.bakingapp.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.ui.StepsActivity;
import com.corebyte.mob.bakingapp.utils.JsonParser;

import java.util.List;

public class AppWidgetRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Recipe> recipes;

    public AppWidgetRemoteViewFactory(Context context) {
        this.context = context;
        recipes = JsonParser.getJsonParser().fetchRecipes(context);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION) {
            return null;
        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_list_item);
        remoteViews.setTextViewText(R.id.widget_recipe_item, recipes.get(position).getName());

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(StepsActivity.RECIPE_KEY, recipes.get(position));
        remoteViews.setOnClickFillInIntent(R.id.widgetRecipeContainer, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
