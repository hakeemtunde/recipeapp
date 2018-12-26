package com.corebyte.mob.bakingapp.utils;

import android.content.Context;
import android.util.Log;

import com.corebyte.mob.bakingapp.entity.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonParser {

    private static final String TAG = JsonParser.class.getSimpleName();

    private static final String FILE_NAME = "baking.json";
    private static final String FILE_ENCODE_FORMAT = "UTF-8";
    private static JsonParser INSTANCE;

    private JsonParser() {
    }

    public static JsonParser getJsonParser() {
        if (INSTANCE == null) {
            INSTANCE = new JsonParser();
        }
        return INSTANCE;
    }

    public ArrayList<Recipe> fetchRecipes(Context context) {
        List<Recipe> recipes = null;
        try {
            String data = loadJsonFile(context);
            recipes = parseUsingGson(data);
            if (recipes == null) throw new Exception("Recipe list is null");
        } catch (IOException io) {
            Log.e(TAG, "IOException: " + io.getMessage());
        } catch (Exception je) {
            Log.e(TAG, "Exception: " + je.getMessage());
        }

        return convertToArrayList(recipes);
    }

    private String loadJsonFile(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open(FILE_NAME);
        int filesize = inputStream.available();
        byte[] buffer = new byte[filesize];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer, FILE_ENCODE_FORMAT);
    }

    public ArrayList<Recipe> parseResponseData(String data) {
        List<Recipe> recipes = null;
        try {
            recipes = parseUsingGson(data);
            if (recipes == null) throw new Exception("Recipe list is null");
        } catch (IOException io) {
            Log.e(TAG, "IOException: " + io.getMessage());
        } catch (Exception je) {
            Log.e(TAG, "Exception: " + je.getMessage());
        }

        return convertToArrayList(recipes);
    }

    private List<Recipe> parseUsingGson(String data) throws JSONException {

        if (data == null) return null;

        JSONArray jsonarray = new JSONArray(data);

        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.serializeNulls();
        Gson gson = gsonbuilder.create();

        List<Recipe> recipes
                = Arrays.asList(gson.fromJson(jsonarray.toString(), Recipe[].class));

        return recipes;
    }

    private ArrayList<Recipe> convertToArrayList(List<Recipe> recipes) {

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        if (recipes == null) return recipeArrayList;

        for (Recipe recipe : recipes) {
            recipeArrayList.add(recipe);
        }

        return recipeArrayList;
    }

}
