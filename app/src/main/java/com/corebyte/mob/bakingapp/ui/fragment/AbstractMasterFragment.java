package com.corebyte.mob.bakingapp.ui.fragment;

import android.accounts.NetworkErrorException;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.corebyte.mob.bakingapp.R;
import com.corebyte.mob.bakingapp.adapter.RecyclerViewAdapter;
import com.corebyte.mob.bakingapp.entity.Recipe;
import com.corebyte.mob.bakingapp.event.RecipeEventListener;
import com.corebyte.mob.bakingapp.ui.NetworkErrorActivity;
import com.corebyte.mob.bakingapp.ui.StepsActivity;
import com.corebyte.mob.bakingapp.ui.widget.RecipeWidgetProvider;
import com.corebyte.mob.bakingapp.utils.JsonParser;
import com.corebyte.mob.bakingapp.utils.thread.RecipeAsyncTaskLoader;

import java.util.ArrayList;

import static com.corebyte.mob.bakingapp.utils.RecipeUtil.RECIPES_KEY;
import static com.corebyte.mob.bakingapp.utils.RecipeUtil.RECIPE_URI;

public abstract class AbstractMasterFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 101;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<Recipe> recipes;
    private ProgressBar progressBar;

    public AbstractMasterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ifNetworkErrorLaunchNetworkErrorActivity(getActivity());

        View view = inflater.inflate(R.layout.recipe_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recipe_list_rv);
        recyclerView.setHasFixedSize(true);
        setLayoutManager();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_KEY, recipes);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)) {
            recipes = savedInstanceState.getParcelableArrayList(RECIPES_KEY);
        } else {
            //load data online
            initializeLoader();
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public abstract void setLayoutManager();

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {

        loadProgressBar();

        return new RecipeAsyncTaskLoader(getActivity(),
                new NetworkErrorHandler() {
                    @Override
                    public void onNetworkError(String error) {
                        networkErrorHandler(error);

                    }
                }, RECIPE_URI);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

        recipes = JsonParser.getJsonParser()
                .parseResponseData(data);

        adapter = new RecyclerViewAdapter(getContext(), recipes, new RecipeEventListener() {
            @Override
            public void onRecipeClicked(Recipe recipe) {
                Intent intent = new Intent(getActivity(), StepsActivity.class);
                intent.putExtra(StepsActivity.RECIPE_KEY, recipe);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
                ComponentName widget = new ComponentName(getActivity(), RecipeWidgetProvider.class);
                int[] ids = appWidgetManager.getAppWidgetIds(widget);

                Intent widgetIntent = new Intent(getActivity(), RecipeWidgetProvider.class);
                widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                widgetIntent.putExtra(StepsActivity.RECIPE_KEY, recipe);
                widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                getActivity().sendBroadcast(widgetIntent);
            }
        });

        recyclerView.setAdapter(adapter);

        unLoadProgressBar();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void ifNetworkErrorLaunchNetworkErrorActivity(Context context) {

        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {

                throw new NetworkErrorException(
                        getString(R.string.network_error_msg));
            }

        } catch (NetworkErrorException e) {

            networkErrorHandler(e.getMessage());

        }

    }

    private void networkErrorHandler(String errorMsg) {
        Intent intent = new Intent(getActivity(), NetworkErrorActivity.class);
        intent.putExtra(NetworkErrorActivity.NETWORK_ERROR_EXTRA, errorMsg);
        startActivity(intent);
    }

    private void initializeLoader() {
        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(LOADER_ID);

        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(LOADER_ID, null, this).forceLoad();
        }
    }

    private void loadProgressBar() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

    }

    private void unLoadProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public interface NetworkErrorHandler {
        public void onNetworkError(String error);
    }

}
