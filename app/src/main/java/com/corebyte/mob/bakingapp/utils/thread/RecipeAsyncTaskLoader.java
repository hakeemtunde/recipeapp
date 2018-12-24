package com.corebyte.mob.bakingapp.utils.thread;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.corebyte.mob.bakingapp.ui.fragment.AbstractMasterFragment.NetworkErrorHandler;
import com.corebyte.mob.bakingapp.utils.NetClient;

import java.io.IOException;

public class RecipeAsyncTaskLoader extends AsyncTaskLoader<String> {

    private static String TAG = RecipeAsyncTaskLoader.class.getSimpleName();

    private String recipePath;
    private String responseData;
    NetworkErrorHandler networkErrorHandler;

    public RecipeAsyncTaskLoader(Context context, String path) {
        super(context);
        this.recipePath = path;
    }

    public RecipeAsyncTaskLoader(Context context, NetworkErrorHandler errorHandler, String path) {
        super(context);
        networkErrorHandler = errorHandler;
        this.recipePath = path;
    }

    @Nullable
    @Override
    public String loadInBackground() {

        NetClient netClient = new NetClient();

        try {
            responseData = netClient.makeHttpRequest(recipePath);
        }catch (IOException io){
            if (networkErrorHandler != null) {
                networkErrorHandler.onNetworkError(io.getMessage());
            } else {
                Log.e(TAG, "connection error: "+ io.getMessage(), io);
            }


        }finally {
            try {
                netClient.disconnect();
            }catch (IOException io){
                Log.e(TAG, "closing connection: "+ io.getMessage(), io);
            }
        }

        return responseData;
    }

    @Override
    protected void onStartLoading() {
        //catch
        if (responseData !=null) {
            deliverResult(responseData);

        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(@Nullable String data) {
        responseData = data;
        super.deliverResult(data);
    }
}
